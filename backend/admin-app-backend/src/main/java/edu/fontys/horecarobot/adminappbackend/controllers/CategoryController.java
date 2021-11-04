package edu.fontys.horecarobot.adminappbackend.controllers;


import edu.fontys.horecarobot.adminappbackend.dtos.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.CategoryModel;
import edu.fontys.horecarobot.adminappbackend.services.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/Category")
@RequiredArgsConstructor
/* @CrossOrigin(origins = "http://localhost:4000") */

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
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.DATABASE_CONNECTION_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createCategory(@RequestBody CategoryModel categoryModel)
    {
        if(categoryModel.getParentCategory().isEmpty() || categoryModel.getName().isBlank() || categoryModel.getImage().isBlank())
        {
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.REQUIRED_FIELDS_ERROR), HttpStatus.BAD_REQUEST);
        }
        try
        {
            categoryService.createCategory(categoryModel);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.DATABASE_CONNECTION_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCategory(@PathVariable UUID id, @RequestBody CategoryModel categoryModel){
        if(!categoryService.getAllCategories().isEmpty())
        {
            return new ResponseEntity<>(ApiResponse.error("Can't locate category in database."), HttpStatus.NOT_FOUND);
        }

        if(categoryModel.getParentCategory().isEmpty() || categoryModel.getName().isBlank() || categoryModel.getImage().isBlank())
        {
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.REQUIRED_FIELDS_ERROR), HttpStatus.BAD_REQUEST);
        }
        try
        {
            categoryService.updateCategory(categoryModel, id);
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.DATABASE_CONNECTION_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteCategory(@PathVariable UUID id){

        if(categoryService.getAllCategories().isEmpty())
        {
            return new ResponseEntity<>(ApiResponse.error("Can't locate categories in database."), HttpStatus.NOT_FOUND);
        }

        try
        {
            categoryService.deleteCategory(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        catch(Exception e)
        {
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.DATABASE_CONNECTION_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
