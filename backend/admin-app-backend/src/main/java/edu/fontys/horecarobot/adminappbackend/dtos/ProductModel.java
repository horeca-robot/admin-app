package edu.fontys.horecarobot.adminappbackend.dtos;

import lombok.Data;

@Data
public class ProductModel {
    private String name;
    private String image;
    private double price;
    private double discountPrice;
    private String description;
    private boolean containsAlcohol;
}