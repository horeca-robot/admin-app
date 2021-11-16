package edu.fontys.horecarobot.adminappbackend.dtos.request;

import lombok.Data;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.UUID;

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
    private List<UUID> categories;
    private List<UUID> tags;
}
