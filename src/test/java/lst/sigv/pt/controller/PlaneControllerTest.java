package lst.sigv.pt.controller;

import lst.sigv.pt.exception.InvalidPlaneStatusException;
import lst.sigv.pt.exception.PlaneAlreadyExistException;
import lst.sigv.pt.exception.PlaneNotFoundException;
import lst.sigv.pt.model.PlaneEntity;
import lst.sigv.pt.model.PlaneStatus;
import lst.sigv.pt.model.api.RestPlane;
import lst.sigv.pt.model.api.RestPlaneForm;
import lst.sigv.pt.service.PlaneService;
import lst.sigv.pt.service.mapper.PlaneMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Afonseca on 18/11/20
 */
class PlaneControllerTest {

    @InjectMocks
    private PlaneController controller;
    @Mock
    private PlaneService planeService;
    @Mock
    private PlaneMapper planeMapper;

    private MockMvc mockMvc;

    private final String planeFormInJson = "{\n" +
            "\n" +
            "    \"name\": \"Miguel Gomes\",\n" +
            "    \"registration\": \"CS-TST\",\n" +
            "    \"photoUrl\":\"https://www.texasstandard.org/wp-content/uploads/2018/04/33491246922_bbabff8c4f_k.jpg\",\n" +
            "    \"textureUrl\":\"https://www.texasstandard.org/wp-content/uploads/2018/04/33491246922_bbabff8c4f_k.jpg\",\n" +
            "    \"aircraftType\": \"B737\"\n" +
            "}";

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

    }

    @Test
    void createPlane() throws Exception {
        PlaneEntity planeEntity = getPlaneEntity();
        RestPlane restPlane = getPlane();
        RestPlaneForm form = getPlaneForm();
        when(planeService.existsByRegistration("CS-TST")).thenReturn(false);
        when(planeMapper.restPlaneFormToPlaneEntity(form)).thenReturn(planeEntity);
        when(planeService.createPlane(planeEntity)).thenReturn(planeEntity);
        when(planeMapper.planeEntityToRestPlane(planeEntity)).thenReturn(restPlane);

        mockMvc.perform(post("/api/plane/create")
                .content(planeFormInJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(restPlane.getName())))
                .andExpect(jsonPath("$.aircraftType", is(restPlane.getAircraftType())))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.photoUrl", is(restPlane.getPhotoUrl())))
                .andExpect(jsonPath("$.textureUrl", is(restPlane.getTextureUrl())))
                .andExpect(jsonPath("$.status", is(restPlane.getStatus())))
                .andExpect(jsonPath("$.registration", is(restPlane.getRegistration())));
    }

    @Test
    void createPlaneWithSameRegistration() throws Exception {
        PlaneEntity planeEntity = getPlaneEntity();
        RestPlaneForm form = getPlaneForm();
        when(planeService.createPlane(planeEntity)).thenThrow(new PlaneAlreadyExistException("Plane with registration: " + "CS-TST" + " already exist"));
        when(planeService.existsByRegistration(planeEntity.getRegistration())).thenReturn(true);
        when(planeMapper.restPlaneFormToPlaneEntity(form)).thenReturn(planeEntity);
        mockMvc.perform(post("/api/plane/create")
                .content(planeFormInJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof PlaneAlreadyExistException))
                .andExpect(mvcResult -> assertEquals("Plane with registration: " + "CS-TST" + " already exist", mvcResult.getResolvedException().getMessage()));
    }


    @Test
    void updatePlane() throws Exception {
        PlaneEntity planeEntity = getPlaneEntity();
        RestPlaneForm form = getPlaneForm();
        RestPlane restPlane = getPlane();
        when(planeMapper.restPlaneFormToPlaneEntity(form)).thenReturn(planeEntity);
        when(planeService.createPlane(planeEntity)).thenReturn(planeEntity);
        when(planeMapper.planeEntityToRestPlane(planeEntity)).thenReturn(restPlane);

        mockMvc.perform(post("/api/plane/update")
                .content(planeFormInJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(restPlane.getName())))
                .andExpect(jsonPath("$.aircraftType", is(restPlane.getAircraftType())))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.photoUrl", is(restPlane.getPhotoUrl())))
                .andExpect(jsonPath("$.textureUrl", is(restPlane.getTextureUrl())))
                .andExpect(jsonPath("$.status", is(restPlane.getStatus())))
                .andExpect(jsonPath("$.registration", is(restPlane.getRegistration())));

    }


    @Test
    void activePlane() throws Exception {
        PlaneEntity activePlaneEntity = getPlaneEntity();
        activePlaneEntity.setStatus(PlaneStatus.ACTIVE);
        RestPlane restPlane = getPlane();
        restPlane.setStatus(PlaneStatus.ACTIVE);
        when(planeService.changePlaneStatus(PlaneStatus.ACTIVE,Long.valueOf("1"))).thenReturn(activePlaneEntity);
        when(planeMapper.planeEntityToRestPlane(activePlaneEntity)).thenReturn(restPlane);
        mockMvc.perform(post("/api/plane/active")
                .content("1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(restPlane.getName())))
                .andExpect(jsonPath("$.aircraftType", is(restPlane.getAircraftType())))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.photoUrl", is(restPlane.getPhotoUrl())))
                .andExpect(jsonPath("$.textureUrl", is(restPlane.getTextureUrl())))
                .andExpect(jsonPath("$.status", is(restPlane.getStatus().toString())))
                .andExpect(jsonPath("$.registration", is(restPlane.getRegistration())));
    }

    @Test
    void activePlaneWithInvalidStatus() throws Exception {
        when(planeService.changePlaneStatus(PlaneStatus.ACTIVE,Long.valueOf("1"))).thenThrow(new InvalidPlaneStatusException("Invalid plane status"));
        mockMvc.perform(post("/api/plane/active")
                .content("1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof InvalidPlaneStatusException))
                .andExpect(mvcResult -> assertEquals("Invalid plane status", mvcResult.getResolvedException().getMessage()));
    }

    @Test
    void inactivePlaneNotFound() throws Exception {
        when(planeService.changePlaneStatus(PlaneStatus.INACTIVE,Long.valueOf("1"))).thenThrow(new PlaneNotFoundException("plane with id: " + "1" + " not found"));
        mockMvc.perform(post("/api/plane/inactive")
                .content("1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(mvcResult -> assertTrue(mvcResult.getResolvedException() instanceof PlaneNotFoundException))
                .andExpect(mvcResult -> assertEquals("plane with id: " + "1" + " not found", mvcResult.getResolvedException().getMessage()));


    }

    @Test
    void inactivePlane() throws Exception {

        PlaneEntity inactivePlaneEntity = getPlaneEntity();
        PlaneEntity activePlaneEntity = getPlaneEntity();
        activePlaneEntity.setStatus(PlaneStatus.INACTIVE);
        RestPlane restPlane = getPlane();
        restPlane.setStatus(PlaneStatus.INACTIVE);
        when(planeService.changePlaneStatus(PlaneStatus.INACTIVE,Long.valueOf("1"))).thenReturn(inactivePlaneEntity);
        when(planeMapper.planeEntityToRestPlane(inactivePlaneEntity)).thenReturn(restPlane);
        mockMvc.perform(post("/api/plane/inactive")
                .content("1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(restPlane.getName())))
                .andExpect(jsonPath("$.aircraftType", is(restPlane.getAircraftType())))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.photoUrl", is(restPlane.getPhotoUrl())))
                .andExpect(jsonPath("$.textureUrl", is(restPlane.getTextureUrl())))
                .andExpect(jsonPath("$.status", is(restPlane.getStatus().toString())))
                .andExpect(jsonPath("$.registration", is(restPlane.getRegistration())));
    }

    @Test
    void deletePlane() throws Exception {
        PlaneEntity deletedPlane = getPlaneEntity();
        deletedPlane.setStatus(PlaneStatus.DELETE);
        RestPlane restPlane = getPlane();
        restPlane.setStatus(PlaneStatus.DELETE);
        when(planeService.changePlaneStatus(PlaneStatus.DELETE,Long.valueOf("1"))).thenReturn(deletedPlane);
        when(planeMapper.planeEntityToRestPlane(deletedPlane)).thenReturn(restPlane);
        mockMvc.perform(post("/api/plane/delete")
                .content("1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(restPlane.getName())))
                .andExpect(jsonPath("$.aircraftType", is(restPlane.getAircraftType())))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.photoUrl", is(restPlane.getPhotoUrl())))
                .andExpect(jsonPath("$.textureUrl", is(restPlane.getTextureUrl())))
                .andExpect(jsonPath("$.status", is(restPlane.getStatus().toString())))
                .andExpect(jsonPath("$.registration", is(restPlane.getRegistration())));
    }

    private PlaneEntity getPlaneEntity() {
        PlaneEntity plane = new PlaneEntity();
        plane.setStatus(PlaneStatus.INACTIVE);
        plane.setAircraftType("B737");
        plane.setName("Miguel Gomes");
        plane.setRegistration("CS-TST");
        plane.setPhotoUrl("https://www.texasstandard.org/wp-content/uploads/2018/04/33491246922_bbabff8c4f_k.jpg");
        plane.setTextureUrl("https://www.texasstandard.org/wp-content/uploads/2018/04/33491246922_bbabff8c4f_k.jpg");
        return plane;
    }


    private RestPlane getPlane() {
        RestPlane restPlane = new RestPlane();
        restPlane.setAircraftType("B737");
        restPlane.setId(1L);
        restPlane.setName("Miguel Gomes");
        restPlane.setRegistration("CS-TST");
        restPlane.setPhotoUrl("https://www.texasstandard.org/wp-content/uploads/2018/04/33491246922_bbabff8c4f_k.jpg");
        return restPlane;
    }

    private RestPlaneForm getPlaneForm() {
        RestPlaneForm form = new RestPlaneForm();
        form.setAircraftType("B737");
        form.setName("Miguel Gomes");
        form.setRegistration("CS-TST");
        form.setTextureUrl("https://www.texasstandard.org/wp-content/uploads/2018/04/33491246922_bbabff8c4f_k.jpg");
        form.setPhotoUrl("https://www.texasstandard.org/wp-content/uploads/2018/04/33491246922_bbabff8c4f_k.jpg");
        return form;
    }
}