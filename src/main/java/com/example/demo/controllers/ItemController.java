package com.example.demo.controllers;

import com.example.demo.entity.Item;
import com.example.demo.services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "http://localhost:4200")
public class ItemController {
    @Autowired
    private ItemService itemService;

    @GetMapping
    public ResponseEntity<List<Item>> getAllItems() {
        return ResponseEntity.ok(itemService.getAllItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        Optional<Item> item = itemService.getItemById(id);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Item> addItem(@RequestBody Item item) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemService.addItem(item));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @RequestBody Item item) {
        if (!itemService.getItemById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        item.setId(id);
        return ResponseEntity.ok(itemService.updateItem(item));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        if (!itemService.getItemById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        itemService.deleteItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/categories")
    public ResponseEntity<List<String>> getAllCategories() {
        return ResponseEntity.ok(itemService.getAllCategories());
    }
    
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Item>> getItemsByCategory(@PathVariable String category) {
        return ResponseEntity.ok(itemService.getItemsByCategory(category));
    }
    
    @GetMapping("/low-stock")
    public ResponseEntity<List<Item>> getLowStockItems() {
        return ResponseEntity.ok(itemService.getLowStockItems());
    }
    
    @GetMapping("/category-counts")
    public ResponseEntity<Map<String, Integer>> getItemCategoryCounts() {
        return ResponseEntity.ok(itemService.getItemCategoryCounts());
    }
    
    @GetMapping("/name/{name}")
    public ResponseEntity<Item> getItemByName(@PathVariable String name) {
        Optional<Item> item = itemService.getItemByName(name);
        return item.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PostMapping("/check-availability")
    public ResponseEntity<Boolean> checkItemAvailability(@RequestParam String itemName, @RequestParam Integer quantity) {
        boolean isAvailable = itemService.checkItemAvailability(itemName, quantity);
        return ResponseEntity.ok(isAvailable);
    }
}