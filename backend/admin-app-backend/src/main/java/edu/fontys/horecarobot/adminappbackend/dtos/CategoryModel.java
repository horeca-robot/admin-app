package edu.fontys.horecarobot.adminappbackend.dtos;

import edu.fontys.horecarobot.databaselibrary.models.Category;
import lombok.Data;

import java.util.List;

@Data
public class CategoryModel {
    private String name;
    private String image;
    private List<Category> parentCategory;

    public CategoryModel() {

    }

    public CategoryModel(String name, String image, List<Category> parentCategory) {
        this.name = name;
        this.image = image;
        this.parentCategory = parentCategory;
    }
}
