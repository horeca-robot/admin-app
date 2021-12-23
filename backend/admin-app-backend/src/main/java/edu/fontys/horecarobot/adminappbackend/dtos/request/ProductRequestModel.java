package edu.fontys.horecarobot.adminappbackend.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.*;

@Data
@NoArgsConstructor
public class ProductRequestModel {
    @NonNull
    private String name;
    private String image;
    @NonNull
    private double price;
    private double discountPrice;
    private String description;
    private boolean containsAlcohol;
    private boolean canBeServedAsByProduct;
    private List<UUID> categories = new ArrayList<>();
    private List<UUID> byProducts = new ArrayList<>();
    private List<UUID> tags = new ArrayList<>();
    private Map<UUID, Boolean> ingredients = new HashMap<>();
}
