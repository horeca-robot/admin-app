package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.ProductModel;
import edu.fontys.horecarobot.databaselibrary.models.Product;
import edu.fontys.horecarobot.databaselibrary.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getAllProducts(){
        return productRepository.findAll();
    }

    public void saveProduct(ProductModel productModel){
        var product = createProduct(productModel);
        productRepository.save(product);
    }

    private Product createProduct(ProductModel productModel){
        Product p = new Product();
        p.setName(productModel.getName());
        p.setDescription(productModel.getDescription());
        p.setPrice(productModel.getPrice());
        p.setContainsAlcohol(productModel.isContainsAlcohol());
        p.setDiscountPrice(productModel.getDiscountPrice());
        p.setImage(productModel.getImage());

        return p;
    }

}
