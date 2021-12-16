package edu.fontys.horecarobot.adminappbackend.dtos.response;

import edu.fontys.horecarobot.databaselibrary.models.Ingredient;
import lombok.Data;

import java.util.UUID;

@Data
public class IngredientResponseModel {

    public IngredientResponseModel(Ingredient ingredient) {
        id = ingredient.getId();
        name = ingredient.getName();
    }

    private final UUID id;
    private final String name;
}
