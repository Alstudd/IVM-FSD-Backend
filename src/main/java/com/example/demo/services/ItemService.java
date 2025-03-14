package com.example.demo.services;

import com.example.demo.entity.Item;
import com.example.demo.repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ItemService {
    @Autowired
    private ItemRepository itemRepository;

    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    public Optional<Item> getItemById(Long id) {
        return itemRepository.findById(id);
    }

    public Optional<Item> getItemByName(String name) {
        return itemRepository.findByName(name);
    }

    public Item addItem(Item item) {
        return itemRepository.save(item);
    }

    public Item updateItem(Item item) {
        return itemRepository.save(item);
    }

    public void deleteItem(Long id) {
        itemRepository.deleteById(id);
    }

    public List<Item> getItemsByCategory(String category) {
        return itemRepository.findByCategory(category);
    }

    public List<Item> getLowStockItems() {
        return itemRepository.findLowStockItems();
    }

    public List<String> getAllCategories() {
        return itemRepository.findAllCategories();
    }

    public Map<String, Integer> getItemCategoryCounts() {
        List<String> categories = itemRepository.findAllCategories();
        Map<String, Integer> categoryCounts = new HashMap<>();
        
        for (String category : categories) {
            List<Item> items = itemRepository.findByCategory(category);
            categoryCounts.put(category, items.size());
        }
        
        return categoryCounts;
    }

    public boolean checkItemAvailability(String itemName, Integer requestedQuantity) {
        Optional<Item> optionalItem = itemRepository.findByName(itemName);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            return item.getQuantityAvailable() >= requestedQuantity;
        }
        return false;
    }

    public void updateItemQuantity(String itemName, Integer quantityChange) {
        Optional<Item> optionalItem = itemRepository.findByName(itemName);
        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.setQuantityAvailable(item.getQuantityAvailable() + quantityChange);
            itemRepository.save(item);
        }
    }
}