package io.git.albertsumara.pcms.catalogservice.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ProductDisplayer {

    private List<Product> products;
    private ProductFilter filter;

    public ProductDisplayer(List<Product> products) {
        this.products = products;
        this.filter = null;
    }

    public void setFilter(ProductFilter filter) {
        this.filter = filter;
    }

    public boolean productFilter (Product product) {

        if (this.filter == null) {
            return true;
        }
        if (this.filter.getName() != null && !this.filter.getName().toLowerCase().equalsIgnoreCase(product.getName())){
            return false;
        }
        if (this.filter.getMinPrice() != null && this.filter.getMinPrice() > product.getPrice()) {
            return false;
        }
        if (this.filter.getMaxPrice() != null && this.filter.getMaxPrice() < product.getPrice()) {
            return false;
        }
        if (this.filter.getAttributes() != null && !this.filter.getAttributes().isEmpty()) {

            Map<String, String> productAttributesMap = product.getAttributes().stream()
                    .collect(Collectors.toMap(
                            Attribute::getName,
                            Attribute::getValue
                    ));

            for (Map.Entry<String, String> kvp : this.filter.getAttributes().entrySet()) {

                if (!productAttributesMap.containsKey(kvp.getKey())) {
                    return false;
                }

                else if(!productAttributesMap.get(kvp.getKey()).equals(kvp.getValue())){
                    return false;
                }

            }
        }
        return true;
    }

    public List<Map<String, Object>> toList() {
        List<Map<String, Object>> productList = new ArrayList<>();
        for (Product p : this.products) {
            if(productFilter(p))
                productList.add(p.toMap());
        }
        return productList;
    }

    public String toJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(toList());
    }

}
