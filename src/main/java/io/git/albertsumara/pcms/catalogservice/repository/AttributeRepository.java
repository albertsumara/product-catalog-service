package io.git.albertsumara.pcms.catalogservice.repository;

import io.git.albertsumara.pcms.catalogservice.model.Attribute;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class AttributeRepository {

    private final JdbcTemplate jdbcTemplate;

    public AttributeRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void saveAttribute(Attribute attribute, Long productId){
        jdbcTemplate.update("INSERT INTO ATTRIBUTE  (name, `value`, product_id) VALUES (?, ?, ?)",
                attribute.getName(), attribute.getValue(), productId);
    }

    public boolean exists(long productId, String key) {
        String sql = "SELECT COUNT(*) FROM attribute WHERE product_id = ? AND name = ?";
        Integer count = jdbcTemplate.queryForObject(sql, new Object[]{productId, key}, Integer.class);
        return count != null && count > 0;
    }

    public void update(long productId, String key, String value) {
        String sql = "UPDATE attribute SET `value` = ? WHERE product_id = ? AND name = ?";
        jdbcTemplate.update(sql, value, productId, key);
    }

    public void insert(long productId, String key, String value) {
        String sql = "INSERT INTO attribute (product_id, name, `value`) VALUES (?, ?, ?)";
        jdbcTemplate.update(sql, productId, key, value);
    }

    public void upsert(long productId, String key, String value) {
        if (exists(productId, key)) {
            update(productId, key, value);
        } else {
            insert(productId, key, value);
        }
    }

    public List<Attribute> findByProductId(long productId) {
        String sql = "SELECT id, name, `value` FROM attribute WHERE product_id = ?";

        return jdbcTemplate.query(
                sql,
                new Object[]{productId},
                (rs, rowNum) -> {
                    Attribute a = new Attribute(rs.getString("name"), rs.getString("value"));
                    a.setId(rs.getLong("id"));
                    return a;
                }
        );
    }


}
