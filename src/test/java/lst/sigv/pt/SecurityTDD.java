package lst.sigv.pt;

import lst.sigv.pt.model.api.RestAuthenticateResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Afonseca on 14/11/20
 */
@SpringBootTest
public class SecurityTDD {
    @Autowired
    private WebApplicationContext webApplicationContext;


    private MockMvc mockMvc;
    private final String existUser = "{\n" +
            "\n" +
            "    \"username\": \"admin@gmail.com\",\n" +
            "    \"password\": \"admin\"\n" +
            "}";

    @BeforeEach()
    void Setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .apply(springSecurity())
                .build();

    }


    @Test
    public void testUnAuthorize() throws Exception {
        String loginUserJson = "{\n" +
                "\n" +
                "    \"username\": \"aa@gmail.com\",\n" +
                "    \"password\": \"password\"\n" +
                "}";
        mockMvc.perform(post("/api/user/login")
                .content(loginUserJson)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden());
    }


    @Test
    public void testSuccessLogin() throws Exception {
        mockMvc.perform(post("/api/user/login")
                .content(existUser)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token", notNullValue()));
    }

    // To make this pass, need set  token.expiration.time.in.minutes = 0
    @Test
    public void testTokenExpired() throws Exception {

        MvcResult result = mockMvc.perform(post("/api/user/login")
                .content(existUser)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        String token = result.getResponse().getContentAsString().split(":")[1].split(",")[0].replaceAll("\"", "");

        String sendToken = "Bearer " + token;
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", sendToken);
        mockMvc.perform(get("/api/user/detail")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }
}
