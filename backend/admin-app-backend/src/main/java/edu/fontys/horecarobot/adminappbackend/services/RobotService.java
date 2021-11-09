package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.response.RobotResponseModel;
import edu.fontys.horecarobot.databaselibrary.models.Robot;
import edu.fontys.horecarobot.databaselibrary.repositories.RobotRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import edu.fontys.horecarobot.adminappbackend.dtos.request.RobotRequestModel;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<RobotResponseModel> getRobots(){
        return robotRepository.findAll()
                .stream()
                .map(RobotResponseModel::new)
                .collect(Collectors.toList());
    }

    public RobotResponseModel addRobot(RobotRequestModel robotRequestModel){
        var r = robotRepository.saveAndFlush(convertFromRobotModel(robotRequestModel));
        return new RobotResponseModel(r);
    }

    public void updateRobot(RobotRequestModel robotRequestModel){
        robotRepository.saveAndFlush(convertFromRobotModel(robotRequestModel));
    }

    public void deleteRobot(String id){
        robotRepository.deleteById(id);
    }

    private Robot convertFromRobotModel(RobotRequestModel robotRequestModel){
        return new Robot(robotRequestModel.getId(), robotRequestModel.getName());
    }
}
