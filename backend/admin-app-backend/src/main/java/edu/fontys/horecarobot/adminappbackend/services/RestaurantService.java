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

    public boolean updateWebsiteInfo(WebsiteModel model)
    {
        var restaurantInfo = getRestaurantInfo();

        try {
            restaurantInfo.setPrimaryColor(model.getPrimaryColor());
            restaurantInfo.setSecondaryColor(model.getSecondaryColor());
            restaurantInfo.setBackgroundImage(model.getBackgroundImage());
            restaurantInfoRepository.saveAndFlush(restaurantInfo);

            return  true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public boolean updateRestaurantInfo(RestaurantModel model)
    {
        var restaurantInfo = getRestaurantInfo();

        try {
            restaurantInfo.setName(model.getName());
            restaurantInfo.setRestaurantLogo(model.getRestaurantLogo());
            restaurantInfo.setOpeningTime(model.getOpeningTime());
            restaurantInfo.setClosingTime(model.getClosingTime());
            restaurantInfo.setContactPersonName(model.getContactPersonName());
            restaurantInfo.setContactPersonEmail(model.getContactPersonEmail());
            restaurantInfo.setContactPersonPhone(model.getContactPersonPhone());

            restaurantInfoRepository.saveAndFlush(restaurantInfo);

            return true;
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return  false;
        }
    }

    public RestaurantModel getRestaurantModel()
    {
        return RestaurantModel.from(getRestaurantInfo());
    }

    public WebsiteModel getWebsiteModel()
    {
        return WebsiteModel.from(getRestaurantInfo());
    }

    private RestaurantInfo getRestaurantInfo() {
        try {
            var restaurantInfo = restaurantInfoRepository.getInfo();

            if(restaurantInfo.isEmpty())
            {
                // Create
                var model = new RestaurantInfo();
                model.setName(" ");

                restaurantInfoRepository.saveAndFlush(model);
                return getRestaurantInfo();
            }

            return restaurantInfo.get();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            return new RestaurantInfo();
        }
    }
}
