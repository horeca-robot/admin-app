package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.request.IngredientRequestModel;
import edu.fontys.horecarobot.adminappbackend.dtos.response.IngredientResponseModel;
import edu.fontys.horecarobot.databaselibrary.models.Ingredient;
import edu.fontys.horecarobot.databaselibrary.repositories.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public List<IngredientResponseModel> getAllIngredients() {
        return ingredientRepository.findAll()
                .stream()
                .map(IngredientResponseModel::new)
                .collect(Collectors.toList());
    }

    public Optional<IngredientResponseModel> getIngredientById(UUID id) {
        return ingredientRepository.findById(id).map(IngredientResponseModel::new);
    }

    public Optional<IngredientResponseModel> getIngredientByName(String name) {
        Ingredient Ingredient = new Ingredient();
        Ingredient.setName(name);

        Example<Ingredient> example = Example.of(Ingredient);
        return ingredientRepository.findOne(example).map(IngredientResponseModel::new);
    }

    public IngredientResponseModel addIngredient(IngredientRequestModel ingredientRequestModel) {
        var Ingredient = new Ingredient(null, ingredientRequestModel.getName());
        Ingredient = ingredientRepository.save(Ingredient);
        return new IngredientResponseModel(Ingredient);
    }

    public void deleteIngredient(UUID id) {
        ingredientRepository.deleteById(id);
    }

}
