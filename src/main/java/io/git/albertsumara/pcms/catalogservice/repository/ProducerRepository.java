package io.git.albertsumara.pcms.catalogservice.repository;

import io.git.albertsumara.pcms.catalogservice.model.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ProducerRepository {

    private final JdbcTemplate jdbcTemplate;
    private final SimpleJdbcInsert productInsert;

    @Autowired
    public ProducerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;

        this.productInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("producer")
                .usingGeneratedKeyColumns("id");
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
                Producer p = new Producer(rs.getString("name"), rs.getLong("id"));
                return p;
            });
            jdbcTemplate.update(deleteSql, producerId);
            return Optional.of(producer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

}
