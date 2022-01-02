package lst.sigv.pt.controller;

import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import lst.sigv.pt.model.api.RestRoute;
import lst.sigv.pt.model.api.RestRouteForm;
import lst.sigv.pt.service.RouteService;
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

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @PostMapping("create")
    @ResponseBody
    @ApiOperation("Create new route")
    public RestRoute createRoute(@RequestBody @Validated RestRouteForm form) {
        return routeService.createRoute(form);
    }

    @PostMapping("update")
    @ApiOperation("Update Route")
    public RestRoute updateRoute(@RequestBody RestRoute route)  {
       return routeService.updateRoute(route);
    }

    @GetMapping("getAllActive/{currentPosition}")
    public List<RestRoute> getAllActiveRoute(@PathVariable ("currentPosition") String currentPosition)  {
        return routeService.getAllActiveRoute(currentPosition);
    }

    @PostMapping("delete/{routeId}")
    public void deleteRoute(@PathVariable("routeId") String routeId)  {
        routeService.deleteRoute(routeId);
    }

    @PostMapping("active/{routeId}")
    @ResponseBody
    public RestRoute activeRoute(@PathVariable("routeId") String routeId)  {
        return routeService.activeRoute(routeId);
    }

    @PostMapping("inactive/{routeId}")
    @ResponseBody
    public RestRoute inactiveRoute(@PathVariable("routeId")  String routeId)  {
        return routeService.inactiveRoute(routeId);
    }

}
