package com.example.basic_crud.domain;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record Item(
        UUID id,
        String name,
        String description,
        BigDecimal price,
        Integer quantity,
        Instant createdAt,
        Instant updatedAt
) {}
