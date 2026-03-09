package io.git.albertsumara.pcms.catalogservice.controller;
import io.git.albertsumara.pcms.catalogservice.model.*;
import io.git.albertsumara.pcms.catalogservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("api/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping()
    public ResponseEntity<?> createProduct(@RequestBody Map<String, String> attributes){

        try {
            Product product = productService.createProduct(attributes);
            return ResponseEntity.ok(product.toJson());
        }catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping("/{productId}")
    public ResponseEntity<?> modifyProduct(@PathVariable("productId") long productId,
                                           @RequestBody Map<String, String> changes){
        try{
            Product product = productService.modifyProduct(productId, changes);
            return  ResponseEntity.ok(product.toJson());
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/filter")
    public ResponseEntity<?> filterProducts(@RequestBody Map<String, String> filters){

        ProductFilter filter = productService.productFilter(filters);
        ProductDisplayer productDisplayer = new ProductDisplayer(productService.getAllProducts());
        productDisplayer.setFilter(filter);
        return ResponseEntity.ok(productDisplayer.toJson());

    }

    @GetMapping("/{productId}")
    public ResponseEntity<?> getProduct(@PathVariable("productId") long productId){

        try{
            Product product = productService.getProduct(productId);
            return  ResponseEntity.ok(product.toJson());
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<?> getAllProducts() {
        return ResponseEntity.ok(new ProductDisplayer(productService.getAllProducts()).toJson());
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> deleteProduct(@PathVariable long productId){

        return ResponseEntity.ok("Product deleted: " + productService.deleteProduct(productId).toJson());

    }

}
