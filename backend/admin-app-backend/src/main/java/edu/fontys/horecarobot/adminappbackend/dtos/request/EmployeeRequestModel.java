package edu.fontys.horecarobot.adminappbackend.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
public class EmployeeRequestModel {
    @NonNull
    private String username;
    @NonNull
    private short pin;
}
