package edu.fontys.horecarobot.adminappbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.UUID;

@Data
@AllArgsConstructor
public class EmployeeModel {
    private UUID id;
    @NonNull
    private String username;
    @NonNull
    private short pin;
}
