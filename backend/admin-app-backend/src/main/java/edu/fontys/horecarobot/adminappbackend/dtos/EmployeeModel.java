package edu.fontys.horecarobot.adminappbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
public class EmployeeModel {
    @NonNull
    private String username;
    @NonNull
    private short pin;
}
