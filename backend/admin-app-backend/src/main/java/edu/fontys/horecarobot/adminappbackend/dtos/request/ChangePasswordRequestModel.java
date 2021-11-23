package edu.fontys.horecarobot.adminappbackend.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ChangePasswordRequestModel {
    @NonNull
    private String email;
    @NonNull
    private String password;
    @NonNull
    private String token;
}
