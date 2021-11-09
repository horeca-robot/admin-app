package edu.fontys.horecarobot.adminappbackend.dtos.response;

import edu.fontys.horecarobot.databaselibrary.models.EmployeeUser;
import lombok.Data;

import java.util.UUID;

@Data
public class EmployeeResponseModel {

    public EmployeeResponseModel(EmployeeUser employeeUser) {
        id = employeeUser.getId();
        username = employeeUser.getUsername();
        pin = employeeUser.getPincode();
    }

    private final UUID id;
    private final String username;
    private final short pin;

}
