package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.ProductModel;
import edu.fontys.horecarobot.adminappbackend.services.ProductService;
import edu.fontys.horecarobot.databaselibrary.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/Product")
@CrossOrigin(origins = "http://localhost:4000")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductModel product){
        if(product.getName().isBlank() || product.getPrice() <= 0)
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.REQUIRED_FIELDS_ERROR), HttpStatus.BAD_REQUEST);

        productService.saveProduct((product));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<?> getProducts(){
        var products = productService.getAllProducts();
        return new ResponseEntity<>(ApiResponse.ok().addData("list", products), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id){
        productService.removeProduct(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
