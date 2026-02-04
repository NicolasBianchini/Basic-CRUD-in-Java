package com.example.basic_crud.domain;

import com.example.basic_crud.controller.dto.SearchItemRequest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ItemRepository {
    List<Item> search(SearchItemRequest req);

    Item save(Item item);
    Optional<Item> findById(UUID id);
    List<Item> findAll();
    boolean existsById(UUID id);
    void deleteById(UUID id);
}
