package edu.fontys.horecarobot.adminappbackend.dtos.request;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CategoryRequestModel {
    private UUID id;
    @NonNull
    private String name;
    private String image;
    private List<UUID> parentCategories = new ArrayList<>();
}
