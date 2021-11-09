package edu.fontys.horecarobot.adminappbackend.dtos.response;

import edu.fontys.horecarobot.databaselibrary.models.Robot;
import lombok.Data;

@Data
public class RobotResponseModel {

    public RobotResponseModel(Robot robot) {
        id = robot.getId();
        name = robot.getName();
    }

    private String id;
    private String name;

}
