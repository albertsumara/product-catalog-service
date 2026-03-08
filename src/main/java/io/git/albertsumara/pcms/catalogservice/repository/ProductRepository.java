package io.git.albertsumara.pcms.catalogservice.repository;
import io.git.albertsumara.pcms.catalogservice.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.dao.EmptyResultDataAccessException;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import java.util.*;

@Repository
public class ProductRepository {

    private final AttributeRepository attributeRepository;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert productInsert;

    @Autowired
    public ProductRepository(JdbcTemplate jdbcTemplate, AttributeRepository attributeRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.attributeRepository = attributeRepository;
        this.productInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("product")
                .usingGeneratedKeyColumns("id");

    }

    public long saveProduct(Product product) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", product.getName());
        parameters.put("price", product.getPrice());
        parameters.put("producer_id", product.getProducerId());

        Number generatedId = productInsert.executeAndReturnKey(parameters);
        return generatedId.longValue();
    }

    public boolean isProductExists(long product_id) {
        String sql = "SELECT EXISTS(SELECT 1 FROM product WHERE id = ?)";
        Boolean exists = jdbcTemplate.queryForObject(sql, new Object[]{product_id}, Boolean.class);
        return exists != null && exists;
    }

    public Optional<Product> findById(long productId) {
        String sql = "SELECT id, producerId, name, price FROM product WHERE id = ?";
        try {
            Product p = jdbcTemplate.queryForObject(sql, new Object[]{productId}, (rs, rowNum) -> {
                Product product = new Product(rs.getLong("producerId"), rs.getString("name"),
                        rs.getDouble("price"));
                product.setId(rs.getLong("id"));
                product.setAttributes(attributeRepository.findByProductId(productId));
                return product;
            });
            return Optional.of(p);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<Product> findByIdAndDelete(long productId) {
        String selectSql = "SELECT id, producer_id, name, price FROM product WHERE id = ?";
        String deleteSql = "DELETE FROM product WHERE id = ?";

        try {
            Product product = jdbcTemplate.queryForObject(selectSql, new Object[]{productId}, (rs, rowNum) -> {
                Product p = new Product(rs.getLong("producer_id"), rs.getString("name"),
                        rs.getDouble("price"));
                p.setId(rs.getLong("id"));
                p.setAttributes(attributeRepository.findByProductId(productId));
                return p;
            });
            jdbcTemplate.update(deleteSql, productId);
            return Optional.of(product);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Product> getAllProducts() {
        String sql = "SELECT id, producer_id, name, price FROM product";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Product p = new Product(rs.getLong("producer_id"), rs.getString("name"),
                            rs.getDouble("price"));
                    p.setId(rs.getLong("id"));
                    p.setAttributes(attributeRepository.findByProductId(rs.getLong("id")));
                    return p;
                });
    }

    public List<Product> getAllProductsByProducerId(long producerId) {
        String sql = "SELECT id, producer_id, name, price FROM product WHERE producer_id = ?";

        return jdbcTemplate.query(
                sql, new Object[]{producerId},
                (rs, rowNum) -> {
                    Product p = new Product(
                            rs.getLong("producer_id"),
                            rs.getString("name"),
                            rs.getDouble("price")
                    );
                    p.setId(rs.getLong("id"));
                    p.setAttributes(attributeRepository.findByProductId(rs.getLong("id")));
                    return p;
                });
    }

    private double parsePrice(String price){
            try {
                return Double.parseDouble(price);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid price: " + price);
            }
        }

    public Product modifyProduct(long productId, Map<String, String> changes) {
        if (!isProductExists(productId)) {
            throw new IllegalArgumentException("Product ID does not exist in DB");
        }

        Set<String> baseOptions = new HashSet<>(Arrays.asList("name", "price"));

        for (Map.Entry<String, String> change : changes.entrySet()) {
            String key = change.getKey();
            String value = change.getValue();

            if (baseOptions.contains(key)) {
                String sql = "UPDATE product SET " + key + " = ? WHERE id = ?";
                if (key.equals("price")) {
                    double priceValue = parsePrice(value);
                    jdbcTemplate.update(sql, priceValue, productId);
                } else {
                    jdbcTemplate.update(sql, value, productId);
                }
            } else {
                attributeRepository.upsert(productId, key, value);
            }
        }
        return findById(productId).get();
    }


}
