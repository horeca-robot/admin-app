package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.response.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.request.LoginRequestModel;
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
@RequestMapping(path = "api/sign-in")
@CrossOrigin(origins = "http://178.62.217.137:8081")
@RequiredArgsConstructor
public class SignInController {

    private final SignInService signInService;
    private final AuthenticationManager authenticationManager;

    @PostMapping
    public ResponseEntity<ApiResponse> authenticate(@RequestBody LoginRequestModel loginModel) {
        if(loginModel.getEmail().isBlank() || loginModel.getPassword().isBlank())
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);

        String jwt;

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginModel.getEmail(),
                            loginModel.getPassword()
                    )
            );

            final UserDetails userDetails = signInService.loadUserByUsername(loginModel.getEmail());
            jwt = signInService.generateJWT(userDetails);
        }
        catch(BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ApiResponse.error("Incorrect email or password."));
        }

        return ResponseEntity.ok(ApiResponse.ok().addData("jwt", jwt));
    }

}
