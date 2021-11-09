package edu.fontys.horecarobot.adminappbackend.dtos.request;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class LoginRequestModel {
    @NonNull
    private String email;
    @NonNull
    private String password;
}
