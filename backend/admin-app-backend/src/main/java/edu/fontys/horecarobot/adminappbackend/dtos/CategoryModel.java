package edu.fontys.horecarobot.adminappbackend.dtos;

import edu.fontys.horecarobot.databaselibrary.models.Category;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryModel
{
    @NonNull
    private String name;
    private String image;
    private List<Category> parentCategory = new ArrayList<>();
}
