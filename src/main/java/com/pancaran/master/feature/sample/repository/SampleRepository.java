package com.pancaran.master.feature.sample.repository;

import com.pancaran.master.feature.sample.entity.SampleEntity;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.List;

@Repository
public class SampleRepository {

    @PersistenceContext(unitName = "master-dbEntityManagerFactory")
    private EntityManager entityManager;

    private final JdbcTemplate jdbcTemplate;

    public SampleRepository(@Qualifier("master-dbJdbcTemplate") JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // --- 1. JPA Example ---
    public void insertJpa(SampleEntity entity) {
        entityManager.persist(entity);
        entityManager.flush();
        entityManager.refresh(entity);
    }

    // --- 2. JdbcTemplate Example ---
    public SampleEntity insertJdbc(SampleEntity entity) {
        String sql = "INSERT INTO example_table (name) VALUES (?) RETURNING id, created_at";
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
            entity.setId(rs.getLong("id"));
            entity.setCreatedAt(rs.getObject("created_at", java.time.OffsetDateTime.class));
            return entity;
        }, entity.getName());
    }
}
