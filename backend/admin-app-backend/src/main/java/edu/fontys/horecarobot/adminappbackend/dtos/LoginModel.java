package edu.fontys.horecarobot.adminappbackend.dtos;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class LoginModel {
    @NonNull
    private String email;
    @NonNull
    private String password;
}
