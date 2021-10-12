package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.databaselibrary.models.Product;
import edu.fontys.horecarobot.databaselibrary.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
public class TestController {

    private final ProductRepository productRepository;

    @GetMapping("/test")
    List<Product> testMapping(@RequestParam(required = false) String name) {
        return productRepository.findAll();
    }

    @PostMapping("/test")
    String addCompany(@RequestBody Product product) {
        productRepository.save(product);
        return "Success!";
    }

}
