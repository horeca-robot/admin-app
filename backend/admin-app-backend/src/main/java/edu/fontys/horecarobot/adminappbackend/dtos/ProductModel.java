package edu.fontys.horecarobot.adminappbackend.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class ProductModel {
    private UUID id;
    @NonNull
    private String name;
    private String image;
    @NonNull
    private double price;
    private double discountPrice;
    private String description;
    private boolean containsAlcohol;
    private List<UUID> categories = new ArrayList<>();
}