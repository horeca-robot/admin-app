package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.LoginModel;
import edu.fontys.horecarobot.adminappbackend.services.PasswordService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/password")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4000")
public class PasswordController {

    private final PasswordService passwordService;

    @PostMapping
    public ResponseEntity<?> resetPasswordLink(@RequestBody LoginModel model){
        passwordService.generateResetLink(model.getEmail());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<?> changePassword(@RequestBody LoginModel login){
        passwordService.changePassword(login.getEmail(), login.getPassword());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
