package lst.sigv.pt.controller;

import lombok.extern.slf4j.Slf4j;
import lst.sigv.pt.model.api.RestRoute;
import lst.sigv.pt.model.api.RestRouteForm;
import lst.sigv.pt.service.RouteService;
import lst.sigv.pt.service.mapper.RouteMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Afonseca on 22/11/2020.
 **/
@RestController
@RequestMapping("/api/route/")
@Slf4j
public class RouteController {
    private final RouteService routeService;
    private final RouteMapper routeMapper;

    public RouteController(RouteService routeService, RouteMapper routeMapper) {
        this.routeService = routeService;
        this.routeMapper = routeMapper;
    }

    @PostMapping("create")
    @ResponseBody
    public RestRoute createRoute(@RequestBody @Validated RestRouteForm form) {
        RestRoute restRoute = routeMapper.restRouteFormToRestRoute(form);
        return routeService.createRoute(restRoute);
    }

    @PostMapping("update")
    public RestRoute updateRoute(@RequestBody RestRoute route)  {
       return routeService.updateRoute(route);
    }

    @GetMapping("getAllActive")
    public List<RestRoute> getAllActiveRoute()  {
        return routeService.getAllActiveRoute();
    }

    @PostMapping("delete")
    public void deleteRoute(@RequestBody String routeId)  {
        routeService.deleteRoute(routeId);
    }

    @PostMapping("active")
    @ResponseBody
    public RestRoute activeRoute(@RequestBody String routeId)  {
        return routeService.activeRoute(routeId);
    }

    @PostMapping("inactive")
    @ResponseBody
    public RestRoute inactiveRoute(@RequestBody  String routeId)  {
        return routeService.inactiveRoute(routeId);
    }

}
