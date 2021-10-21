package edu.fontys.horecarobot.adminappbackend.dtos;

public class Category {
    private String name;
    private String image;
    private String headCategory;

    public Category() {

    }

    public Category(String name, String image, String headCategory) {
        this.name = name;
        this.image = image;
        this.headCategory = headCategory;
    }

    public String getName() {
        return name;
    }
    public String getImage() {
        return image;
    }
    public String getHeadCategory() {
        return headCategory;
    }
}
