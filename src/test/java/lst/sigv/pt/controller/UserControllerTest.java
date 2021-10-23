package lst.sigv.pt.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Ignore;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

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


    @Ignore
    void createNewUser() throws Exception {
        String registerUserInJson = "{\n" +
                "    \"firstName\":\"teste\",\n" +
                "    \"lastName\": \"teste\",\n" +
                "    \"email\": \"aa@gmail.com\",\n" +
                "    \"emailConfirmation\": \"aa@gmail.com\",\n" +
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
                "    \"lastName\": \"teste\",\n" +
                "    \"emailConfirmation\": \"aa@gmail.com\",\n" +
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
                .andExpect(jsonPath("$.errors.email", is(notNullValue())))
                .andExpect(jsonPath("$.errors.firstName", is(notNullValue())))
                .andExpect(jsonPath("$.errors.firstName", is("Please provide valid name.")))
                .andExpect(jsonPath("$.errors.email", is("Please provide valid email.")));
    }

    @Test
    void authenticateUserBadCredential() throws Exception {
        String authenticateUser = "{\n" +
                "\n" +
                "    \"username\": \"admin@gmail.com\",\n" +
                "    \"password\": \"aaaaa\"\n" +
                "}";
        mockMvc.perform(post("/api/user/login")
                .content(authenticateUser)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }

    @Test
    void activeUserBadTokenKey() throws Exception {
        mockMvc.perform(post("/api/user/active/MTYxMTE2ODY3MjcyNzo5ZDQ1M2E1ODdhZDEwODg2NDRlY2MxZTU4ZTU4ODJjZDphQGdtYWlsLmNvbTpkMDdkOGQyZDkxZGFkYTVlZmU4NjljNGRhMmJiYjcxNTUyZmJlZDdkMmE5NjQ3NTk3NjJiZDgzYzQxYzNhMGU5YmI2YTFkNTk0YTk4MDU4NjdjMWJmNDExZDVlMDk2NDMwMTZmNDgwNTA0NzJmNjljMDZkOGU3YjRkNmQxNjU2Ng==")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void EditAccount() throws Exception {
        String updateUserForm = "[{\n" +
                "    \"op\":\"replace\",\n" +
                "    \"path\":\"/firstName\",\n" +
                "    \"value\": \"teste\"\n" +
                "} ]";
        String accessToken = obtainAccessToken();
        try {
            mockMvc.perform(patch("/api/user/edit-account/1")
                            .contentType("application/json-patch+json")
                            .content(updateUserForm)
                            .header("Authorization", "Bearer " + accessToken))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.firstName", is("teste")));
        } catch (Exception exception) {
            Assertions.fail();
        }

    }

    private String obtainAccessToken() throws Exception {

        String authenticateUser = "{\n" +
                "\n" +
                "    \"username\": \"admin@gmail.com\",\n" +
                "    \"password\": \"admin\"\n" +
                "}";

        ResultActions result
                = mockMvc.perform(post("/api/user/login")
                        .content(authenticateUser)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        String resultString = result.andReturn().getResponse().getContentAsString();

        JacksonJsonParser jsonParser = new JacksonJsonParser();
        return jsonParser.parseMap(resultString).get("token").toString();
    }
}