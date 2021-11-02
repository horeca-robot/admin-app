package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.CategoryModel;
import edu.fontys.horecarobot.databaselibrary.models.Category;
import edu.fontys.horecarobot.databaselibrary.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void saveCategory(CategoryModel categoryModel) {
        var category = createCategory(categoryModel);
        categoryRepository.save(category);
    }

    private Category createCategory(CategoryModel categoryModel) {
        Category c = new Category();
        c.setParentCategory(categoryModel.getParentCategory());
        c.setName(categoryModel.getName());
        c.setImage(categoryModel.getImage());

        categoryRepository.save(c);
    }

    private Category updateCategory(CategoryModel categoryModel) {
        Category c = new Category();
        c.setName(categoryModel.getName());
        c.setImage(categoryModel.getImage());
        c.setParentCategory(categoryModel.getParentCategory());
        categoryRepository.save(c);
    }

    private void deleteCategory(CategoryModel categoryModel)
    {
        Category c = new Category();
        categoryRepository.deleteById(c.getId());
    }
}
