package lst.sigv.pt.controller;

import lombok.extern.slf4j.Slf4j;
import lst.sigv.pt.model.api.RestAirport;
import lst.sigv.pt.model.api.RestPageRequest;
import lst.sigv.pt.model.api.RestPageResult;
import lst.sigv.pt.service.AirportService;
import org.mapstruct.ap.shaded.freemarker.template.utility.StringUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Afonseca on 13/02/21
 */
@RestController
@RequestMapping("/api/airport/")
@Slf4j
public class AirportController {

    private final AirportService airportService;

    public AirportController(AirportService airportService) {
        this.airportService = airportService;
    }

    @GetMapping("search")
    @ResponseBody
    public RestPageResult<RestAirport> search(@RequestParam("size") String pageSize, @RequestParam("number")  String pageNumber) {
        RestPageRequest request = new RestPageRequest();
        request.setPageNumber(Integer.parseInt(pageNumber));
        request.setPageSize(Integer.parseInt(pageSize));
        return airportService.getAllAirports(request);
    }

    @PostMapping("create")
    @ResponseBody
    public RestAirport addAirport(@RequestBody RestAirport airport) {
        return airportService.addAirport(airport);
    }

    @PutMapping("edit")
    @ResponseBody
    public RestAirport editAirport(@RequestBody RestAirport airport) {
        return airportService.updateAirport(airport);
    }

    @PostMapping("delete/{id}")
    public void deleteAirport(@PathVariable("id") String id) {
        airportService.deleteAirport(id);
    }



}
