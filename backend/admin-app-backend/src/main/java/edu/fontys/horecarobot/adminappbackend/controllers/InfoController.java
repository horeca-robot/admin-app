package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.RestaurantModel;
import edu.fontys.horecarobot.adminappbackend.dtos.WebsiteModel;
import edu.fontys.horecarobot.adminappbackend.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4000")
@RequestMapping("api/info")
@RequiredArgsConstructor
public class InfoController {

    private final RestaurantService restaurantService;

    @GetMapping(path = "/website")
    public ResponseEntity<ApiResponse> getWebsiteInfo()
    {
        var websiteModel = restaurantService.getWebsiteModel();

        return new ResponseEntity<>(ApiResponse.ok().addData("info",websiteModel),HttpStatus.OK);
    }

    @GetMapping(path = "/restaurant")
    public ResponseEntity<ApiResponse> getRestaurantInfo()
    {
        var restaurantModel = restaurantService.getRestaurantModel();

        return new ResponseEntity<>(ApiResponse.ok().addData("info",restaurantModel),HttpStatus.OK);
    }

    @PutMapping(path = "/website")
    public ResponseEntity<ApiResponse> putWebsiteSettings(@RequestBody WebsiteModel model)
    {
        if(!restaurantService.updateWebsiteInfo(model))
        {
            return new ResponseEntity<>(ApiResponse.error(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(ApiResponse.ok(), HttpStatus.OK);
    }

    @PutMapping(path = "/restaurant")
    public ResponseEntity<ApiResponse> putRestaurantSettings(@RequestBody RestaurantModel model)
    {
        if(!restaurantService.updateRestaurantInfo(model))
        {
            return new ResponseEntity<>(ApiResponse.error(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(ApiResponse.ok(), HttpStatus.OK);
    }
}
