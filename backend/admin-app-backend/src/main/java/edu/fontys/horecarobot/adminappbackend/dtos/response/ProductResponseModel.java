package edu.fontys.horecarobot.adminappbackend.dtos.response;

import edu.fontys.horecarobot.databaselibrary.models.Category;
import edu.fontys.horecarobot.databaselibrary.models.Product;
import lombok.Data;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
public class ProductResponseModel {

    public ProductResponseModel(Product product) {
        id = product.getId();
        name = product.getName();
        image = product.getImage();
        price = product.getPrice();
        discountPrice = product.getDiscountPrice();
        description = product.getDescription();
        containsAlcohol = product.isContainsAlcohol();
        categories = product.getCategories()
                .stream()
                .map(Category::getId)
                .collect(Collectors.toList());
        tags = product.getTags()
                .stream()
                .map(TagResponseModel::new)
                .collect(Collectors.toList());
        ingredients = product.getIngredients()
                .stream()
                .map(IngredientProductResponseModel::new)
                .collect(Collectors.toList());
    }

    private final UUID id;
    private final String name;
    private final String image;
    private final double price;
    private final double discountPrice;
    private final String description;
    private final boolean containsAlcohol;
    private final List<UUID> categories;
    private final List<TagResponseModel> tags;
    private final List<IngredientProductResponseModel> ingredients;

}
