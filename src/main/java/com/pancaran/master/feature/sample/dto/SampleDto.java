package com.pancaran.master.feature.sample.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Data;
import java.time.OffsetDateTime;

@Data
@JsonPropertyOrder({ "id", "name", "createdAt" })
public class SampleDto {
    private Long id;
    private String name;
    private OffsetDateTime createdAt;
}
