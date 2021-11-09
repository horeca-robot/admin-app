package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.RobotModel;
import edu.fontys.horecarobot.adminappbackend.services.RobotService;
import edu.fontys.horecarobot.databaselibrary.models.Robot;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/robot")
@CrossOrigin(origins = "http://localhost:4000")
@RequiredArgsConstructor
public class RobotController {

    private final RobotService robotService;

    @GetMapping
    public ResponseEntity<ApiResponse> getRobots() {
        List<Robot> robots;
        try {
            robots = robotService.getRobots();
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        return ResponseEntity.ok(ApiResponse.ok().addData("robots", robots));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> postRobot(@RequestBody RobotModel robotModel) {
        if(robotModel.getId().isBlank() || robotModel.getName().isBlank())
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);

        if(!robotService.doesUserHaveAccessToRobot(robotModel.getId()))
            return ResponseEntity.badRequest().body(ApiResponse.error("You were unable to confirm your ownership."));

        try {
            robotService.addRobot(robotModel);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        // TODO: Change response entity to contain uri
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> putRobot(@PathVariable String id, @RequestBody RobotModel robotModel) {
        if(!robotService.doesRobotExist(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Can't locate robot in database."));

        if(robotModel.getId().isBlank() || robotModel.getName().isBlank())
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);

        if(id.equals(robotModel.getId()))
            return ResponseEntity.badRequest().body(ApiResponse.ID_ALIGN_ERROR);

        try {
            robotService.updateRobot(robotModel);
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        // TODO: Change response entity to contain uri
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteRobot(@PathVariable String id) {
        if(id.isBlank())
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);

        if(!robotService.doesRobotExist(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Can't locate robot in database."));

        try {
            robotService.deleteRobot(id);
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        return ResponseEntity.noContent().build();
    }
}
