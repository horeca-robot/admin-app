package edu.fontys.horecarobot.adminappbackend.services;

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

    public boolean doesCategoryExist(UUID id)
    {
        return categoryRepository.findById(id).isPresent();
    }

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

    public void addCategory(CategoryModel categoryModel) {
        categoryRepository.saveAndFlush(convertFromCategoryModel(categoryModel));
    }

    public void updateCategory(CategoryModel categoryModel, UUID id) {
        Category c = convertFromCategoryModel(categoryModel);
        c.setId(id);
        categoryRepository.saveAndFlush(c);
    }

    public void deleteCategory(UUID id)
    {
        categoryRepository.deleteById(id);
    }

    public CategoryModel convertToCategoryModel(Category category){
        CategoryModel c = new CategoryModel();
        c.setId(category.getId());
        c.setName(category.getName());
        c.setImage(category.getImage());
        c.setParentCategories(category.getParentCategories().stream().map(Category::getId).collect(Collectors.toList()));
        var list = new ArrayList<CategoryModel>();
        for(var child:category.getChildCategories()){
            list.add(convertToCategoryModel(child));
        }
        c.setChildCategories(list);
        return c;
    }

    private Category convertFromCategoryModel(CategoryModel categoryModel){
        Category c = new Category();
        c.setName(categoryModel.getName());
        c.setImage(categoryModel.getImage());
        c.setParentCategories(getCategories(categoryModel.getParentCategories()));
        return c;
    }

    private List<Category> getCategories(List<UUID> categories){
        var list = new ArrayList<Category>();

        for (var id:categories) {
            var category = getById(id);
            category.ifPresent(list::add);
        }

        return list;
    }
}
