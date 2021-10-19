package edu.fontys.horecarobot.adminappbackend.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import edu.fontys.horecarobot.adminappbackend.dtos.RobotModel;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
public class RobotService {

    //TODO: Add RobotRepository object

    public boolean doesUserHaveAccessToRobot(String id){

        //TODO: Add check to confirm the users ownership on robot with timer of one minute
        return true;
    }

    public boolean doesRobotExist(String id){

        //TODO: Add check to confirm the robots existence in the database
        return true;
    }

    public ArrayList<RobotModel> getRobots(){

        //TODO: Get robots from database with use of repository
        ArrayList<RobotModel> robots = new ArrayList<RobotModel>();
        robots.add(new RobotModel("test1", "test1"));
        robots.add(new RobotModel("test2", "test2"));
        robots.add(new RobotModel("test3", "test3"));
        robots.add(new RobotModel("test4", "test4"));
        robots.add(new RobotModel("test5", "test5"));
        return robots;
    }

    public void addRobot(RobotModel robotModel){

        //TODO: Add robot to database with use of repository
    }

    public void removeRobot(String id){

        //TODO: Remove robot from database with use of repository
    }
}
