package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.RestaurantModel;
import edu.fontys.horecarobot.databaselibrary.models.RestaurantInfo;
import edu.fontys.horecarobot.databaselibrary.repositories.RestaurantInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantInfoRepository restaurantInfoRepository;

    public RestaurantModel getRestaurantModel()
    {
        var restaurantInfoList = restaurantInfoRepository.findAll();

        if(restaurantInfoList.isEmpty())
            return null;

        return createRestaurantModel(restaurantInfoList.get(0));
    }

    public boolean AddRestaurantInfo(RestaurantModel model)
    {
        if(model == null)
            return  false;

        var restaurantInfoList = restaurantInfoRepository.findAll();

        if(restaurantInfoList.isEmpty())
        {
            // Create new one
            RestaurantInfo restaurantInfo = createRestaurantInfo(model);

            restaurantInfoRepository.saveAndFlush(restaurantInfo);
        }
        else
        {
            // Update current
            var restaurantInfo = restaurantInfoList.get(0);

            restaurantInfo.setName(model.getName());
            restaurantInfo.setPrimaryColor(model.getPrimaryColor());
            restaurantInfo.setSecondaryColor(model.getSecondaryColor());
            restaurantInfo.setRestaurantLogo(model.getRestaurantLogo());
            restaurantInfo.setBackgroundImage(model.getBackgroundImage());
            restaurantInfo.setOpeningTime(model.getOpeningTime());
            restaurantInfo.setClosingTime(model.getClosingTime());
            restaurantInfo.setContactPersonName(model.getContactPersonName());
            restaurantInfo.setContactPersonEmail(model.getContactPersonEmail());
            restaurantInfo.setContactPersonPhone(model.getContactPersonPhone());
        }

        return  true;
    }

    private RestaurantInfo createRestaurantInfo(RestaurantModel model)
    {
        var restaurantInfo = new RestaurantInfo();

        restaurantInfo.setName(model.getName());
        restaurantInfo.setPrimaryColor(model.getPrimaryColor());
        restaurantInfo.setSecondaryColor(model.getSecondaryColor());
        restaurantInfo.setRestaurantLogo(model.getRestaurantLogo());
        restaurantInfo.setBackgroundImage(model.getBackgroundImage());
        restaurantInfo.setOpeningTime(model.getOpeningTime());
        restaurantInfo.setClosingTime(model.getClosingTime());
        restaurantInfo.setContactPersonName(model.getContactPersonName());
        restaurantInfo.setContactPersonEmail(model.getContactPersonEmail());
        restaurantInfo.setContactPersonPhone(model.getContactPersonPhone());

        return  restaurantInfo;
    }

    private RestaurantModel createRestaurantModel(RestaurantInfo model)
    {
        var restaurantModel = new RestaurantModel();

        restaurantModel.setName(model.getName());
        restaurantModel.setPrimaryColor(model.getPrimaryColor());
        restaurantModel.setSecondaryColor(model.getSecondaryColor());
        restaurantModel.setRestaurantLogo(model.getRestaurantLogo());
        restaurantModel.setBackgroundImage(model.getBackgroundImage());
        restaurantModel.setOpeningTime(model.getOpeningTime());
        restaurantModel.setClosingTime(model.getClosingTime());
        restaurantModel.setContactPersonName(model.getContactPersonName());
        restaurantModel.setContactPersonEmail(model.getContactPersonEmail());
        restaurantModel.setContactPersonPhone(model.getContactPersonPhone());

        return  restaurantModel;
    }
}
