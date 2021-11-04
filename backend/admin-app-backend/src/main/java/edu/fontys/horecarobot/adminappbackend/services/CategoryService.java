package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.CategoryModel;
import edu.fontys.horecarobot.databaselibrary.models.Category;
import edu.fontys.horecarobot.databaselibrary.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public Category createCategory(CategoryModel categoryModel) {
        Category c = new Category();
        c.setParentCategory(categoryModel.getParentCategory());
        c.setName(categoryModel.getName());
        c.setImage(categoryModel.getImage());

        categoryRepository.save(c);
        return c;
    }

    public Category updateCategory(CategoryModel categoryModel, UUID id) {
        Category c = new Category();
        c.setId(id);
        c.setName(categoryModel.getName());
        c.setImage(categoryModel.getImage());
        c.setParentCategory(categoryModel.getParentCategory());
        categoryRepository.save(c);

        return c;
    }

    public void deleteCategory(UUID id)
    {
        categoryRepository.deleteById(id);
    }
}
