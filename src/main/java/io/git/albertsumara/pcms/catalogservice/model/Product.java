package io.git.albertsumara.pcms.catalogservice.model;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.*;

public class Product {

    private Long id;

    private Long producerId;

    private String name;

    private double price;

    private List<Attribute> attributes = new ArrayList<>();

    public Product(Long id, Long producerId, String name, double price, List<Attribute> atttributes) {

        this.id = id;
        this.producerId = producerId;
        this.name = name;
        this.price = price;
        this.attributes = atttributes;

    }

    public Product(Long producerId, String name, double price) {

        this.producerId = producerId;
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;

    }

    public Long getProducerId() {
        return producerId;
    }

    public void setProducerId(Long producerId) {
        this.producerId = producerId;
    }

    public List<Attribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<Attribute> _attributes) {
        this.attributes = _attributes;
    }

    public Map<String, Object> toMap(){

        Map<String, Object> product = new LinkedHashMap<>();
        product.put("id", this.id);
        product.put("name", this.name);
        product.put("price", this.price);

        for (Attribute a : this.attributes) {
            product.put(a.getName(), a.getValue());
        }
        return product;
    }

    public String toJson() {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(toMap());

    }

}
