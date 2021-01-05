package lst.sigv.pt.controller;

import lombok.extern.slf4j.Slf4j;
import lst.sigv.pt.config.JwtUtils;
import lst.sigv.pt.model.UserEntity;
import lst.sigv.pt.model.api.RestAuthenticate;
import lst.sigv.pt.model.api.RestAuthenticateResponse;
import lst.sigv.pt.model.api.RestUser;
import lst.sigv.pt.model.api.RestUserRegistration;
import lst.sigv.pt.notification.NotificationNewUserData;
import lst.sigv.pt.notification.service.EmailService;
import lst.sigv.pt.service.LstUserDetailService;
import lst.sigv.pt.service.UserService;
import lst.sigv.pt.service.mapper.UserMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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

    public UserController(UserService userService, UserMapper userMapper, PasswordEncoder passwordEncoder, LstUserDetailService userDetailService, AuthenticationManager authenticationManager, JwtUtils jwtUtils, EmailService emailService) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.userDetailService = userDetailService;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.emailService = emailService;
    }

    @PostMapping("/register")
    @ResponseBody
    public RestUser registerUser(@Validated @RequestBody RestUserRegistration restUserRegistration) {
        UserEntity userEntity = new UserEntity();
        userEntity.setFirstName(restUserRegistration.getFirstName());
        userEntity.setLastName(restUserRegistration.getLastName());
        userEntity.setBirthDate(restUserRegistration.getBirthDate());
        userEntity.setUsername(restUserRegistration.getEmail());
        userEntity.setEmail(restUserRegistration.getEmail());
        userEntity.setPassword(passwordEncoder.encode(restUserRegistration.getPassword()));
        RestUser user = userMapper.userEntityToRestUser(userService.saveUser(userEntity));
        emailService.sendNewUserEmail(NotificationNewUserData.builder()
                .userEmail(user.getEmail())
                .name(user.getFirstName() + " " + user.getLastName())
                .url("https://www.baeldung.com/spring-email")
                .message("Hello Word!\n Welcome to Lusitania Air")
                .build());
        return user;
    }

    @PostMapping("/login")
    @ResponseBody
    public RestAuthenticateResponse login(@Validated @RequestBody RestAuthenticate authenticate) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticate.getUsername(), authenticate.getPassword()));
            UserDetails userDetails = userDetailService.loadUserByUsername(authenticate.getUsername());
            //TODO get authorities for the user
            return new RestAuthenticateResponse(jwtUtils.generateToken(userDetails), null);
        } catch (BadCredentialsException exception) {
            log.error("Can not authenticate user " + authenticate.getUsername() + " BadCredential");
            throw new BadCredentialsException("Incorrect user or password", exception);
        }
    }
}
