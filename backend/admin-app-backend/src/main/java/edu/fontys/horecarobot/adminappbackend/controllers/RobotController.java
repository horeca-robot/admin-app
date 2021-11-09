package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.response.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.request.RobotRequestModel;
import edu.fontys.horecarobot.adminappbackend.dtos.response.RobotResponseModel;
import edu.fontys.horecarobot.adminappbackend.services.RobotService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(path = "api/robot")
@CrossOrigin(origins = "http://localhost:4000")
@RequiredArgsConstructor
public class RobotController {

    private final RobotService robotService;

    @GetMapping
    public ResponseEntity<ApiResponse> getRobots() {
        List<RobotResponseModel> robots;
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
    public ResponseEntity<ApiResponse> postRobot(@RequestBody RobotRequestModel robotRequestModel) {
        if(robotRequestModel.getId().isBlank() || robotRequestModel.getName().isBlank())
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);

        if(!robotService.doesUserHaveAccessToRobot(robotRequestModel.getId()))
            return ResponseEntity.badRequest().body(ApiResponse.error("You were unable to confirm your ownership."));

        RobotResponseModel robotResponseModel;
        try {
            robotResponseModel = robotService.addRobot(robotRequestModel);
        } catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        var uri = URI.create("api/robot/" + robotResponseModel.getId());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> putRobot(@PathVariable String id, @RequestBody RobotRequestModel robotRequestModel) {
        if(!robotService.doesRobotExist(id))
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ApiResponse.error("Can't locate robot in database."));

        if(robotRequestModel.getId().isBlank() || robotRequestModel.getName().isBlank())
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);

        if(id.equals(robotRequestModel.getId()))
            return ResponseEntity.badRequest().body(ApiResponse.ID_ALIGN_ERROR);

        try {
            robotService.updateRobot(robotRequestModel);
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        return ResponseEntity.noContent().build();
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
