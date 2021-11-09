package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.response.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.request.ProductRequestModel;
import edu.fontys.horecarobot.adminappbackend.dtos.response.ProductResponseModel;
import edu.fontys.horecarobot.adminappbackend.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/product")
@CrossOrigin(origins = "http://localhost:4000")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public ResponseEntity<?> getProducts() {
        List<ProductResponseModel> products;

        try {
            products = productService.getAllProducts();
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        return ResponseEntity.ok(ApiResponse.ok().addData("products", products));
    }

    @PostMapping
    public ResponseEntity<?> postProduct(@RequestBody ProductRequestModel product) {
        if(product.getName().isBlank() || product.getPrice() <= 0)
            return ResponseEntity.badRequest().body(ApiResponse.REQUIRED_FIELDS_ERROR);

        ProductResponseModel productResponseModel;
        try {
            productResponseModel = productService.addProduct(product);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        var uri = URI.create("api/product/" + productResponseModel.getId());
        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable UUID id) {
        try {
            productService.deleteProduct(id);
        }
        catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(ApiResponse.GENERAL_EXCEPTION_ERROR);
        }

        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

}
