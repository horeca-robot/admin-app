package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.LoginModel;
import edu.fontys.horecarobot.adminappbackend.services.SignInService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping(path = "api/SignIn")
public class SignInController {

    private final SignInService signInService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public SignInController(SignInService signInService, AuthenticationManager authenticationManager) {
        this.signInService = signInService;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping("/test")
    public String test(){
        return "It Works! :)";
    }

    @PostMapping("/authenticate")
    public ApiResponse authenticate(@RequestBody LoginModel loginModel) throws Exception{
        if(loginModel.getEmail().isBlank() || loginModel.getPassword().isBlank())
            return ApiResponse.Error(Optional.of("Not all fields are filled in."));

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginModel.getEmail(), loginModel.getPassword()));
        }
        catch(BadCredentialsException e) {
            throw new Exception("Incorrect email or password", e);
        }

        final UserDetails userDetails = signInService.loadUserByUsername(loginModel.getEmail());
        final String jwt = signInService.generateJWT(userDetails);
        return ApiResponse.Ok(Optional.of("Success")).AddData("jwt", jwt);
    }
}
