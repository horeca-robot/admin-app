package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.RobotModel;
import edu.fontys.horecarobot.adminappbackend.services.RobotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping(path = "api/Robot")
@CrossOrigin(origins = "http://localhost:4000")
@RequiredArgsConstructor
public class RobotController {

    private final RobotService robotService;

    @GetMapping
    public ResponseEntity<ApiResponse> getRobots(){
        try{
            ArrayList<RobotModel> robots = robotService.getRobots();
            return new ResponseEntity<>(ApiResponse.ok().addData("robots", robots), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(ApiResponse.error("Something went wrong while retrieving robots from database. Please try again later.").addData("exception", e), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> addRobot(@RequestBody RobotModel robotModel){
        if(robotModel.getId().isBlank() || robotModel.getName().isBlank())
            return new ResponseEntity<>(ApiResponse.error("Not all required fields are filled in."), HttpStatus.BAD_REQUEST);

        if(!robotService.doesUserHaveAccessToRobot(robotModel.getId()))
            return new ResponseEntity<>(ApiResponse.error("You were unable to confirm your ownership."), HttpStatus.BAD_REQUEST);

        try{
            robotService.addRobot(robotModel);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(ApiResponse.error("Something went wrong while trying to connect the robot to your restaurant. Please try again later.").addData("exception", e), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> removeRobot(@PathVariable String id){
        if(id.isBlank())
            return new ResponseEntity<>(ApiResponse.error("Not all required fields are filled in."), HttpStatus.BAD_REQUEST);

        if(!robotService.doesRobotExist(id))
            return new ResponseEntity<>(ApiResponse.error("Can't locate robot in database."), HttpStatus.BAD_REQUEST);

        try{
            robotService.removeRobot(id);
            return new ResponseEntity<>(ApiResponse.ok("Successfully deleted the robot from the restaurant."), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(ApiResponse.error("Something went wrong while trying to delete the robot from your restaurant. Please try again later.").addData("exception", e), HttpStatus.BAD_REQUEST);
        }
    }
}
