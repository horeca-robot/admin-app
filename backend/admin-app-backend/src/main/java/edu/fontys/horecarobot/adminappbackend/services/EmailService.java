package edu.fontys.horecarobot.adminappbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {
    private final JavaMailSender javaMailSender;

    public void sendEmail(String userEmail, String link){
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(userEmail);
        mail.setSubject("Forgot password");
        mail.setText("Click this link to reset password " + link);

        javaMailSender.send(mail);
    }
}
