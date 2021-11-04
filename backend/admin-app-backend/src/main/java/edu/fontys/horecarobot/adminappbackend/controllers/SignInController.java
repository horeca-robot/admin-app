package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.LoginModel;
import edu.fontys.horecarobot.adminappbackend.services.SignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/SignIn")
@CrossOrigin(origins = "http://localhost:4000")
@RequiredArgsConstructor
public class SignInController {

    private final SignInService signInService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/authenticate")
    public ResponseEntity<ApiResponse> authenticate(@RequestBody LoginModel loginModel){
        if(loginModel.getEmail().isBlank() || loginModel.getPassword().isBlank())
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.REQUIRED_FIELDS_ERROR), HttpStatus.BAD_REQUEST);

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginModel.getEmail(),
                            loginModel.getPassword()
                    )
            );

            final UserDetails userDetails = signInService.loadUserByUsername(loginModel.getEmail());
            final String jwt = signInService.generateJWT(userDetails);
            return new ResponseEntity<>(ApiResponse.ok().addData("jwt", jwt), HttpStatus.OK);
        }
        catch(BadCredentialsException e) {
            return new ResponseEntity<>(ApiResponse.error("Incorrect email or password."), HttpStatus.UNAUTHORIZED);
        }

    }

}
