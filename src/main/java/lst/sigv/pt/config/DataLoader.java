package lst.sigv.pt.config;

import lombok.extern.slf4j.Slf4j;
import lst.sigv.pt.exception.PlaneAlreadyExistException;
import lst.sigv.pt.exception.UserNotFoundException;
import lst.sigv.pt.model.*;
import lst.sigv.pt.model.api.*;
import lst.sigv.pt.service.*;
import lst.sigv.pt.service.mapper.PlaneMapper;
import lst.sigv.pt.service.mapper.UserMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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
    private final AirportService airportService;

    public DataLoader(AuthorityManagementService authorityManagementService, UserService userService, PlaneService planeService, RouteService routeService, UserMapper userMapper, PlaneMapper planeMapper, PasswordEncoder passwordEncoder, AirportService airportService) {
        this.authorityManagementService = authorityManagementService;
        this.userService = userService;
        this.planeService = planeService;
        this.routeService = routeService;
        this.userMapper = userMapper;
        this.planeMapper = planeMapper;
        this.passwordEncoder = passwordEncoder;
        this.airportService = airportService;
    }

    @Override
    public void run(String... args) {
        createAuthority();
        createUser();
        createAirports();
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
        String username = "admin@gmail.com";
        try {
            userService.findUserByUsername(username);
        } catch (UserNotFoundException ex) {
            log.info("Creating new user");
            UserEntity user = new UserEntity();
            user.setEmail(username);
            user.setUsername(username);
            user.setFirstName("admin");
            user.setPassword(passwordEncoder.encode("admin"));
            user.setStatus(UserStatus.ACTIVE);
            user.setLastName("admin");
            RestUser restUser = userMapper.userEntityToRestUser(user);
            authorityManagementService.associateAuthority(restUser, RestAuthority.builder().role("ADMIN").build());
            log.info("------------------------------------------------Saving user admin ------------------------------------------------------");
            userService.createUser(user);
        }
    }

    private void createPlane() {
        if (planeService.getAllActivePlane().size() == 0) {
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
            try {
                planeService.createPlane(bessa);
                planeService.createPlane(luis);
                log.info("------------------------------------------------Saving plane ------------------------------------------------------");
            } catch (PlaneAlreadyExistException exception) {
                log.info(exception.getMessage());
            }

        }
    }

    private void createRoute() {
        List<PlaneEntity> planeEntities = planeService.getAllActivePlane();
        List<RestPlane> planes = planeMapper.planeEntityToRestPlane(planeEntities);

        RestAirport gvnp = airportService.findAirportByIcaoCode("GVNP");
        RestAirport lppt = airportService.findAirportByIcaoCode("LPPT");
        RestAirport lppr = airportService.findAirportByIcaoCode("LPPR");
        RestRouteForm restRouteForm = new RestRouteForm();
        List<RestAirport> destinations = new ArrayList<>();

        destinations.add(lppt);
        destinations.add(lppr);
        restRouteForm.setDepart(gvnp);
        restRouteForm.setDestination(destinations);
        routeService.createRoute(restRouteForm);
        log.info("------------------------------------------------Saving route ------------------------------------------------------");
    }

    private void createAirports() {
        RestAirport lppt = RestAirport.builder().city("Lisbon")
                            .country("Portugal")
                            .iataCode("LIS")
                            .icaoCode("LPPT")
                            .latitude("38.7813")
                            .name("Humberto Delgado")
                            .longitude("-9.13592")
                .build();

        RestAirport lppr = RestAirport.builder().city("Porto")
                .country("Portugal")
                .iataCode("POR")
                .icaoCode("LPPR")
                .latitude("38.7813")
                .name("Francisco Sá Carneiro")
                .longitude("-9.13592")
                .build();

        RestAirport gvnp = RestAirport.builder().city("Praia")
                .country("Cape Verd")
                .iataCode("CAV")
                .icaoCode("GVNP")
                .latitude("38.7813")
                .name("Nelson Mandela")
                .longitude("-9.13592")
                .build();
        if (airportService.getAllAirports(new RestPageRequest(0, 10)).getNumberOfElements() == 0) {
            airportService.addAirport(lppt);
            airportService.addAirport(lppr);
            airportService.addAirport(gvnp);
        }

    }
}
