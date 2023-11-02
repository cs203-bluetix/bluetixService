package bluetix.controller;

import bluetix.dao.request.SignUpRequest;
import bluetix.dao.request.SigninRequest;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(properties = { "spring.config.location=classpath:application-test.properties" })
@AutoConfigureMockMvc
class AuthenticationControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;

    // Test signupCustomer endpoint
    @Test
    void testSignupCustomer() throws Exception {
        SignUpRequest signUpRequest = new SignUpRequest("yes", "yes", "yes@yes.yes", "yes");

        mockMvc.perform(post("/api/auth/signup/customer")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("jwt"));
    }

    // Test signin endpoint
    @Test
    void testSignin() throws Exception {
        SigninRequest signinRequest = new SigninRequest("yes@yes.yes", "yes");

        mockMvc.perform(post("/api/auth/signin")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signinRequest)))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("jwt"));
    }

    // Add more test methods for other endpoints...

    // Test validateJwt endpoint
    @Test
    void testValidateJwt() throws Exception {
        // Perform a signup to obtain a valid jwt for testing
        SignUpRequest signUpRequest = new SignUpRequest("no", "no", "no@no.no", "no");
        ResultActions result = mockMvc.perform(post("/api/auth/signup/customer")
                .contentType("application/json")
                .content(objectMapper.writeValueAsString(signUpRequest)))
                .andExpect(status().isOk())
                .andExpect(cookie().exists("jwt"));

        // Extract jwt from the response
        String jwt = result.andReturn().getResponse().getCookie("jwt").getValue();

        // Test validateJwt endpoint
        mockMvc.perform(post("/api/auth/validateJwt")
                .cookie(new jakarta.servlet.http.Cookie("jwt", jwt)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("no@no.no"))
                .andExpect(jsonPath("$.role").value("CUSTOMER"));
    }
}
