package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.response.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.request.CategoryRequestModel;
import edu.fontys.horecarobot.adminappbackend.dtos.response.CategoryResponseModel;
import edu.fontys.horecarobot.adminappbackend.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/category")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse> getCategories() {
        List<CategoryResponseModel> categories;
        try {
            categories = categoryService.getAllCategories();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        return ResponseEntity.ok(ApiResponse.ok().addData("categories", categories));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable UUID id) {
        Optional<CategoryResponseModel> category;
        try {
            category = categoryService.getById(id);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        if (category.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Category not found"));
        }

        return ResponseEntity.ok(ApiResponse.ok().addData("category", category.get()));
    }

    @PostMapping
    public ResponseEntity<ApiResponse> postCategory(@RequestBody CategoryRequestModel categoryRequestModel) {
        if(categoryRequestModel.getName().isBlank()) {
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);
        }

        CategoryResponseModel categoryResponseModel;
        try {
            categoryResponseModel = categoryService.addCategory(categoryRequestModel);
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        var uri = URI.create("api/category/" + categoryResponseModel.getId());
        return ResponseEntity.created(uri).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> putCategory(@PathVariable UUID id, @RequestBody CategoryRequestModel categoryRequestModel) {
        if(!categoryService.doesCategoryExist(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Can't locate category in database."));
        }

        if(categoryRequestModel.getName().isBlank()) {
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);
        }
        try {
            categoryService.updateCategory(categoryRequestModel, id);
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable UUID id) {
        try {
            categoryService.deleteCategory(id);
        }
        catch(Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        return ResponseEntity.noContent().build();
    }

}
