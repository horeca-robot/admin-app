package edu.fontys.horecarobot.adminappbackend.dtos.request;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class RobotRequestModel {
    @NonNull
    private String id;
    @NonNull
    private String name;
}
