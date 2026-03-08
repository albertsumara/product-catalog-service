package io.git.albertsumara.pcms.catalogservice.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Producer {

    private long id;
    private String name;
    private List<Product> productList = new ArrayList<>();

    public Producer(List<Product> productList, String name) {
        this.productList = productList;
        this.name = name;
    }

    public Producer(String name) {
        this.name = name;
    }

    public Producer(Long id, String name) {
        this.name = name;
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String toJson(){

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Map<String, Object> producerMap = new LinkedHashMap<>();

        producerMap.put("id", this.id);
        producerMap.put("name", this.name);

        return gson.toJson(producerMap);

    }
}
