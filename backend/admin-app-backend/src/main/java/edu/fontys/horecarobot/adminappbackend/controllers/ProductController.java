package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.ProductModel;
import edu.fontys.horecarobot.adminappbackend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/product")
@CrossOrigin(origins = "http://localhost:4000")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> getProducts(){
        var products = productService.getAllProducts();
        return ResponseEntity.ok(ApiResponse.ok().addData("products", products));
    }

    @PostMapping
    public ResponseEntity<?> postProduct(@RequestBody ProductModel product){
        if(product.getName().isBlank() || product.getPrice() <= 0)
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);

        productService.addProduct(product);
        // TODO: Change response entity to contain uri
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id){
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
