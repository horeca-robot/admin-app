package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.RestaurantModel;
import edu.fontys.horecarobot.adminappbackend.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "http://localhost:4000")
@RequestMapping("api/restaurant")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping(path = "/save/settings")
    public ResponseEntity<ApiResponse> saveSettings(@RequestBody RestaurantModel model)
    {
        if(restaurantService.AddRestaurantInfo(model))
        {
            return ResponseEntity.ok(ApiResponse.ok("Settings have been saved!"));
        }

        return new ResponseEntity<>(ApiResponse.error("Settings could not be saved!"), HttpStatus.BAD_REQUEST);
    }

    @GetMapping(path = "load/settings")
    public ResponseEntity<ApiResponse> loadSettings()
    {
        var model = restaurantService.getRestaurantModel();

        if(model == null)
            return new ResponseEntity<>(ApiResponse.error("Model not found"), HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(ApiResponse.ok().addData("settings", model));
    }
}
