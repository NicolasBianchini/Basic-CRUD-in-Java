package com.example.basic_crud.controller.dto;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

public record CreateItemRequest(
        @NotBlank @Size(max = 120) String name,
        @Size(max = 500) String description,
        @NotNull @DecimalMin(value = "0.0", inclusive = true) BigDecimal price,
        @NotNull @Min(0) Integer quantity
) {}
