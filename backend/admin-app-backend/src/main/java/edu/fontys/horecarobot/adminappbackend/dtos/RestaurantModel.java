package edu.fontys.horecarobot.adminappbackend.dtos;

import edu.fontys.horecarobot.databaselibrary.models.RestaurantInfo;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
public class RestaurantModel {

    private String name;
    private String restaurantLogo;
    private String contactPersonName;
    private String contactPersonEmail;
    private String contactPersonPhone;
    private List<RestaurantDayModel> restaurantDays;

    public RestaurantModel(RestaurantInfo model) {
        name = model.getName();
        restaurantLogo = model.getRestaurantLogo();
        contactPersonName = model.getContactPersonName();
        contactPersonEmail = model.getContactPersonEmail();
        contactPersonPhone = model.getContactPersonPhone();
        //restaurantDays = model.getRestaurantDays();
    }
}
