package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.databaselibrary.models.Robot;
import edu.fontys.horecarobot.databaselibrary.repositories.RobotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import edu.fontys.horecarobot.adminappbackend.dtos.RobotModel;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RobotService {

    private final RobotRepository robotRepository;

    //TODO: Add check to confirm the users ownership on robot with timer of one minute
    public boolean doesUserHaveAccessToRobot(String id){
        return true;
    }

    public boolean doesRobotExist(String id){
        return robotRepository.findById(id).isPresent();
    }

    public List<Robot> getRobots(){
        return robotRepository.findAll();
    }

    public void addRobot(RobotModel robotModel){
        robotRepository.saveAndFlush(convertFromRobotModel(robotModel));
    }

    public void updateRobot(RobotModel robotModel){
        robotRepository.saveAndFlush(convertFromRobotModel(robotModel));
    }

    public void deleteRobot(String id){
        robotRepository.deleteById(id);
    }

    private Robot convertFromRobotModel(RobotModel robotModel){
        return new Robot(robotModel.getId(), robotModel.getName());
    }
}
