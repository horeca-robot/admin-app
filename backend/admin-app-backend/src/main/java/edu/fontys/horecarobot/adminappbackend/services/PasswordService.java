package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.utilities.JwtUtil;
import edu.fontys.horecarobot.databaselibrary.models.AdminUser;
import edu.fontys.horecarobot.databaselibrary.repositories.AdminUserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PasswordService {

    private final AdminUserRepository adminUserRepository;
    private final EmailService emailService;
    private final JwtUtil jwtUtil;
    private final static long EXPIRATION_TIME = 1000 * 60 * 30;

    public boolean sendResetLink(String email){

        try{
            final UserDetails userDetails = getAdminUser(email);
            final String jwt = jwtUtil.generateToken(userDetails, EXPIRATION_TIME);

            String resetLink = "http://localhost:4000/forgot-password?token=" + jwt;
            emailService.sendEmail(email, resetLink);
            return true;
        }
        catch(BadCredentialsException e){
            return false;
        }
    }

    public boolean changePassword(String email, String password, String token){
        if(isValidModel(email, token)){
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
        }
        return false;
    }

    @NonNull
    private UserDetails getAdminUser(String email){
        AdminUser exampleAdmin = new AdminUser();
        exampleAdmin.setEmail(email);
        Example<AdminUser> example = Example.of(exampleAdmin);

        Optional<AdminUser> optionalAdmin = adminUserRepository.findOne(example);

        if(optionalAdmin.isPresent()){
            AdminUser admin = optionalAdmin.get();
            return new User(admin.getEmail(), admin.getPassword(), new ArrayList<>());
        }
        return null;
    }

    private boolean isValidModel(String email, String token){
        var username = jwtUtil.extractUsername(token);
        if(username != email)
            return false;
        return true;
    }
}
