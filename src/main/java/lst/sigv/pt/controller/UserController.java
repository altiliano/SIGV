package lst.sigv.pt.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;
import lombok.extern.slf4j.Slf4j;
import lst.sigv.pt.config.JwtUtils;
import lst.sigv.pt.exception.UserNotFoundException;
import lst.sigv.pt.model.UserEntity;
import lst.sigv.pt.model.api.*;
import lst.sigv.pt.notification.NotificationNewUserData;
import lst.sigv.pt.notification.service.EmailService;
import lst.sigv.pt.service.AuthenticationFacade;
import lst.sigv.pt.service.FileStoreService;
import lst.sigv.pt.service.UserService;
import lst.sigv.pt.service.impl.LstUserDetailService;
import lst.sigv.pt.service.mapper.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Afonseca on 15/11/20
 */
@RestController
@RequestMapping("/api/user")
@Slf4j
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final LstUserDetailService userDetailService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final AuthenticationFacade authenticationFacade;
    private final FileStoreService fileStoreService;

    public UserController(UserService userService, UserMapper userMapper, PasswordEncoder passwordEncoder, LstUserDetailService userDetailService, AuthenticationManager authenticationManager, JwtUtils jwtUtils, EmailService emailService, TokenService tokenService, AuthenticationFacade authenticationFacade, FileStoreService fileStoreService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userDetailService = userDetailService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.emailService = emailService;
        this.tokenService = tokenService;
        this.authenticationFacade = authenticationFacade;
        this.fileStoreService = fileStoreService;
    }

    @PostMapping("/register")
    @ResponseBody
    public RestUser registerUser(@Validated @RequestBody RestUserRegistration restUserRegistration) throws MessagingException {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(restUserRegistration.getFirstName());
        userEntity.setLastName(restUserRegistration.getLastName());
        userEntity.setBirthDate(restUserRegistration.getBirthDate());
        userEntity.setUsername(restUserRegistration.getEmail());
        userEntity.setEmail(restUserRegistration.getEmail());
        userEntity.setPassword(passwordEncoder.encode(restUserRegistration.getPassword()));
        RestUser user = userMapper.userEntityToRestUser(userService.createUser(userEntity));
        emailService.sendNewUserEmail(NotificationNewUserData.builder()
                .userEmail(user.getEmail())
                .name(user.getFirstName() + " " + user.getLastName())
                .build());
        return user;
    }

    @PostMapping("/login")
    @ResponseBody
    public RestAuthenticateResponse login(@Validated @RequestBody RestAuthenticate authenticate) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticate.getUsername(), authenticate.getPassword()));
            UserDetails userDetails = userDetailService.loadUserByUsername(authenticate.getUsername());
            return new RestAuthenticateResponse(jwtUtils.generateToken(userDetails), convertAuthoritiesToRestAuthorities(userDetails.getAuthorities()));
        } catch (BadCredentialsException exception) {
            log.error("Can not authenticate user " + authenticate.getUsername() + " BadCredential");
            throw new BadCredentialsException("Incorrect user or password", exception);
        }
    }

    @RequestMapping(value = "/active/{key}", method = RequestMethod.POST)
    public ResponseEntity<?> activeUser(@PathVariable("key") String tokenKey) {
        try {
            Token token = tokenService.verifyToken(tokenKey);
            Assert.notNull(token, "key can't be null");
            String userEmail = token.getExtendedInformation();
            userService.activeUser(userEmail);
            return ResponseEntity.ok().body("User active successfully");
        } catch (Exception exception) {
            log.error("Can't active user");
            return ResponseEntity.badRequest().body("Can't active user");
        }

    }

    @GetMapping("/detail")
    @ResponseBody
    public RestUser getUserDetail() throws UsernameNotFoundException {
        UserDetails userDetails = getAuthenticateUser();
        assert userDetails != null;
        return userMapper.userEntityToRestUser(userService.findUserByUsername(userDetails.getUsername()));
    }


    @PostMapping("/changePassword")
    public ResponseEntity<?> changePassword(@Validated @RequestBody RestChangePasswordForm form) {
        UserEntity user = null;
       if (form.getUserName()  == null && form.getUserName().isEmpty() ){
           UserDetails userDetails = getAuthenticateUser();
           user = userService.findUserByEmail(userDetails.getUsername());
       }else {
           user = userService.findUserByEmail(form.getUserName());
       }
      if ( user == null) {
          return ResponseEntity.badRequest().body("can't change password because user is null");
      }
        user.setPassword(passwordEncoder.encode(form.getNewPassword()));
        userService.saveUser(user);
        return ResponseEntity.ok().body("Password change successfully");
    }
    @PostMapping("/requestChangePassword")
    public ResponseEntity<?> requestChangePassword(@Validated @RequestBody RestRequestChangePasswordForm form) throws MessagingException {
        UserEntity user =   userService.findUserByEmail(form.getEmail());
        if (user  != null) {
            String name = user.getFirstName() + " "+ user.getLastName();
            emailService.sendUrlToChangePassword( NotificationNewUserData.builder()
                    .name(name)
                    .userEmail(user.getEmail())
                    .build());
        } else {
            // to avoid fishing need to remove this line
            throw  new UserNotFoundException("user with: " + form.getEmail() + " not found");
        }

        return ResponseEntity.ok().body("Password recovery successfully");
    }

    @RequestMapping(value = "/changePassword/{key}", method = RequestMethod.POST)
    public ResponseEntity<RestResetPasswordForm> changePassword(@PathVariable("key") String tokenKey) {
        try {
            Token token = tokenService.verifyToken(tokenKey);
            RestResetPasswordForm form = new RestResetPasswordForm();
            form.setUserName(token.getExtendedInformation());
            Assert.notNull(token, "key can't be null");
            return ResponseEntity.ok().body(form);
        } catch (Exception exception) {
            log.error("Can't change password");
            return ResponseEntity.badRequest().body(null);
        }
    }



    @GetMapping("/refreshToken")
    public ResponseEntity<?> refreshToken() {
        UserDetails userDetails = getAuthenticateUser();
        return ResponseEntity.ok().body(jwtUtils.generateToken(userDetails));
    }

    @PatchMapping(path ="/edit-account/{id}" , consumes = "application/json-patch+json")
    public ResponseEntity<RestUser> editProfile(@RequestBody JsonPatch patch, @PathVariable("id") String userId) throws JsonPatchException, JsonProcessingException {
        Long id = Long.valueOf(userId);
        RestUser existentUser = userMapper.userEntityToRestUser(userService.findUserById(id));
        if (existentUser == null) {
            throw new UserNotFoundException("user with id: " + userId + " not found");
        }

        RestUser updatedUser = applyPatchToUser(patch, existentUser);
        UserEntity userEntity = userMapper.restUserToUserEntity(updatedUser);
        UserEntity user = userService.saveUser(userEntity);
        RestUser restUser = userMapper.userEntityToRestUser(user);

        return ResponseEntity.ok(restUser);
    }

    @PostMapping(path = "/photo-profile/{id}")
    public ResponseEntity<RestUser> updatePhotoProfile(@PathVariable("id") String userId, @RequestParam("file") MultipartFile file) throws UserNotFoundException, IOException {
        Long id = Long.parseLong(userId);
        if (file == null) {
            throw new RuntimeException("cannot upload profile photo.Because file is null");
        }
        UserEntity user = userService.addPhoto(file, id);

        RestUser restUser = userMapper.userEntityToRestUser(user);
        restUser.setProfileImage(file.getBytes());
        return ResponseEntity.ok(restUser);
    }



    private Set<RestAuthority> convertAuthoritiesToRestAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities) {
        if (grantedAuthorities != null && grantedAuthorities.size() > 0) {
            return grantedAuthorities.stream()
                    .map(grantedAuthority -> new RestAuthority(grantedAuthority.getAuthority()))
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }



    private UserDetails getAuthenticateUser() {
        Authentication authentication = authenticationFacade.getAuthentication();
       try {
           return  (UserDetails) authentication.getPrincipal();
        }catch (ClassCastException classCastException) {
           log.error(classCastException.getMessage());
       }
        return null;
    }

    private RestUser applyPatchToUser(JsonPatch patch, RestUser target) throws JsonPatchException, JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode patched = patch.apply(objectMapper.convertValue(target, JsonNode.class));
        return objectMapper.treeToValue(patched, RestUser.class);
    }

}
