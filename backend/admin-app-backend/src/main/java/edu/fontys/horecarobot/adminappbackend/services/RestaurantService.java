package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.RestaurantModel;
import edu.fontys.horecarobot.adminappbackend.dtos.WebsiteModel;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantInfo;
import edu.fontys.horecarobot.databaselibrary.repositories.RestaurantInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantInfoRepository restaurantInfoRepository;

    public void updateWebsiteInfo(WebsiteModel model) {
        var restaurantInfo = getRestaurantInfo();
        restaurantInfo.setPrimaryColor(model.getPrimaryColor());
        restaurantInfo.setSecondaryColor(model.getSecondaryColor());
        restaurantInfo.setBackgroundImage(model.getBackgroundImage());
        restaurantInfoRepository.saveAndFlush(restaurantInfo);
    }

    public void updateRestaurantInfo(RestaurantModel model) {
        var restaurantInfo = getRestaurantInfo();
        restaurantInfo.setName(model.getName());
        restaurantInfo.setRestaurantLogo(model.getRestaurantLogo());
        restaurantInfo.setContactPersonName(model.getContactPersonName());
        restaurantInfo.setContactPersonEmail(model.getContactPersonEmail());
        restaurantInfo.setContactPersonPhone(model.getContactPersonPhone());
        //restaurantInfo.setRestaurantDays(model.getRestaurantDays());

        restaurantInfoRepository.saveAndFlush(restaurantInfo);
    }

    public RestaurantModel getRestaurantModel() {
        return new RestaurantModel(getRestaurantInfo());
    }

    public WebsiteModel getWebsiteModel() {
        return new WebsiteModel(getRestaurantInfo());
    }

    private RestaurantInfo getRestaurantInfo() {
        var restaurantInfo = restaurantInfoRepository.getInfo();

        if(restaurantInfo.isEmpty())
        {
            var model = new RestaurantInfo();
            model.setName("");

            restaurantInfoRepository.saveAndFlush(model);
            return getRestaurantInfo();
        }

        return restaurantInfo.get();
    }
}
