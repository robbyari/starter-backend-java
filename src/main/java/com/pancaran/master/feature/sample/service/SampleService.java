package com.pancaran.master.feature.sample.service;

import com.pancaran.master.feature.sample.dto.SampleDto;
import com.pancaran.master.feature.sample.entity.SampleEntity;
import com.pancaran.master.feature.sample.repository.SampleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SampleService {

    private final SampleRepository repository;

    public SampleService(SampleRepository repository) {
        this.repository = repository;
    }

    // CREATE DENGAN JPA
    @Transactional("master-dbTransactionManager")
    public SampleDto createUsingJpa(SampleDto dto) {
        SampleEntity entity = SampleEntity.builder().name(dto.getName()).build();
        repository.insertJpa(entity);
        dto.setId(entity.getId());
        return dto;
    }

    // CREATE DENGAN JDBC
    @Transactional("master-dbTransactionManager")
    public SampleDto createUsingJdbc(SampleDto dto) {
        SampleEntity entity = SampleEntity.builder().name(dto.getName()).build();
        SampleEntity savedEntity = repository.insertJdbc(entity);
        dto.setId(savedEntity.getId());
        return dto;
    }
}
