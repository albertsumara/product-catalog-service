package io.git.albertsumara.pcms.catalogservice.controller;
import io.git.albertsumara.pcms.catalogservice.model.Product;
import io.git.albertsumara.pcms.catalogservice.model.ProductsDisplayer;
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

    @PatchMapping("/{product_id}")
    public ResponseEntity<?> modifyProduct(@PathVariable("product_id") long productId,
                                           @RequestBody Map<String, String> changes){
        try{
            Product product = productService.modifyProduct(productId, changes);
            return  ResponseEntity.ok(product.toJson());
        }
        catch (IllegalArgumentException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<?> getProduct(@PathVariable("product_id") long productId){

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
        return ResponseEntity.ok(new ProductsDisplayer(productService.getAllProducts()).toJson());
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<?> deleteProduct(@PathVariable long product_id){

        return ResponseEntity.ok("Product deleted: " + productService.deleteProduct(product_id).toJson());

    }

}
