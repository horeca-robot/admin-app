package edu.fontys.horecarobot.adminappbackend.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
public class LoginRequestModel {
    @NonNull
    private String email;
    @NonNull
    private String password;
}
