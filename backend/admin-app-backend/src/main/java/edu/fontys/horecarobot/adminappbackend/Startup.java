package edu.fontys.horecarobot.adminappbackend;

import edu.fontys.horecarobot.databaselibrary.models.AdminUser;
import edu.fontys.horecarobot.databaselibrary.repositories.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class Startup {

    @Value("${debug}")
    private boolean debug;
    private final AdminUserRepository adminUserRepository;

    @EventListener(ApplicationReadyEvent.class)
    public  void runAfterStartup(){
        if(debug)
            createUser();
    }

    private void createUser(){
        var email = "info.rigs.fontys@gmail.com";
        AdminUser exampleAdmin = new AdminUser();
        exampleAdmin.setEmail(email);
        Example<AdminUser> example = Example.of(exampleAdmin);

        Optional<AdminUser> optionalAdmin = adminUserRepository.findOne(example);
        if(optionalAdmin.isEmpty()){
            var admin = new AdminUser();
            admin.setEmail(email);
            admin.setPassword("AbC1@DeF");
            adminUserRepository.saveAndFlush(admin);
        }
    }

}
