package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.AdminAppBackendApplication;
import edu.fontys.horecarobot.adminappbackend.dtos.request.ProductRequestModel;
import edu.fontys.horecarobot.adminappbackend.utilities.JsonConverterUtil;
import edu.fontys.horecarobot.adminappbackend.utilities.JwtUtil;
import edu.fontys.horecarobot.databaselibrary.models.AdminUser;
import edu.fontys.horecarobot.databaselibrary.repositories.AdminUserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = AdminAppBackendApplication.class)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductControllerTest {

    @Autowired
    private MockMvc mvc;
    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private JwtUtil jwtUtil;
    private String token;

    @BeforeAll
    public void setUp(){
        createAccount();
    }

    @Test
    public void get_all_Products() throws Exception{
        mvc.perform(get("/api/product")
                .header("authorization", "Bearer " + token)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void post_new_product() throws Exception{
        String json = JsonConverterUtil.convertToJson(product());

        mvc.perform(post("/api/product")
                .header("authorization", "Bearer " + token)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated());
    }

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AdminUser exampleAdmin = new AdminUser();
        exampleAdmin.setEmail(userName);
        Example<AdminUser> example = Example.of(exampleAdmin);

        Optional<AdminUser> optionalAdmin = adminUserRepository.findOne(example);

        if (optionalAdmin.isPresent()) {
            AdminUser admin = optionalAdmin.get();
            return new User(admin.getEmail(), admin.getPassword(), new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User with username (email) not found!");
        }
    }

    public void createAccount(){
        var user = new AdminUser();
        user.setPassword("Test");
        user.setEmail("test@gmail.com");
        adminUserRepository.saveAndFlush(user);

        final UserDetails userDetails = loadUserByUsername(user.getEmail());
        token = jwtUtil.generateToken(userDetails);
    }

    public ProductRequestModel product(){
        ProductRequestModel product = new ProductRequestModel();
        product.setName("Bread");
        product.setPrice(5);
        return product;
    }

}
