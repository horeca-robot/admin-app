package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.request.ChangePasswordRequestModel;
import edu.fontys.horecarobot.adminappbackend.dtos.request.ResetPasswordRequestModel;
import edu.fontys.horecarobot.adminappbackend.services.PasswordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/password")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4000")
public class PasswordController {

    private final PasswordService passwordService;

    @PostMapping
    public ResponseEntity<?> postResetPasswordRequest(@RequestBody ResetPasswordRequestModel model){
        var result = passwordService.sendResetLink(model.getEmail());
        if(result)
            return ResponseEntity.noContent().build();
        return ResponseEntity.notFound().build();
    }

    @PutMapping
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequestModel model){
        passwordService.changePassword(model.getEmail(), model.getPassword(), model.getToken());
        return ResponseEntity.noContent().build();
    }
}
