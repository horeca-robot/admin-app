package edu.fontys.horecarobot.adminappbackend.dtos;

import edu.fontys.horecarobot.databaselibrary.models.RestaurantInfo;
import lombok.Data;

import java.time.LocalTime;

@Data
public class RestaurantModel {

    private String name;
    private String restaurantLogo;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private String contactPersonName;
    private String contactPersonEmail;
    private String contactPersonPhone;

    public static RestaurantModel from(RestaurantInfo model)
    {
        var restaurantModel = new RestaurantModel();

        restaurantModel.setName(model.getName());
        restaurantModel.setRestaurantLogo(model.getRestaurantLogo());
        restaurantModel.setOpeningTime(model.getOpeningTime());
        restaurantModel.setClosingTime(model.getClosingTime());
        restaurantModel.setContactPersonName(model.getContactPersonName());
        restaurantModel.setContactPersonEmail(model.getContactPersonEmail());
        restaurantModel.setContactPersonPhone(model.getContactPersonPhone());

        return  restaurantModel;
    }
}
