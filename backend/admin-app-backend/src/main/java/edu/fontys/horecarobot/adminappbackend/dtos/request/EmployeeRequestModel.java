package edu.fontys.horecarobot.adminappbackend.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Data
@NoArgsConstructor
public class EmployeeRequestModel {
    @NonNull
    private String username;
    @NonNull
    private short pincode;
}
