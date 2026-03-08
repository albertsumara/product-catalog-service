package io.git.albertsumara.pcms.catalogservice.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProducersDisplayer {

    private List<Producer> producers;

    public ProducersDisplayer(List<Producer> producers) {
        this.producers = producers;
    }

    public String toJson() {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Map<String, List<Map<String, Object>>> producersMap = new LinkedHashMap<>();

        for (Producer pr : this.producers) {
            producersMap.put(pr.getName(), new ProductsDisplayer(pr.getProductList()).toList());
        }

        return gson.toJson(producersMap);

    }

}
