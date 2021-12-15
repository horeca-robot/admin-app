package edu.fontys.horecarobot.adminappbackend.dtos.response;

import edu.fontys.horecarobot.databaselibrary.models.IngredientProduct;
import lombok.Data;

import java.util.UUID;

@Data
public class IngredientProductResponseModel {

    private final UUID id;
    private final String name;
    private final boolean required;

    public IngredientProductResponseModel(IngredientProduct ingredientProduct) {
        id = ingredientProduct.getIngredient().getId();
        name = ingredientProduct.getIngredient().getName();
        required = ingredientProduct.isRequired();
    }

}
