package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.request.IngredientRequestModel;
import edu.fontys.horecarobot.adminappbackend.dtos.response.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.response.IngredientResponseModel;
import edu.fontys.horecarobot.adminappbackend.services.IngredientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/ingredient")
@CrossOrigin(origins = "http://178.62.217.137:8081")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    public ResponseEntity<?> getIngredients() {
        List<IngredientResponseModel> ingredients;

        try {
            ingredients = ingredientService.getAllIngredients();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        return ResponseEntity.ok(ApiResponse.ok().addData("ingredients", ingredients));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getIngredientById(@PathVariable UUID id) {
        Optional<IngredientResponseModel> ingredient;
        try {
            ingredient = ingredientService.getIngredientById(id);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        if (ingredient.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Ingredient not found"));
        }

        return ResponseEntity.ok(ApiResponse.ok().addData("ingredient", ingredient.get()));
    }

    @PostMapping
    public ResponseEntity<?> postIngredient(@RequestBody IngredientRequestModel Ingredient) {
        if(Ingredient.getName().isBlank())
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);

        IngredientResponseModel IngredientResponseModel;
        try {
            IngredientResponseModel = ingredientService.addIngredient(Ingredient);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        var uri = URI.create("api/ingredient/" + IngredientResponseModel.getId());
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIngredient(@PathVariable UUID id) {
        try {
            ingredientService.deleteIngredient(id);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
