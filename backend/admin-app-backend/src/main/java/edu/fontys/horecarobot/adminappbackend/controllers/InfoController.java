package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.RestaurantModel;
import edu.fontys.horecarobot.adminappbackend.dtos.WebsiteModel;
import edu.fontys.horecarobot.adminappbackend.dtos.response.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.services.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "http://localhost:4000")
@RequestMapping("api/info")
@RequiredArgsConstructor
public class InfoController {

    private final RestaurantService restaurantService;

    @GetMapping(path = "/website")
    public ResponseEntity<ApiResponse> getWebsiteInfo() {
        WebsiteModel websiteModel;
        try {
            websiteModel = restaurantService.getWebsiteModel();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        return ResponseEntity.ok(ApiResponse.ok().addData("info", websiteModel));
    }

    @GetMapping(path = "/restaurant")
    public ResponseEntity<ApiResponse> getRestaurantInfo() {
        RestaurantModel restaurantModel;
        try {
            restaurantModel = restaurantService.getRestaurantModel();
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        return ResponseEntity.ok(ApiResponse.ok().addData("info", restaurantModel));
    }

    @PutMapping(path = "/website")
    public ResponseEntity<ApiResponse> putWebsiteSettings(@RequestBody WebsiteModel model) {
        try {
            restaurantService.updateWebsiteInfo(model);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        return ResponseEntity.noContent().build();
    }

    @PutMapping(path = "/restaurant")
    public ResponseEntity<ApiResponse> putRestaurantSettings(@RequestBody RestaurantModel model) {
        try {
            restaurantService.updateRestaurantInfo(model);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        return ResponseEntity.noContent().build();
    }
}
