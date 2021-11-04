package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.databaselibrary.models.Category;
import edu.fontys.horecarobot.databaselibrary.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/Test")
@CrossOrigin(origins = "http://localhost:4000")
@RequiredArgsConstructor
public class TestController {

    private final CategoryRepository categoryRepository;

    @GetMapping
    public String getProducts(){
/*
        Category c1 = new Category();
        c1.setName("c1");
        Category c2 = new Category();
        c2.setName("c2");
        Category c3 = new Category();
        c3.setName("c3");
        Category c4 = new Category();
        c4.setName("c4");

        categoryRepository.saveAllAndFlush(Arrays.asList(c1, c2, c3, c4));

        List<Category> c3Parents = c3.getParentCategories();
        c3Parents.add(c1);
        c3Parents.add(c2);
        c3.setParentCategories(c3Parents);
        categoryRepository.saveAndFlush(c3);

        List<Category> c4Childs = c4.getChildCategories();
        c4Childs.add(c1);
        c4.setChildCategories(c4Childs);
        categoryRepository.saveAndFlush(c4);
*/
        List<Category> categories = categoryRepository.findAll();

        StringBuilder sb = new StringBuilder();
        for (Category c : categories) {
            sb.append("-----------------------------------------");
            sb.append('\n');

            sb.append("Id:");
            sb.append('\n');
            sb.append(c.getId());
            sb.append('\n');

            sb.append("Name:");
            sb.append('\n');
            sb.append(c.getName());

            sb.append('\n');
            sb.append("Parents:");
            sb.append('\n');
            sb.append(listToString(c.getParentCategories()));
            sb.append('\n');
            sb.append("Childs:");
            sb.append('\n');
            sb.append(listToString(c.getChildCategories()));
            sb.append('\n');

            sb.append("-----------------------------------------");
            sb.append('\n');
        }

        return sb.toString();
    }

    private String listToString(List<Category> categories) {
        StringBuilder sb = new StringBuilder();
        for (Category c : categories) {
            sb.append(c.getName())
                    .append(", ");
        }
        return sb.toString();
    }

}
