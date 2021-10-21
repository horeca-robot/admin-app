package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.utilities.JwtUtil;
import edu.fontys.horecarobot.databaselibrary.models.AdminUser;
import edu.fontys.horecarobot.databaselibrary.repositories.AdminUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Example;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SignInService implements UserDetailsService {

    private final JwtUtil jwtUtil;
    private final AdminUserRepository adminUserRepository;

    @Value("${debug}")
    private boolean debug;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        AdminUser exampleAdmin = new AdminUser();
        exampleAdmin.setEmail(userName);
        Example<AdminUser> example = Example.of(exampleAdmin);

        Optional<AdminUser> optionalAdmin = adminUserRepository.findOne(example);

        if (optionalAdmin.isPresent()) {
            AdminUser admin = optionalAdmin.get();
            return new User(admin.getEmail(), admin.getPassword(), new ArrayList<>());
        } else if (debug && userName.equalsIgnoreCase("test")) {
            return new User("test", "test", new ArrayList<>());
        } else {
            throw new UsernameNotFoundException("User with username (email) not found!");
        }
    }

    public String generateJWT(UserDetails userDetails){
        return jwtUtil.generateToken(userDetails);
    }
}
