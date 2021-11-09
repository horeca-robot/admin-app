package edu.fontys.horecarobot.adminappbackend.services;

import edu.fontys.horecarobot.adminappbackend.dtos.request.ProductRequestModel;
import edu.fontys.horecarobot.adminappbackend.dtos.response.ProductResponseModel;
import edu.fontys.horecarobot.databaselibrary.models.Product;
import edu.fontys.horecarobot.databaselibrary.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

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

    public void deleteProduct(UUID id){
        productRepository.deleteById(id);
    }

    private Product convertToProduct(ProductRequestModel productRequestModel){
        Product p = new Product();
        p.setName(productRequestModel.getName());
        p.setDescription(productRequestModel.getDescription());
        p.setPrice(productRequestModel.getPrice());
        p.setContainsAlcohol(productRequestModel.isContainsAlcohol());
        p.setDiscountPrice(productRequestModel.getDiscountPrice());
        p.setImage(productRequestModel.getImage());

        return p;
    }

}
