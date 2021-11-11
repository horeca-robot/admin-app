package edu.fontys.horecarobot.adminappbackend.dtos.request;

import lombok.Data;
import lombok.NonNull;

import java.util.List;
import java.util.UUID;

@Data
public class CategoryRequestModel {
    private UUID id;
    @NonNull
    private String name;
    private String image;
    private List<UUID> parentCategories;
}
