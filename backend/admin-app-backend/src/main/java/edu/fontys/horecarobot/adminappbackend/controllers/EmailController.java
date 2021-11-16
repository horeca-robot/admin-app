package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.services.CategoryService;
import edu.fontys.horecarobot.adminappbackend.services.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/mailService")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4000")
public class EmailController {

    private final EmailService emailService;

    @GetMapping
    public String sendEmail(String email){
        //emailService.sendEmail(email);
        return "email send ";
    }
}
