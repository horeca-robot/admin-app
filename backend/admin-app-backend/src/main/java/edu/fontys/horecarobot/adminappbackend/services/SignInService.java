package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.utilities.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class SignInService implements UserDetailsService {

    private final JwtUtil jwtUtil;

    @Autowired
    public SignInService(JwtUtil jwtUtil){
        this.jwtUtil = jwtUtil;
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        //TODO: Add connection to UserRepository in database library
        return new User("test", "test", new ArrayList<>());
    }

    public String generateJWT(UserDetails userDetails){
        return jwtUtil.generateToken(userDetails);
    }
}
