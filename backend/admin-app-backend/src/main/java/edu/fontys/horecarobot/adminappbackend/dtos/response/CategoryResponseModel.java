package edu.fontys.horecarobot.adminappbackend.dtos.response;

import edu.fontys.horecarobot.databaselibrary.models.Category;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class CategoryResponseModel {

    public CategoryResponseModel(Category category) {
        id = category.getId();
        name = category.getName();
        image = category.getImage();
        parentCategories = category.getParentCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toList());
        childCategories = category.getChildCategories()
                .stream()
                .map(CategoryResponseModel::new)
                .collect(Collectors.toList());
    }

    private final UUID id;
    private final String name;
    private final String image;
    private final List<UUID> parentCategories;
    private final List<CategoryResponseModel> childCategories;

}
