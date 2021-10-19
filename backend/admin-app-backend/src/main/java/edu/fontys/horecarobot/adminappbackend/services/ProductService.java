package edu.fontys.horecarobot.adminappbackend.services;

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


    public boolean PostProduct( Product product)
    {
        var result = productRepository.save((product));
        if(result != null)
            return true;
        else
            return false;
    }
}
