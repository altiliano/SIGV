package lst.sigv.pt.controller;

import lombok.extern.slf4j.Slf4j;
import lst.sigv.pt.exception.InvalidPlaneStatusException;
import lst.sigv.pt.exception.PlaneAlreadyExistException;
import lst.sigv.pt.exception.PlaneNotFoundException;
import lst.sigv.pt.model.PlaneEntity;
import lst.sigv.pt.model.PlaneStatus;
import lst.sigv.pt.model.api.RestPlane;
import lst.sigv.pt.model.api.RestPlaneForm;
import lst.sigv.pt.service.PlaneService;
import lst.sigv.pt.service.mapper.PlaneMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Afonseca on 18/11/20
 */
@RestController
@RequestMapping("/api/plane")
@Slf4j
public class PlaneController {
    private final PlaneService planeService;
    private final PlaneMapper planeMapper;

    public PlaneController(PlaneService planeService, PlaneMapper planeMapper) {
        this.planeService = planeService;
        this.planeMapper = planeMapper;
    }

    @RequestMapping("/create")
    @ResponseBody
    public RestPlane createPlane(@Validated @RequestBody RestPlaneForm form) {
        PlaneEntity plane = planeMapper.restPlaneFormToPlaneEntity(form);
        return planeMapper.planeEntityToRestPlane(planeService.createPlane(plane));
    }

    @RequestMapping("/update")
    @ResponseBody
    public RestPlane updatePlane(@Validated @RequestBody RestPlaneForm form) {
        PlaneEntity plane = planeMapper.restPlaneFormToPlaneEntity(form);
        return planeMapper.planeEntityToRestPlane(planeService.createPlane(plane));
    }

    @RequestMapping("/active")
    @ResponseBody
    public RestPlane activePlane(@RequestBody String planeId) {
        return planeMapper.planeEntityToRestPlane(planeService.changePlaneStatus(PlaneStatus.ACTIVE, Long.valueOf(planeId)));
    }

    @RequestMapping("/inactive")
    @ResponseBody
    public RestPlane inactivePlane(@RequestBody String planeId) {
        return planeMapper.planeEntityToRestPlane(planeService.changePlaneStatus(PlaneStatus.INACTIVE, Long.valueOf(planeId)));
    }

    @RequestMapping("/delete")
    @ResponseBody
    public RestPlane deletePlane(@RequestBody String planeId) {
        return planeMapper.planeEntityToRestPlane(planeService.changePlaneStatus(PlaneStatus.DELETE, Long.valueOf(planeId)));
    }


}
