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

    public void validateMandatoryKey(Map<String, String> attributes, String key, boolean parseDouble) {

        String value = attributes.get(key);

        if (value == null) {
            throw new IllegalArgumentException("Missing mandatory attribute: " + key);
        }

        if (parseDouble) {
            try {
                Double.parseDouble(value);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException(key + " must be a valid number: " + value);
            }
        }
    }

    public Product createProduct (Map<String, String> attributes) {

        if (attributes == null || attributes.isEmpty()){
            throw new IllegalArgumentException("Attributes set cannot be empty");
        }

        validateMandatoryKey(attributes, "name", false);
        String name = attributes.get("name");
        attributes.remove("name");

        validateMandatoryKey(attributes, "price", true);
        double price = Double.parseDouble(attributes.get("price"));
        attributes.remove("price");

        validateMandatoryKey(attributes, "producerId", true);
        long producerId = Long.parseLong(attributes.get("producerId"));
        attributes.remove("producerId");


        Product product = new Product(producerId, name, price);


        Long productId = productRepository.saveProduct(product);


        List<Attribute> attributesOther = new ArrayList<>();

        for (Map.Entry<String, String> kvp : attributes.entrySet()){
            Attribute attribute = new Attribute(kvp.getKey(), kvp.getValue());
            attributesOther.add(attribute);
            attributeRepository.saveAttribute(attribute, productId);
        }
        return new Product(productId, producerId, name, price, attributesOther);
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


