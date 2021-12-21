package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.AdminAppBackendApplication;
import edu.fontys.horecarobot.adminappbackend.dtos.request.LoginRequestModel;
import edu.fontys.horecarobot.databaselibrary.models.AdminUser;
import edu.fontys.horecarobot.databaselibrary.repositories.AdminUserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import edu.fontys.horecarobot.adminappbackend.utilities.JsonConverterUtil;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AdminAppBackendApplication.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SignInControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private AdminUserRepository adminUserRepository;

    @BeforeAll
    public void setup(){
        var admin = new AdminUser();
        admin.setEmail("test@gmail.com");
        admin.setPassword("AbC1@DeF");
        adminUserRepository.saveAndFlush(admin);
    }

    @Test
    public void should_obtain_JWT_when_valid_credentials() throws Exception{

        var json = jsonConverter("test@gmail.com", "AbC1@DeF");
        mvc.perform(post("/api/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk())
                .andExpect(content()
                        .contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("data.jwt").isString());
    }

    @Test
    public void Should_refuse_when_email_is_invalid() throws Exception{
        var json = jsonConverter("wrong@gmail.com", "AbC1@DeF");

        mvc.perform(post("/api/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        .andExpect(status().isUnauthorized());
    }

    @Test
    public void Should_refuse_when_password_is_invalid() throws Exception{
        var json = jsonConverter("test@gmail.com", "wrongPassword");
        mvc.perform(post("/api/sign-in")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
        .andExpect(status().isUnauthorized());
    }

    private String jsonConverter(String email, String password){
        LoginRequestModel model = new LoginRequestModel();
        model.setEmail(email);
        model.setPassword(password);

        return JsonConverterUtil.convertToJson(model);
    }

}
