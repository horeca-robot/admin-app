package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.RobotModel;
import edu.fontys.horecarobot.adminappbackend.services.RobotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/Robot")
@CrossOrigin(origins = "http://localhost:4000")
@RequiredArgsConstructor
public class RobotController {

    private final RobotService robotService;

    @GetMapping
    public ResponseEntity<ApiResponse> getRobots(){
        try{
            var robots = robotService.getRobots();
            return new ResponseEntity<>(ApiResponse.ok().addData("robots", robots), HttpStatus.OK);
        }
        catch(Exception e){
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.DATABASE_CONNECTION_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> postRobot(@RequestBody RobotModel robotModel){
        if(robotModel.getId().isBlank() || robotModel.getName().isBlank())
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.REQUIRED_FIELDS_ERROR), HttpStatus.BAD_REQUEST);

        if(!robotService.doesUserHaveAccessToRobot(robotModel.getId()))
            return new ResponseEntity<>(ApiResponse.error("You were unable to confirm your ownership."), HttpStatus.BAD_REQUEST);

        try{
            robotService.addRobot(robotModel);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.DATABASE_CONNECTION_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> putRobot(@PathVariable String id, @RequestBody RobotModel robotModel){
        if(!robotService.doesRobotExist(id))
            return new ResponseEntity<>(ApiResponse.error("Can't locate robot in database."), HttpStatus.NOT_FOUND);

        if(robotModel.getId().isBlank() || robotModel.getName().isBlank())
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.REQUIRED_FIELDS_ERROR), HttpStatus.BAD_REQUEST);

        if(id.equals(robotModel.getId()))
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.ID_ALIGN_ERROR), HttpStatus.BAD_REQUEST);

        try{
            robotService.updateRobot(robotModel);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch(Exception e){
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.DATABASE_CONNECTION_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRobot(@PathVariable String id){
        if(id.isBlank())
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.REQUIRED_FIELDS_ERROR), HttpStatus.BAD_REQUEST);

        if(!robotService.doesRobotExist(id))
            return new ResponseEntity<>(ApiResponse.error("Can't locate robot in database."), HttpStatus.BAD_REQUEST);

        try{
            robotService.deleteRobot(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e){
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.DATABASE_CONNECTION_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
