package io.git.albertsumara.pcms.catalogservice.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProductsDisplayer {

    private List<Product> products;

    public ProductsDisplayer(List<Product> products) {
        this.products = products;
    }

    public String toJson() {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        long counter = 1;

        Map<Long, Map<String, Object>> products = new LinkedHashMap<>();

        for (Product p : this.products) {
            products.put(counter, p.toMap());
            counter++;
        }

        return gson.toJson(products);

    }

}
