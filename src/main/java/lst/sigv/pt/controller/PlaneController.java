package lst.sigv.pt.controller;

import lombok.extern.slf4j.Slf4j;
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
        if (planeService.existsByRegistration(form.getRegistration())) {
            //TODO add validation error. Registration need be unique!
            return null;
        }
        PlaneEntity plane = planeMapper.restPlaneFormToPlaneEntity(form);
        plane.setStatus(PlaneStatus.INACTIVE);
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
        PlaneEntity plane = planeService.findPlaneById(Long.valueOf(planeId));
        if (!isValidaPlaneStatus(PlaneStatus.ACTIVE, plane.getStatus())) {
            //TODO add validation error. Invalided plane status.
        }
        plane.setStatus(PlaneStatus.ACTIVE);
        return planeMapper.planeEntityToRestPlane(planeService.updatePlane(plane));
    }

    @RequestMapping("/inactive")
    @ResponseBody
    public RestPlane inactivePlane(@RequestBody String planeId) {
        PlaneEntity plane = planeService.findPlaneById(Long.valueOf(planeId));
        if (!isValidaPlaneStatus(PlaneStatus.INACTIVE, plane.getStatus())) {
            //TODO add validation error. Invalided plane status.
        }

        plane.setStatus(PlaneStatus.INACTIVE);
        return planeMapper.planeEntityToRestPlane(planeService.updatePlane(plane));
    }

    @RequestMapping("/delete")
    @ResponseBody
    public RestPlane deletePlane(@RequestBody String planeId) {
        PlaneEntity plane = planeService.findPlaneById(Long.valueOf(planeId));
        if (!isValidaPlaneStatus(PlaneStatus.DELETE, plane.getStatus())) {
            //TODO add validation error. Invalided plane status.
        }

        plane.setStatus(PlaneStatus.DELETE);
        return planeMapper.planeEntityToRestPlane(planeService.updatePlane(plane));
    }

    private boolean isValidaPlaneStatus(PlaneStatus newStatus, PlaneStatus oldStatus) {
        if (newStatus.equals(oldStatus)) {
            return false;
        } else if (oldStatus.equals(PlaneStatus.DELETE)) {
            return false;
        }
        return true;
    }

}
