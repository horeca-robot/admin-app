package edu.fontys.horecarobot.adminappbackend.dtos;

import javax.persistence.Entity;
import javax.persistence.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@Entity
@Table(name = "employees")
public class EmployeeModel {
    private String username;
    private short pin;
}
