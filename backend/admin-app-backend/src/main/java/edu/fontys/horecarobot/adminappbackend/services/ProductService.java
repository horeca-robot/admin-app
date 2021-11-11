package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.request.ProductRequestModel;
import edu.fontys.horecarobot.adminappbackend.dtos.response.ProductResponseModel;
import edu.fontys.horecarobot.adminappbackend.dtos.CategoryModel;
import edu.fontys.horecarobot.adminappbackend.dtos.ProductModel;
import edu.fontys.horecarobot.databaselibrary.models.Category;
import edu.fontys.horecarobot.databaselibrary.models.Product;
import edu.fontys.horecarobot.databaselibrary.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public boolean doesProductExist(UUID id) {
        return productRepository.findById(id).isPresent();
    }

    public List<ProductModel> getAllProducts(){
        var list = new ArrayList<ProductModel>();
        var products = productRepository.findAll();

        for(var product:products){
            list.add(convertToProductModel(product));
        }

    public List<ProductResponseModel> getAllProducts(){
        return productRepository.findAll()
                .stream()
                .map(ProductResponseModel::new)
                .collect(Collectors.toList());
    }

    public ProductResponseModel addProduct(ProductRequestModel productRequestModel){
        var product = convertToProduct(productRequestModel);
        product = productRepository.save(product);
        return new ProductResponseModel(product);
    }

    public void updateProduct(ProductModel productModel, UUID id) {
        var product = convertFromProductModel(productModel);
        product.setId(id);
        productRepository.saveAndFlush(product);
    }

    public void deleteProduct(UUID id){
        productRepository.deleteById(id);
    }

    private Product convertToProduct(ProductRequestModel productRequestModel){
    public ProductModel convertToProductModel(Product product){
        ProductModel p = new ProductModel();
        p.setId(product.getId());
        p.setName(product.getName());
        p.setDescription(product.getDescription());
        p.setPrice(product.getPrice());
        p.setContainsAlcohol(product.isContainsAlcohol());
        p.setDiscountPrice(product.getDiscountPrice());
        p.setImage(product.getImage());
        p.setCategories(product.getCategories().stream().map(Category::getId).collect(Collectors.toList()));
        return p;
    }

    private Product convertFromProductModel(ProductModel productModel){
        Product p = new Product();
        p.setName(productRequestModel.getName());
        p.setDescription(productRequestModel.getDescription());
        p.setPrice(productRequestModel.getPrice());
        p.setContainsAlcohol(productRequestModel.isContainsAlcohol());
        p.setDiscountPrice(productRequestModel.getDiscountPrice());
        p.setImage(productRequestModel.getImage());

        p.setName(productModel.getName());
        p.setDescription(productModel.getDescription());
        p.setPrice(productModel.getPrice());
        p.setContainsAlcohol(productModel.isContainsAlcohol());
        p.setDiscountPrice(productModel.getDiscountPrice());
        p.setImage(productModel.getImage());
        p.setCategories(getCategories(productModel.getCategories()));
        return p;
    }

    private List<Category> getCategories(List<UUID> categories){
        var list = new ArrayList<Category>();

        for (var id:categories) {
            var category = categoryService.getById(id);
            category.ifPresent(list::add);
        }

        return list;
    }
}
