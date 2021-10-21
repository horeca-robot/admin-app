package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.databaselibrary.models.AdminUser;
import edu.fontys.horecarobot.databaselibrary.models.Category;
import edu.fontys.horecarobot.databaselibrary.repositories.CategoryRepository;
import edu.fontys.horecarobot.adminappbackend.dtos.CategoryModel;
import lombok.RequiredArgsConstructor;
import org.h2.expression.ConcatenationOperation;
import org.springframework.data.domain.Example;
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
        return c;
    }

    private Category updateCategory(CategoryModel categoryModel) {
        CategoryModel c = new CategoryModel(categoryModel.getName(), categoryModel.getImage(), categoryModel.getParentCategory());
        Example<CategoryModel> example = Example.of(c);

        Optional<CategoryModel> optionalCategory = categoryRepository.findOne(example);
    }
}
