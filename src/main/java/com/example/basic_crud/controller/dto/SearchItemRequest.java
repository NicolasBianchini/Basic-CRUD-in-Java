package com.example.basic_crud.controller.dto;

import java.math.BigDecimal;

public record SearchItemRequest(
        String name,
        String description,
        BigDecimal price
) {}
