package edu.fontys.horecarobot.adminappbackend.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

@Data
@NoArgsConstructor
public class ResetPasswordRequestModel {
    @NonNull
    private String email;
}
