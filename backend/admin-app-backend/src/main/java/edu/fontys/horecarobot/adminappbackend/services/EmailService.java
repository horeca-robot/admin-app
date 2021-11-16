package edu.fontys.horecarobot.adminappbackend.services;

import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;

    public EmailService(JavaMailSender javaMailSender){
        this.javaMailSender = javaMailSender;
    }

    public void sendEmail(String userEmail, String link){
        SimpleMailMessage mail = new SimpleMailMessage();

        mail.setTo(userEmail);
        mail.setSubject("Forgot password again?");
        mail.setText("Here change it " + link);
        // add link to webpage to change password

        javaMailSender.send(mail);
    }
}
