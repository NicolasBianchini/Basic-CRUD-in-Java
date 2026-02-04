package com.example.basic_crud.service;

import com.example.basic_crud.controller.dto.SearchItemRequest;
import com.example.basic_crud.domain.Item;
import com.example.basic_crud.domain.ItemRepository;
import com.example.basic_crud.domain.NotFoundException;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository repository;

    public Item create(String name, String description, BigDecimal price, Integer quantity) {
        Instant now = Instant.now();
        Item item = new Item(
                UUID.randomUUID(),
                name,
                description,
                price,
                quantity,
                now,
                now
        );

        return repository.save(item);
    }

    public Item getById(UUID id) {
        return repository.findById(id).orElseThrow(() -> new NotFoundException("Item", id));
    }

    public List<Item> list() {
        return repository.findAll();
    }

    public Item update(UUID id, String name, String description, BigDecimal price, Integer quantity) {
        Item existing = getById(id);
        Item updated = new Item(
                existing.id(),
                name,
                description,
                price,
                quantity,
                existing.createdAt(),
                Instant.now()
        );

        return repository.save(updated);
    }

    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new NotFoundException("Item", id);
        }
        repository.deleteById(id);
    }

    public List<Item> search(SearchItemRequest req) {
        return repository.search(req);
    }
}
