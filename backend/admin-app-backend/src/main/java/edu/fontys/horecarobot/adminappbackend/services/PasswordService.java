package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.utilities.JwtUtil;
import edu.fontys.horecarobot.databaselibrary.models.AdminUser;
import edu.fontys.horecarobot.databaselibrary.repositories.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PasswordService {

    private final AdminUserRepository adminUserRepository;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    private final long expirationTime = (60 * 60) * 30;

    public boolean generateResetLink(String email){

        try{
            createUser(email);

            final UserDetails userDetails = getAdminUser(email);
            final String jwt = jwtUtil.generateToken(userDetails, expirationTime);

            String resetLink = "http://localhost:8080/reset-password/token=" + jwt;
            emailService.sendEmail(email, resetLink);
            return true;
        }
        catch(BadCredentialsException e){
            return false;
        }
    }

    public boolean changePassword(String email, String password){
        AdminUser exampleAdmin = new AdminUser();
        exampleAdmin.setEmail(email);
        Example<AdminUser> example = Example.of(exampleAdmin);
        Optional<AdminUser> optionalAdmin = adminUserRepository.findOne(example);

        if(optionalAdmin.isPresent()) {
            var admin = optionalAdmin.get();
            admin.setPassword(password);
            adminUserRepository.saveAndFlush(admin);
            return true;
        }
        return false;
    }

    private UserDetails getAdminUser(String email){
        AdminUser exampleAdmin = new AdminUser();
        exampleAdmin.setEmail(email);
        Example<AdminUser> example = Example.of(exampleAdmin);

        Optional<AdminUser> optionalAdmin = adminUserRepository.findOne(example);

        if(optionalAdmin.isPresent()){
            AdminUser admin = optionalAdmin.get();
            return new User(admin.getEmail(), admin.getPassword(), new ArrayList<>());
        }
        else{
            return null;
            //throw new UsernameNotFoundException("User with username (email) not found!");
        }
    }

    //DON'T PUSH TO DEVELOP:
    private void createUser(String email){
        AdminUser exampleAdmin = new AdminUser();
        exampleAdmin.setEmail(email);
        Example<AdminUser> example = Example.of(exampleAdmin);

        Optional<AdminUser> optionalAdmin = adminUserRepository.findOne(example);
        if(optionalAdmin.isEmpty()) {
            var admin = new AdminUser();
            admin.setEmail(email);
            admin.setPassword("test");
            adminUserRepository.saveAndFlush(admin);
        }
    }
}
