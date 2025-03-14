package com.example.demo.repositories;

import com.example.demo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByName(String name);
    
    List<Item> findByCategory(String category);
    
    @Query("SELECT i FROM Item i WHERE i.quantityAvailable < 10")
    List<Item> findLowStockItems();
    
    @Query("SELECT DISTINCT i.category FROM Item i")
    List<String> findAllCategories();
}