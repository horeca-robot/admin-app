package edu.fontys.horecarobot.adminappbackend.dtos.response;

import edu.fontys.horecarobot.databaselibrary.models.Category;
import edu.fontys.horecarobot.databaselibrary.models.Product;
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
        visible = category.isVisible();
        parentCategories = category.getParentCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toList());
        childCategories = category.getChildCategories()
                .stream()
                .map(CategoryResponseModel::new)
                .collect(Collectors.toList());
        products = category.getProducts()
                .stream()
                .map(Product::getId)
                .collect(Collectors.toList());
    }

    private final UUID id;
    private final String name;
    private final String image;
    private final boolean visible;
    private final List<UUID> parentCategories;
    private final List<CategoryResponseModel> childCategories;
    private final List<UUID> products;

}
