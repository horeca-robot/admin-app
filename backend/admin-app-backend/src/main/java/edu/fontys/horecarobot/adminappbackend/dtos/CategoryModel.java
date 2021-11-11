package edu.fontys.horecarobot.adminappbackend.dtos;

import edu.fontys.horecarobot.databaselibrary.models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel
{
    private UUID id;
    @NonNull
    private String name;
    private String image;
    private List<UUID> parentCategories = new ArrayList<>();
    private List<CategoryModel> childCategories = new ArrayList<>();
}
