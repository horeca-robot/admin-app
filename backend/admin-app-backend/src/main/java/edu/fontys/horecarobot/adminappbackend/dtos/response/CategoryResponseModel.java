package edu.fontys.horecarobot.adminappbackend.dtos.response;

import edu.fontys.horecarobot.databaselibrary.models.Category;
import lombok.Data;

import java.util.UUID;

@Data
public class CategoryResponseModel {

    public CategoryResponseModel(Category category) {
        id = category.getId();
        name = category.getName();
        image = category.getImage();
    }

    private UUID id;
    private String name;
    private String image;

}
