package edu.fontys.horecarobot.adminappbackend.dtos.request;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class EmployeeRequestModel {
    @NonNull
    private String username;
    @NonNull
    private short pin;
}
