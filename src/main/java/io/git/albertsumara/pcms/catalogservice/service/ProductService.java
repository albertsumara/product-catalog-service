package io.git.albertsumara.pcms.catalogservice.service;

import io.git.albertsumara.pcms.catalogservice.model.Product;
import io.git.albertsumara.pcms.catalogservice.model.Attribute;

import io.git.albertsumara.pcms.catalogservice.repository.AttributeRepository;
import io.git.albertsumara.pcms.catalogservice.repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {


    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private AttributeRepository attributeRepository;

    public Product createProduct (Map<String, String> attributes) {
        if (!attributes.containsKey("name")) {
            throw new IllegalArgumentException("the product's attribute set does not contain a mandatory key name.");
        }
        String name = attributes.get("name");
        attributes.remove("name");
        if (!attributes.containsKey("price")) {
            throw new IllegalArgumentException("the attribute set does not contain a mandatory key price.");
        }
        double priceTest;
        try {
            priceTest = Double.parseDouble(attributes.get("price"));
            attributes.remove("price");
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("price parsing error: " + e);
        }
        double price = priceTest;
        Product product = new Product(name, price);
        Long product_id = productRepository.saveProduct(product);

        List<Attribute> attributesOther = new ArrayList<>();

        for (Map.Entry<String, String> kvp : attributes.entrySet()){
            Attribute attribute = new Attribute(kvp.getKey(), kvp.getValue());
            attributesOther.add(attribute);
            attributeRepository.saveAttribute(attribute, product_id);
        }
        return new Product(product_id, name, price, attributesOther);
    }

    public Product modifyProduct(long productId, Map<String, String> changes) {

        if (productId < 1){
            throw new IllegalArgumentException("wrong id value");
        }

        if (changes.isEmpty()){
            throw new IllegalArgumentException("changes does not exists");
        }

        return productRepository.modifyProduct(productId, changes);
    }

    public List<Product> getAllProducts(){

        return productRepository.getAllProducts();

    }

    public Product getProduct(long productId){
        Optional<Product> result = productRepository.findById(productId);
        if (result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Product id: " + productId + " does not exist.");
        }
        return result.get();
    }


    public Product deleteProduct(long productId){
        Optional<Product> result = productRepository.findByIdAndDelete(productId);
        if (result.isEmpty()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Product id: " + productId + " does not exist.");
        }
        return result.get();
    }

}


