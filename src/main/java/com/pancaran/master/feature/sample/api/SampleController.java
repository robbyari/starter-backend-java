package com.pancaran.master.feature.sample.api;

import com.pancaran.master.common.ApiException;
import com.pancaran.master.common.APIResponse;
import com.pancaran.master.feature.sample.dto.SampleDto;
import com.pancaran.master.feature.sample.service.SampleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new ApiException(400, "Name parameter is required and cannot be empty");
        }
        if ("trigger-error".equalsIgnoreCase(dto.getName())) {
            throw new RuntimeException("Simulated unexpected database connection leak!");
        }
        if ("trigger-mui-error".equalsIgnoreCase(dto.getName())) {
            Map<String, String> errors = new HashMap<>();
            errors.put("name", "Name cannot be 'trigger-mui-error'");
            errors.put("code", "Invalid code format");
            throw new ApiException(400, errors);
        }
        return ResponseEntity.ok(APIResponse.success(service.createUsingJpa(dto)));
    }

    // 2. Contoh endpoint CREATE menggunakan JdbcTemplate
    @PostMapping("/jdbc")
    public ResponseEntity<APIResponse<SampleDto>> createUsingJdbc(@RequestBody SampleDto dto) {
        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            throw new ApiException(400, "Name parameter is required and cannot be empty");
        }
        if ("trigger-error".equalsIgnoreCase(dto.getName())) {
            throw new RuntimeException("Simulated unexpected database connection leak!");
        }
        if ("trigger-mui-error".equalsIgnoreCase(dto.getName())) {
            Map<String, String> errors = new HashMap<>();
            errors.put("name", "Name cannot be 'trigger-mui-error'");
            errors.put("code", "Invalid code format");
            throw new ApiException(400, errors);
        }
        return ResponseEntity.ok(APIResponse.success(service.createUsingJdbc(dto)));
    }
}
