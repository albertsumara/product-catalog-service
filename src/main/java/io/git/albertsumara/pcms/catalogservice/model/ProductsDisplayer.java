package io.git.albertsumara.pcms.catalogservice.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProductsDisplayer {

    private List<Product> products;

    public ProductsDisplayer(List<Product> products) {
        this.products = products;
    }


    public List<Map<String, Object>> toList() {
        List<Map<String, Object>> productList = new ArrayList<>();
        for (Product p : this.products) {
            productList.add(p.toMap());
        }
        return productList;
    }

    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(toList());
    }

}
