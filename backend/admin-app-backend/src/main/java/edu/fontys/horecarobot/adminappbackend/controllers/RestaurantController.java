package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.RestaurantModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:4000")
@RequestMapping("api/restaurant")
public class RestaurantController {

    @PostMapping(path = "/save/settings")
    public ResponseEntity<ApiResponse> saveSettings(@RequestBody RestaurantModel model)
    {
        // TODO check if some of the data is null or unchanged with saved version
        // TODO save to database with db library
        return ResponseEntity.ok(ApiResponse.ok("Settings have been saved!").addData("name",model.getName()));
    }

}
