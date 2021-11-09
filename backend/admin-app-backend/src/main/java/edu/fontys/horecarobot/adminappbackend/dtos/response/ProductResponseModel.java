package edu.fontys.horecarobot.adminappbackend.dtos.response;

import edu.fontys.horecarobot.databaselibrary.models.Product;
import lombok.Data;

import java.util.UUID;

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
    }

    private UUID id;
    private String name;
    private String image;
    private double price;
    private double discountPrice;
    private String description;
    private boolean containsAlcohol;

}
