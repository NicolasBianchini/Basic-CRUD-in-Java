package com.example.basic_crud.domain;

import java.util.UUID;

public class NotFoundException extends RuntimeException {
    public NotFoundException(String resource, UUID id) {
        super("Resource " + resource + " not found with id: " + id);
    }
}
