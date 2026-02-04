package com.example.basic_crud.infra;

import java.util.*;

import com.example.basic_crud.controller.dto.SearchItemRequest;
import com.example.basic_crud.domain.Item;
import com.example.basic_crud.domain.ItemRepository;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Repository;

@Repository
public class InMemoryItemRepository implements ItemRepository {

    private final Map<UUID, Item> store = new ConcurrentHashMap<>();

    @Override
    public Item save(Item item) {
        store.put(item.id(), item);
        return item;
    }

    @Override
    public Optional<Item> findById(UUID id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Item> findAll() {
        return store.values().stream()
                .sorted(Comparator.comparing(Item::createdAt))
                .toList();
    }

    @Override
    public boolean existsById(UUID id) {
        return store.containsKey(id);
    }

    @Override
    public void deleteById(UUID id) {
        store.remove(id);
    }

    @Override
    public List<Item> search(SearchItemRequest req) {
        return store.values().stream()
                .filter(i -> req.name() == null || i.name().toLowerCase().contains(req.name().toLowerCase()))
                .filter(i -> req.description() == null || (i.description() != null &&
                        i.description().toLowerCase().contains(req.description().toLowerCase())))
                .filter(i -> req.price() == null || i.price().compareTo(req.price()) == 0)
                .sorted(Comparator.comparing(Item::createdAt))
                .toList();
    }
}
