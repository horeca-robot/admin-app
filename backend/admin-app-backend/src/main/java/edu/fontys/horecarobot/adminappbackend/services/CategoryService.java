package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.request.CategoryRequestModel;
import edu.fontys.horecarobot.adminappbackend.dtos.response.CategoryResponseModel;
import edu.fontys.horecarobot.databaselibrary.models.Category;
import edu.fontys.horecarobot.databaselibrary.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public boolean doesCategoryExist(UUID id) {
        return categoryRepository.findById(id).isPresent();
    }

    public List<CategoryResponseModel> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponseModel::new)
                .collect(Collectors.toList());
    }

    public Optional<CategoryResponseModel> getById(UUID id) {
            return categoryRepository.findById(id).map(CategoryResponseModel::new);
    }

    public CategoryResponseModel addCategory(CategoryRequestModel categoryRequestModel) {
        var c = categoryRepository.saveAndFlush(convertFromCategoryModel(categoryRequestModel));
        return new CategoryResponseModel(c);
    }

    public void updateCategory(CategoryRequestModel categoryRequestModel, UUID id) {
        Category c = convertFromCategoryModel(categoryRequestModel, id);
        c.setId(id);
        categoryRepository.saveAndFlush(c);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }

    private Category convertFromCategoryModel(CategoryRequestModel categoryModel) {
        return convertFromCategoryModel(categoryModel, new Category());
    }

    private Category convertFromCategoryModel(CategoryRequestModel categoryModel, UUID id) {
        Category c = categoryRepository.getById(id);
        return convertFromCategoryModel(categoryModel, c);
    }

    private Category convertFromCategoryModel(CategoryRequestModel categoryModel, Category c){
        c.setVisible(categoryModel.isVisible());
        c.setName(categoryModel.getName());
        c.setImage(categoryModel.getImage());
        c.setParentCategories(getCategoriesByIds(categoryModel.getParentCategories()));
        return c;
    }

    private List<Category> getCategoriesByIds(List<UUID> categories) {
        return categories.stream().map(categoryRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
