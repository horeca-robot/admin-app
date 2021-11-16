package edu.fontys.horecarobot.adminappbackend.dtos;

import edu.fontys.horecarobot.databaselibrary.models.RestaurantInfo;
import lombok.Data;

@Data
public class WebsiteModel {
    private String primaryColor;
    private String secondaryColor;
    private String backgroundImage;

    public WebsiteModel(RestaurantInfo model) {
        primaryColor = model.getPrimaryColor();
        secondaryColor = model.getSecondaryColor();
        backgroundImage = model.getBackgroundImage();
    }

}
