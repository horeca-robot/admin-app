package edu.fontys.horecarobot.adminappbackend.controllers;

import edu.fontys.horecarobot.adminappbackend.dtos.response.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.request.ProductRequestModel;
import edu.fontys.horecarobot.adminappbackend.dtos.response.ProductResponseModel;
import edu.fontys.horecarobot.adminappbackend.dtos.ApiResponse;
import edu.fontys.horecarobot.adminappbackend.dtos.CategoryModel;
import edu.fontys.horecarobot.adminappbackend.dtos.ProductModel;
import edu.fontys.horecarobot.adminappbackend.services.ProductService;
import edu.fontys.horecarobot.databaselibrary.models.Category;
import edu.fontys.horecarobot.databaselibrary.models.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<?> getProducts(){
        var products = productService.getAllProducts();
        return new ResponseEntity<>(ApiResponse.ok().addData("products", products), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getProductById(@PathVariable UUID id)
    {
        Optional<Product> product;
        try {
            product = productService.getById(id);
        } catch (Exception e) {
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.DATABASE_CONNECTION_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (product.isPresent()) {
            return new ResponseEntity<>(ApiResponse.ok().addData("product", productService.convertToProductModel(product.get())), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(ApiResponse.error("Product not found"), HttpStatus.NOT_FOUND);
        }
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

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> putCategory(@PathVariable UUID id, @RequestBody ProductModel productModel){
        if(!productService.doesProductExist(id))
            return new ResponseEntity<>(ApiResponse.error("Can't locate product in database."), HttpStatus.NOT_FOUND);

        if(productModel.getName().isBlank() || productModel.getPrice() <= 0)
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.REQUIRED_FIELDS_ERROR), HttpStatus.BAD_REQUEST);

        try {
            productService.updateProduct(productModel, id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch(Exception e) {
            return new ResponseEntity<>(ApiResponse.error(ApiResponse.DATABASE_CONNECTION_ERROR), HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
