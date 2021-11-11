package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.request.CategoryRequestModel;
import edu.fontys.horecarobot.adminappbackend.dtos.response.CategoryResponseModel;
import edu.fontys.horecarobot.adminappbackend.dtos.CategoryModel;
import edu.fontys.horecarobot.databaselibrary.models.Category;
import edu.fontys.horecarobot.databaselibrary.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponseModel> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(CategoryResponseModel::new)
                .collect(Collectors.toList());
    }

    public boolean doesCategoryExist(UUID id) {
    public boolean doesCategoryExist(UUID id)
    {
        return categoryRepository.findById(id).isPresent();
    }

    public Optional<CategoryResponseModel> getById(UUID id) {
        return categoryRepository.findById(id).map(CategoryResponseModel::new);
    public List<CategoryModel> getAllCategories() {
        var list = new ArrayList<CategoryModel>();
        var categories = categoryRepository.findAll();

        for(var category:categories){
            list.add(convertToCategoryModel(category));
        }

        return list;
    }

    public Optional<Category> getById(UUID id)
    {
        return categoryRepository.findById(id);
    }

    public CategoryResponseModel addCategory(CategoryRequestModel categoryRequestModel) {
        var c = categoryRepository.saveAndFlush(convertFromCategoryModel(categoryRequestModel));
        return new CategoryResponseModel(c);
    }

    public void updateCategory(CategoryRequestModel categoryRequestModel, UUID id) {
        Category c = convertFromCategoryModel(categoryRequestModel);
        c.setId(id);
        categoryRepository.saveAndFlush(c);
    }

    public void deleteCategory(UUID id) {
        categoryRepository.deleteById(id);
    }

    private Category convertFromCategoryModel(CategoryRequestModel categoryRequestModel){
        Category c = new Category();
        c.setParentCategories(categoryRequestModel.getParentCategory());
        c.setName(categoryRequestModel.getName());
        c.setImage(categoryRequestModel.getImage());
        c.setName(categoryModel.getName());
        c.setImage(categoryModel.getImage());
        c.setParentCategories(getCategories(categoryModel.getParentCategories()));
        return c;
    }

}
