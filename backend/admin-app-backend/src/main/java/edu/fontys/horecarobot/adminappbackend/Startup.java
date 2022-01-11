package edu.fontys.horecarobot.adminappbackend;

import edu.fontys.horecarobot.adminappbackend.security.SecurityConfigurer;
import edu.fontys.horecarobot.databaselibrary.models.AdminUser;
import edu.fontys.horecarobot.databaselibrary.repositories.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Component
@RequiredArgsConstructor
@Service
public class Startup {

    @Value("${demo}")
    private boolean demo;
    @Value("${admin.email}")
    private String email;
    @Value("${admin.password}")
    private String password;
    private final AdminUserRepository adminUserRepository;
    private final SecurityConfigurer securityConfigurer;

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup(){
        if(demo)
            createUser();
    }

    private void createUser(){
        AdminUser exampleAdmin = new AdminUser();
        exampleAdmin.setEmail(email);
        Example<AdminUser> example = Example.of(exampleAdmin);

        Optional<AdminUser> optionalAdmin = adminUserRepository.findOne(example);
        if(optionalAdmin.isEmpty()){
            var admin = new AdminUser();
            admin.setEmail(email);
            admin.setPassword(securityConfigurer.passwordEncoder().encode(password));
            adminUserRepository.saveAndFlush(admin);
        }
    }

}
