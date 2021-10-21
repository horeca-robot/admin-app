package edu.fontys.horecarobot.adminappbackend.dtos;

import edu.fontys.horecarobot.databaselibrary.models.RestaurantInfo;
import lombok.Data;

import java.time.LocalTime;

@Data
public class RestaurantModel {

    private String name;

    private String primaryColor;

    private String secondaryColor;

    private String restaurantLogo;

    private String backgroundImage;

    private LocalTime openingTime;

    private LocalTime closingTime;

    private String contactPersonName;

    private String contactPersonEmail;

    private String contactPersonPhone;
}
