package lst.sigv.pt.config;

import lombok.extern.slf4j.Slf4j;
import lst.sigv.pt.model.*;
import lst.sigv.pt.model.api.RestAuthority;
import lst.sigv.pt.model.api.RestRoute;
import lst.sigv.pt.model.api.RestUser;
import lst.sigv.pt.service.AuthorityManagementService;
import lst.sigv.pt.service.PlaneService;
import lst.sigv.pt.service.RouteService;
import lst.sigv.pt.service.UserService;
import lst.sigv.pt.service.mapper.PlaneMapper;
import lst.sigv.pt.service.mapper.UserMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * Created by Afonseca on 23/01/21
 */
@Component
@Slf4j
public class DataLoader implements CommandLineRunner {
    private final AuthorityManagementService authorityManagementService;
    private final UserService userService;
    private final PlaneService planeService;
    private final RouteService routeService;
    private final UserMapper userMapper;
    private final PlaneMapper planeMapper;
    private final PasswordEncoder passwordEncoder;

    public DataLoader(AuthorityManagementService authorityManagementService, UserService userService, PlaneService planeService, RouteService routeService, UserMapper userMapper, PlaneMapper planeMapper, PasswordEncoder passwordEncoder) {
        this.authorityManagementService = authorityManagementService;
        this.userService = userService;
        this.planeService = planeService;
        this.routeService = routeService;
        this.userMapper = userMapper;
        this.planeMapper = planeMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void run(String... args) throws Exception {
        createAuthority();
        createUser();
        createPlane();
        createRoute();

    }


    private void createAuthority() {
        if (authorityManagementService.getAuthorities().size() == 0) {
            List<RestAuthority> restAuthorities = new ArrayList<>();
            restAuthorities.add(RestAuthority.builder().role("ADMIN").build());
            restAuthorities.add(RestAuthority.builder().role("MODERATOR").build());
            restAuthorities.add(RestAuthority.builder().role("ROUTE_MANAGER").build());
            authorityManagementService.addAuthorities(restAuthorities);
            log.info("------------------------------------------------Saving authorities ------------------------------------------------------");
        }
    }

    private void createUser() {
        if (userService.findUserByUsername("admin@gmial.com") == null) {
            UserEntity user = new UserEntity();
            user.setEmail("admin@gmail.com");
            user.setUsername("admin@gmail.com");
            user.setFirstName("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setStatus(UserStatus.ACTIVE);
            user.setLastName("admin");
            userService.saveUser(user);
            RestUser restUser = userMapper.userEntityToRestUser(user);
            authorityManagementService.associateAuthority(restUser, RestAuthority.builder().role("ADMIN").build());
            log.info("------------------------------------------------Saving user admin ------------------------------------------------------");
        }
    }

    private void createPlane() {
        PlaneEntity bessa = new PlaneEntity();
        bessa.setName("Agustina Bessa Luis");
        bessa.setAircraftType("A320");
        bessa.setRegistration("CS-TVB");
        bessa.setStatus(PlaneStatus.ACTIVE);
        bessa.setPhotoUrl("https://www.jetphotos.com/photo/10012775");
        bessa.setTextureUrl("https://www.jetphotos.com/photo/10012775");

        PlaneEntity luis = new PlaneEntity();
        luis.setName("Agustina Bessa Luis");
        luis.setStatus(PlaneStatus.ACTIVE);
        luis.setAircraftType("A320");
        luis.setRegistration("CS-TVV");
        luis.setPhotoUrl("https://www.jetphotos.com/photo/10012775");
        luis.setTextureUrl("https://www.jetphotos.com/photo/10012775");
        planeService.createPlane(bessa);
        planeService.createPlane(luis);
        log.info("------------------------------------------------Saving plane ------------------------------------------------------");
    }

    private void createRoute() {
        Iterable<PlaneEntity> planeEntities = planeService.getAllActivePlane();
        RestRoute route = new RestRoute();
        route.setDepart("LPPT");
        route.setDestination("GVNP");
        route.setStatus(RouteStatus.ACTIVE);
        for (PlaneEntity plane : planeEntities) {
            if (route.getPlanes() == null){
                route.setPlanes(new HashSet<>());
            }
            route.getPlanes().add(planeMapper.planeEntityToRestPlane(plane));
        }
        routeService.createRoute(route);
        log.info("------------------------------------------------Saving route ------------------------------------------------------");
    }
}
