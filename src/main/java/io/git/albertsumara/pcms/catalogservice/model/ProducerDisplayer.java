package io.git.albertsumara.pcms.catalogservice.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ProducerDisplayer {

    private List<Producer> producers;
    private ProductFilter productFilter;

    public ProducerDisplayer(List<Producer> producers) {
        this.producers = producers;
        this.productFilter = null;
    }

    public void setProductFilter(ProductFilter productFilter) {
        this.productFilter = productFilter;
    }

    public String toJson() {

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        Map<String, List<Map<String, Object>>> producersMap = new LinkedHashMap<>();

        for (Producer pr : this.producers) {
            ProductDisplayer productDisplayer  = new ProductDisplayer(pr.getProductList());
            productDisplayer.setFilter(this.productFilter);
            producersMap.put(pr.getName(), productDisplayer.toList());
        }

        return gson.toJson(producersMap);

    }

}
