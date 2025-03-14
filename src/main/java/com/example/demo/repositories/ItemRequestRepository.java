package com.example.demo.repositories;

import com.example.demo.entity.ItemRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRequestRepository extends JpaRepository<ItemRequest, Long> {
    List<ItemRequest> findByStatus(ItemRequest.RequestStatus status);
    
    List<ItemRequest> findByDepartment(String department);
    
    @Query("SELECT COUNT(r) FROM ItemRequest r WHERE r.status = :status")
    Long countByStatus(ItemRequest.RequestStatus status);
    
    @Query("SELECT r.department, COUNT(r) FROM ItemRequest r GROUP BY r.department")
    List<Object[]> countRequestsByDepartment();
    
    List<ItemRequest> findByRequestorName(String requestorName);
}