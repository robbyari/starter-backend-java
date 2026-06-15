package com.pancaran.master.feature.sample.api;

import com.pancaran.master.common.APIResponse;
import com.pancaran.master.feature.sample.dto.SampleDto;
import com.pancaran.master.feature.sample.service.SampleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/samples")
public class SampleController {

    private final SampleService service;

    public SampleController(SampleService service) {
        this.service = service;
    }

    // 1. Contoh endpoint CREATE menggunakan JPA
    @PostMapping("/jpa")
    public ResponseEntity<APIResponse<SampleDto>> createUsingJpa(@RequestBody SampleDto dto) {
        return ResponseEntity.ok(APIResponse.success(service.createUsingJpa(dto)));
    }

    // 2. Contoh endpoint CREATE menggunakan JdbcTemplate
    @PostMapping("/jdbc")
    public ResponseEntity<APIResponse<SampleDto>> createUsingJdbc(@RequestBody SampleDto dto) {
        return ResponseEntity.ok(APIResponse.success(service.createUsingJdbc(dto)));
    }
}
