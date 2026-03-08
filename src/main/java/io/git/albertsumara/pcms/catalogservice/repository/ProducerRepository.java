package io.git.albertsumara.pcms.catalogservice.repository;

import io.git.albertsumara.pcms.catalogservice.model.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProducerRepository {

    private final ProductRepository productRepository;
    private final AttributeRepository attributeRepository;

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert productInsert;

    @Autowired
    public ProducerRepository(JdbcTemplate jdbcTemplate,
                              ProductRepository productRepository, AttributeRepository attributeRepository) {
        this.jdbcTemplate = jdbcTemplate;
        this.productInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("producer")
                .usingGeneratedKeyColumns("id");

        this.productRepository = productRepository;
        this.attributeRepository = attributeRepository;

    }

    public long saveProducer(Producer producer) {

        if(isNameAlreadyExists(producer.getName()))
            throw new IllegalArgumentException("Name: " + producer.getName() + " already exists for other object.");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", producer.getName());

        Number generatedId = productInsert.executeAndReturnKey(parameters);
        return generatedId.longValue();
    }

    public boolean isNameAlreadyExists(String name){
        String sql = "SELECT EXISTS(SELECT 1 FROM producer WHERE name = ?)";
        Boolean exists = jdbcTemplate.queryForObject(sql, new Object[]{name}, Boolean.class);
        return exists != null && exists;
    }

    public Optional<Producer> findByIdAndDelete(long producerId) {
        String selectSql = "SELECT id, name FROM producer WHERE id = ?";
        String deleteSql = "DELETE FROM producer WHERE id = ?";

        try {
            Producer producer = jdbcTemplate.queryForObject(selectSql, new Object[]{producerId},
                    (rs, rowNum) -> {
                Producer p = new Producer(rs.getLong("id"), rs.getString("name"));
                return p;
            });
            jdbcTemplate.update(deleteSql, producerId);
            return Optional.of(producer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public List<Producer> getAllProducers() {
        String sql = "SELECT id, name FROM producer";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    Producer p = new Producer(rs.getLong("id"), rs.getString("name"));
                    p.setProductList(productRepository.getAllProductsByProducerId(rs.getLong("id")));
                    return p;
                });
    }

    public Optional<Producer> findById(long producerId) {
        String selectSql = "SELECT id, name FROM producer WHERE id = ?";

        try {
            Producer producer = jdbcTemplate.queryForObject(selectSql, new Object[]{producerId},
                    (rs, rowNum) -> {
                        Producer p = new Producer(rs.getLong("id"), rs.getString("name"));
                        p.setProductList(productRepository.getAllProductsByProducerId(p.getId()));
                        return p;
                    });
            return Optional.of(producer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }


}
