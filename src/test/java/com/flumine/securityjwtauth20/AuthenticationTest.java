package com.flumine.securityjwtauth20;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.flumine.securityjwtauth20.auth.AuthenticationRequest;
import com.flumine.securityjwtauth20.auth.RegisterRequest;
import com.flumine.securityjwtauth20.models.UserModel;
import com.flumine.securityjwtauth20.repositories.UserRepository;
import com.flumine.securityjwtauth20.services.JwtService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc

public class AuthenticationTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Value("${port}") private String port;



    @Test
    @Sql(value = {"delete-users.sql"})
    public void register() throws Exception {
        RegisterRequest registerRequest = new RegisterRequest("vadim2422", "breev.vadim@yandex.ru", "1234");
        String contentBody = objectMapper.writeValueAsString(registerRequest);
        this.mockMvc.perform(post("http://localhost:" + port + "/register").contentType(MediaType.APPLICATION_JSON)
                .content(contentBody)).andExpect(status().isCreated());
    }

    @Test
    @Sql(value = {"create-user-before-not-enable.sql"})
    public void confirm_email() throws Exception {
        UserModel user = userRepository.findByUsernameIgnoreCase("vadim2422").orElse(new UserModel());


        this.mockMvc.perform(get("http://localhost:" + port + "/confirm_email/"+ jwtService.generateAccessToken(new HashMap<>(), user)))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/"));
//        todo: redirect
    }

    @Test
    @Sql(value = {"create-user-before-enable.sql"})
    public void auth() throws Exception {

        AuthenticationRequest authenticationRequest = new AuthenticationRequest("vadim2422", "1234");
        String contentBody = objectMapper.writeValueAsString(authenticationRequest);
        this.mockMvc.perform(post("http://localhost:" + port + "/authenticate").contentType(MediaType.APPLICATION_JSON).content(contentBody))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("access_token")))
                .andExpect(content().string(containsString("refresh_token")));
    }

    @Test
    @Sql(value = {"create-user-before-enable.sql"})
    public void refresh_tokens() throws Exception {
        UserModel user = userRepository.findByUsernameIgnoreCase("vadim2422").orElse(new UserModel());
        String token = jwtService.generateRefreshToken(new HashMap<>(), user);
        this.mockMvc.perform(get("http://localhost:" + port + "/refresh_token").header("Authorization", "Bearer " + token))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("access_token")))
                .andExpect(content().string(containsString("refresh_token")));
    }
}
