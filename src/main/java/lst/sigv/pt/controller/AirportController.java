package lst.sigv.pt.controller;

import lombok.extern.slf4j.Slf4j;
import lst.sigv.pt.model.api.RestAirport;
import lst.sigv.pt.service.AirportService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Afonseca on 13/02/21
 */
@RestController
@RequestMapping("/api/airport")
@Slf4j
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping("all")
    @ResponseBody
    public List<RestAirport> getAllAirports() {
        return airportService.getAirports();
    }

    @PostMapping("/addAirport")
    @ResponseBody
    public RestAirport addAirport(@RequestBody RestAirport airport) {
        return airportService.addAirport(airport);
    }

    @PutMapping("edit")
    @ResponseBody
    public RestAirport editAirport(@RequestBody RestAirport airport) {
        return airportService.editAirport(airport);
    }

    @PostMapping("delete")
    public void deleteAirport(@RequestBody RestAirport airport) {
        airportService.deleteAirport(airport);
    }



}
