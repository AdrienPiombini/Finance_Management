package finance_management.security.controller;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import finance_management.security.service.AuthenticationService;
import finance_management.security.utils.AuthenticationRequest;
import finance_management.security.utils.AuthenticationResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationService authenticationService;

    @Autowired
    private ObjectMapper objectMapper;

    private String requestJson;
    private AuthenticationResponse authenticationResponse;
    private final String URL = "/authentication/";

    @BeforeEach
    void setup() throws JsonProcessingException {
        AuthenticationRequest request = new AuthenticationRequest("fake data", null);
        requestJson = objectMapper.writeValueAsString(request);

    }

    @Test
    public void testRegisterMissingData() throws Exception {
        authenticationResponse = AuthenticationResponse.dataMissing();
        when(authenticationService.register(any())).thenReturn(authenticationResponse);

        ResultActions result = mockMvc.perform(post(URL + "register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        String expectedResponseJson = objectMapper.writeValueAsString(authenticationResponse.data());

        result.andExpect(status().isBadRequest())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    public void testRegisterExistentUser() throws Exception {
        authenticationResponse = AuthenticationResponse.userAlreadyExist();
        when(authenticationService.register(any())).thenReturn(authenticationResponse);

        ResultActions result = mockMvc.perform(post(URL + "register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        String expectedResponseJson = objectMapper.writeValueAsString(authenticationResponse.data());
        result.andExpect(status().isBadRequest())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    public void shouldRegister() throws Exception {
        authenticationResponse = AuthenticationResponse.token("jwt token");
        when(authenticationService.register(any())).thenReturn(authenticationResponse);

        ResultActions result = mockMvc.perform(post(URL + "register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        String expectedResponseJson = objectMapper.writeValueAsString(authenticationResponse.data());
        result.andExpect(status().isOk())
                .andExpect(content().json(expectedResponseJson));
    }


    @Test
    public void testFailedLogin() throws Exception {
        authenticationResponse = AuthenticationResponse.invalidCredentials();
        when(authenticationService.login(any())).thenReturn(authenticationResponse);

        ResultActions result = mockMvc.perform(post(URL + "login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        String expectedResponseJson = objectMapper.writeValueAsString(authenticationResponse.data());

        result.andExpect(status().isUnauthorized())
                .andExpect(content().json(expectedResponseJson));
    }

    @Test
    public void shouldLogin() throws Exception {
        authenticationResponse = AuthenticationResponse.token("jwt token");
        when(authenticationService.login(any())).thenReturn(authenticationResponse);

        ResultActions result = mockMvc.perform(post(URL + "login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson));

        String expectedResponseJson = objectMapper.writeValueAsString(authenticationResponse.data());

        result.andExpect(status().isOk())
                .andExpect(content().string(expectedResponseJson));
    }
}