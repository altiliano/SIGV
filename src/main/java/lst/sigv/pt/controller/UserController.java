package lst.sigv.pt.controller;

import lombok.extern.slf4j.Slf4j;
import lst.sigv.pt.config.JwtUtils;
import lst.sigv.pt.model.UserEntity;
import lst.sigv.pt.model.api.*;
import lst.sigv.pt.notification.NotificationNewUserData;
import lst.sigv.pt.notification.service.EmailService;
import lst.sigv.pt.service.UserService;
import lst.sigv.pt.service.impl.LstUserDetailService;
import lst.sigv.pt.service.mapper.AuthorityMapper;
import lst.sigv.pt.service.mapper.UserMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.token.Token;
import org.springframework.security.core.token.TokenService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.security.Principal;
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

    public UserController(UserService userService, UserMapper userMapper, PasswordEncoder passwordEncoder, LstUserDetailService userDetailService, AuthenticationManager authenticationManager, JwtUtils jwtUtils, EmailService emailService, TokenService tokenService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userDetailService = userDetailService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.emailService = emailService;
        this.tokenService = tokenService;
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
    public RestUser getUserDetail(Principal principal) {
        return userMapper.userEntityToRestUser(userService.findUserByUsername(principal.getName()));
    }


    private Set<RestAuthority> convertAuthoritiesToRestAuthorities(Collection< ? extends GrantedAuthority> grantedAuthorities) {
        if (grantedAuthorities != null && grantedAuthorities.size() > 0) {
            return grantedAuthorities.stream()
                    .map(grantedAuthority -> new RestAuthority(grantedAuthority.getAuthority()))
                    .collect(Collectors.toSet());
        }
        return new HashSet<>();
    }
}
