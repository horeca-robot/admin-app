package edu.fontys.horecarobot.adminappbackend.dtos;

import lombok.Data;

@Data
public class LoginModel {
    private String email;
    private String password;
}
