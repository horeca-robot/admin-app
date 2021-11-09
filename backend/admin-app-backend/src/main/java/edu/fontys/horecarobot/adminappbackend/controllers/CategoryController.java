package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.CategoryModel;
import edu.fontys.horecarobot.adminappbackend.services.CategoryService;
import edu.fontys.horecarobot.databaselibrary.models.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/category")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4000")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public ResponseEntity<ApiResponse> getCategories()
    {
        try
        {
            var categories = categoryService.getAllCategories();
            return ResponseEntity.ok(ApiResponse.ok().addData("categories", categories));
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCategoryById(@PathVariable UUID id)
    {
        Optional<Category> category;
        try
        {
            category = categoryService.getById(id);
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        if (category.isPresent()) {
            return ResponseEntity.ok(ApiResponse.ok().addData("category", category.get()));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Category not found"));
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> postCategory(@RequestBody CategoryModel categoryModel)
    {
        if(categoryModel.getName().isBlank())
        {
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);
        }
        try
        {
            categoryService.addCategory(categoryModel);
            // TODO: Change response entity to contain uri
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> putCategory(@PathVariable UUID id, @RequestBody CategoryModel categoryModel){
        if(!categoryService.doesCategoryExist(id))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ApiResponse.error("Can't locate category in database."));
        }

        if(categoryModel.getName().isBlank())
        {
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);
        }
        try
        {
            categoryService.updateCategory(categoryModel, id);
            return ResponseEntity.noContent().build();
        }
        catch(Exception e)
        {
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable UUID id){
        try
        {
            categoryService.deleteCategory(id);
            return ResponseEntity.noContent().build();
        }
        catch(Exception e)
        {
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }
    }
}
