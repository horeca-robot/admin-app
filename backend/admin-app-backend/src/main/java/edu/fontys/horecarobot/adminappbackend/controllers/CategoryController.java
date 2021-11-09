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
@RequestMapping(path = "api/Category")
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
            return new ResponseEntity<>(ApiResponse.ok().addData("categories", categories), HttpStatus.OK);
        }
        catch (Exception e)
        {
            return ResponseEntity.internalServerError().body(ApiResponse.DATABASE_CONNECTION_ERROR);
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
            return ResponseEntity.internalServerError().body(ApiResponse.DATABASE_CONNECTION_ERROR);
        }

        if (category.isPresent()) {
            return new ResponseEntity<>(ApiResponse.ok().addData("category", category.get()), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ApiResponse.error("Category not found"), HttpStatus.NOT_FOUND);
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
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            return ResponseEntity.internalServerError().body(ApiResponse.DATABASE_CONNECTION_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> putCategory(@PathVariable UUID id, @RequestBody CategoryModel categoryModel){
        if(!categoryService.doesCategoryExist(id))
        {
            return new ResponseEntity<>(ApiResponse.error("Can't locate category in database."), HttpStatus.NOT_FOUND);
        }

        if(categoryModel.getName().isBlank())
        {
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);
        }
        try
        {
            categoryService.updateCategory(categoryModel, id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e)
        {
            return ResponseEntity.internalServerError().body(ApiResponse.DATABASE_CONNECTION_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable UUID id){
        try
        {
            categoryService.deleteCategory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e)
        {
            return ResponseEntity.internalServerError().body(ApiResponse.DATABASE_CONNECTION_ERROR);
        }
    }
}
