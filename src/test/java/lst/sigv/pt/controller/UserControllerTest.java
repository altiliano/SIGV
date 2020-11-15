package lst.sigv.pt.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

/**
 * Created by Afonseca on 15/11/20
 */
@SpringBootTest
class UserControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;


    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();
    }

    @Test
    void createNewUser() throws Exception {
        String registerUserInJson = "{\n" +
                "    \"firstName\":\"teste\",\n" +
                "    \"lastName\": \"teste\",\n" +
                "    \"email\": \"aa@gmail.com\",\n" +
                "    \"country\":\"Portugal\",\n" +
                "    \"city\": \"Lisbon\",\n" +
                "    \"ivaoId\": \"123232323\",\n" +
                "    \"vatsimId\": \"2312323\",\n" +
                "    \"birthDate\": \"2018-10-22\",\n" +
                "    \"confirmPassword\": \"password\",\n" +
                "    \"username\": \"aa@gmail.com\",\n" +
                "    \"password\": \"password\"\n" +
                "}";
        mockMvc.perform(post("/api/user/register")
                .content(registerUserInJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void testValidation() throws Exception {
        String registerUserInJson = "{\n" +
                "    \"firstName\":\"teste\",\n" +
                "    \"lastName\": \"teste\",\n" +
                "    \"email\": \"aa@gmail.com\",\n" +
                "    \"country\":\"Portugal\",\n" +
                "    \"city\": \"Lisbon\",\n" +
                "    \"ivaoId\": \"123232323\",\n" +
                "    \"vatsimId\": \"2312323\",\n" +
                "    \"birthDate\": \"2018-10-22\",\n" +
                "    \"confirmPassword\": \"password\",\n" +
                "    \"password\": \"password\"\n" +
                "}";
        mockMvc.perform(post("/api/user/register")
                .content(registerUserInJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors", is(notNullValue())))
                .andExpect(jsonPath("$.errors.username", is(notNullValue())))
                .andExpect(jsonPath("$.errors.username", is("Please provide valid username.")));
    }
}