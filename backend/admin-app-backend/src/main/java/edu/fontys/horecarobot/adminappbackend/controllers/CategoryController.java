package edu.fontys.horecarobot.adminappbackend.controllers;


import edu.fontys.horecarobot.adminappbackend.services.CategoryService;
import edu.fontys.horecarobot.databaselibrary.models.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/Category")
@RequiredArgsConstructor
/* @CrossOrigin(origins = "http://localhost:4000") */

public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public List<Category> getCategories(){
        return categoryService.getAllCategories();
    }
}
