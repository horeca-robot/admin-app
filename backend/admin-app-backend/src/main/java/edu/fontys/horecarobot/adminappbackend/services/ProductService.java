package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.request.ProductRequestModel;
import edu.fontys.horecarobot.adminappbackend.dtos.response.ProductResponseModel;
import edu.fontys.horecarobot.databaselibrary.models.Category;
import edu.fontys.horecarobot.databaselibrary.models.Product;
import edu.fontys.horecarobot.databaselibrary.repositories.CategoryRepository;
import edu.fontys.horecarobot.databaselibrary.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public boolean doesProductExist(UUID id) {
        return productRepository.findById(id).isPresent();
    }

    public List<ProductResponseModel> getAllProducts() {
        return productRepository.findAll()
                .stream()
                .map(ProductResponseModel::new)
                .collect(Collectors.toList());
    }

    public Optional<ProductResponseModel> getProductById(UUID id) {
        return productRepository.findById(id).map(ProductResponseModel::new);
    }

    public ProductResponseModel addProduct(ProductRequestModel productRequestModel) {
        var product = convertFromProductModel(productRequestModel);
        product = productRepository.save(product);
        return new ProductResponseModel(product);
    }

    public void updateProduct(ProductRequestModel productRequestModel, UUID id) {
        var product = convertFromProductModel(productRequestModel);
        product.setId(id);
        productRepository.saveAndFlush(product);
    }

    public void deleteProduct(UUID id) {
        productRepository.deleteById(id);
    }

    private Product convertFromProductModel(ProductRequestModel productModel) {
        Product p = new Product();
        p.setName(productModel.getName());
        p.setDescription(productModel.getDescription());
        p.setPrice(productModel.getPrice());
        p.setContainsAlcohol(productModel.isContainsAlcohol());
        p.setDiscountPrice(productModel.getDiscountPrice());
        p.setImage(productModel.getImage());
        p.setCategories(getProductCategories(productModel.getCategories()));
        return p;
    }

    private List<Category> getProductCategories(List<UUID> categories) {
        return categories.stream().map(categoryRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

}
