package edu.fontys.horecarobot.adminappbackend.dtos;

import edu.fontys.horecarobot.databaselibrary.models.RestaurantInfo;
import lombok.Data;

@Data
public class WebsiteModel {
    private String primaryColor;
    private String secondaryColor;
    private String backgroundImage;

    public  static WebsiteModel from(RestaurantInfo model)
    {
        var websiteModel = new WebsiteModel();

        websiteModel.setPrimaryColor(model.getPrimaryColor());
        websiteModel.setSecondaryColor(model.getSecondaryColor());
        websiteModel.setBackgroundImage(model.getBackgroundImage());

        return  websiteModel;
    }
}
