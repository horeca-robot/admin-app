package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.services.ProductService;
import edu.fontys.horecarobot.databaselibrary.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "api/Product")
@CrossOrigin(origins = "http://localhost:4000")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;


    @PostMapping
    public ResponseEntity<ApiResponse> CreateProduct(@RequestBody Product product)
    {
        if(product.getName().isBlank() || product.getPrice() <= 0 || product.getDiscountPrice() <= 0)
            return new ResponseEntity<>(ApiResponse.error("Not all fields are filled in"), HttpStatus.BAD_REQUEST);

        if(productService.PostProduct(product))
            return new ResponseEntity<>(ApiResponse.ok("Product has been posted to the database"), HttpStatus.OK);
        else
            return new ResponseEntity<>(ApiResponse.error(), HttpStatus.BAD_REQUEST);
    }


}
