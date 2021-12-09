package edu.fontys.horecarobot.adminappbackend.dtos;

import edu.fontys.horecarobot.databaselibrary.models.RestaurantInfo;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Data
@NoArgsConstructor
public class RestaurantModel {

    private String name;
    private String restaurantLogo;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String contactPersonName;
    private String contactPersonEmail;
    private String contactPersonPhone;

    public RestaurantModel(RestaurantInfo model) {
        name = model.getName();
        restaurantLogo = model.getRestaurantLogo();
        openingTime = model.getOpeningTime();
        closingTime = model.getClosingTime();
        contactPersonName = model.getContactPersonName();
        contactPersonEmail = model.getContactPersonEmail();
        contactPersonPhone = model.getContactPersonPhone();
    }

}
