package com.pancaran.master.feature.sample.dto;

import lombok.Data;
import java.time.OffsetDateTime;

@Data
public class SampleDto {
    private Long id;
    private String name;
    private OffsetDateTime createdAt;
}
