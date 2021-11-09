package edu.fontys.horecarobot.adminappbackend.dtos.request;

import lombok.Data;
import org.springframework.lang.NonNull;

@Data
public class ProductRequestModel {
    @NonNull
    private String name;
    private String image;
    @NonNull
    private double price;
    private double discountPrice;
    private String description;
    private boolean containsAlcohol;
}