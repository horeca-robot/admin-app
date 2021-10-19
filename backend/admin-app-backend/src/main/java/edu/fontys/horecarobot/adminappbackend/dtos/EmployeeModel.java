package edu.fontys.horecarobot.adminappbackend.dtos;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class EmployeeModel {
    private String username;
    private String pin;

    //testcode
    public void setUsername(String username) {
        this.username = username;
    }

    //testcode
    public void setPin(String pin) {
        this.pin = pin;
    }
}