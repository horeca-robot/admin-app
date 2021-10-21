package edu.fontys.horecarobot.adminappbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.NonNull;

@Data
@AllArgsConstructor
public class RobotModel {
    @NonNull
    private String id;
    @NonNull
    private String name;
}
