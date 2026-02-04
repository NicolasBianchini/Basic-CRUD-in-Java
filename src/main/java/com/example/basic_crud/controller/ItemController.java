package com.example.basic_crud.controller;

import com.example.basic_crud.controller.dto.CreateItemRequest;
import com.example.basic_crud.controller.dto.ItemResponse;
import com.example.basic_crud.controller.dto.SearchItemRequest;
import com.example.basic_crud.controller.dto.UpdateItemRequest;
import com.example.basic_crud.domain.Item;
import com.example.basic_crud.service.ItemService;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/items", produces = MediaType.APPLICATION_JSON_VALUE)
public class ItemController {
        private final ItemService service;

        public ItemController(ItemService service) {
            this.service = service;
        }

        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<ItemResponse> create(@Valid @RequestBody CreateItemRequest req) {
            Item created = service.create(req.name(),  req.description(), req.price(), req.quantity());
            URI location = URI.create("/api/items/" + created.id());
            return ResponseEntity.created(location).body(toResponse(created));
        }

            @GetMapping
            public List<ItemResponse> list() {
                return service.list().stream().map(this::toResponse).toList();
            }

            @GetMapping("/{id:[0-9a-fA-F\\-]{36}}")
            public  ItemResponse get(@PathVariable UUID id) {
                return toResponse(service.getById(id));
            }

        @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
        public ItemResponse update(@PathVariable UUID id, @Valid @RequestBody UpdateItemRequest req) {
            return toResponse(service.update(id, req.name(), req.description(), req.price(), req.quantity()));
        }

        @DeleteMapping("/{id}")
        public ResponseEntity<Void> delete(@PathVariable UUID id) {
            service.delete(id);
            return ResponseEntity.noContent().build();
        }

        @RequestMapping(value = "/search", method = {RequestMethod.GET, RequestMethod.POST}, consumes = MediaType.APPLICATION_JSON_VALUE)
        public List<ItemResponse> search(@RequestBody SearchItemRequest req) {
            return service.search(req).stream().map(this::toResponse).toList();
        }

        private ItemResponse toResponse(Item item) {
            return new ItemResponse(
                    item.id(),
                    item.name(),
                    item.description(),
                    item.price(),
                    item.quantity(),
                    item.createdAt(),
                    item.updatedAt()
            );
        }
}
