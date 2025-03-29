package com.example.demo.controllers;

import com.example.demo.entity.ItemRequest;
import com.example.demo.services.ItemRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/requests")
@CrossOrigin(origins = "http://localhost:4200")
public class ItemRequestController {
    @Autowired
    private ItemRequestService itemRequestService;

    @GetMapping
    public ResponseEntity<List<ItemRequest>> getAllRequests() {
        return ResponseEntity.ok(itemRequestService.getAllRequests());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ItemRequest> getRequestById(@PathVariable Long id) {
        Optional<ItemRequest> request = itemRequestService.getRequestById(id);
        return request.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<ItemRequest> submitRequest(@RequestBody ItemRequest itemRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(itemRequestService.submitRequest(itemRequest));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ItemRequest> updateRequest(@PathVariable Long id, @RequestBody ItemRequest itemRequest) {
        if (!itemRequestService.getRequestById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        itemRequest.setId(id);
        return ResponseEntity.ok(itemRequestService.updateRequest(itemRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRequest(@PathVariable Long id) {
        if (!itemRequestService.getRequestById(id).isPresent()) {
            return ResponseEntity.notFound().build();
        }
        itemRequestService.deleteRequest(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<ItemRequest>> getRequestsByStatus(@PathVariable ItemRequest.RequestStatus status) {
        return ResponseEntity.ok(itemRequestService.getRequestsByStatus(status));
    }
    
    @GetMapping("/department/{department}")
    public ResponseEntity<List<ItemRequest>> getRequestsByDepartment(@PathVariable String department) {
        return ResponseEntity.ok(itemRequestService.getRequestsByDepartment(department));
    }
    
    @GetMapping("/status-counts")
    public ResponseEntity<Map<String, Long>> getRequestStatusCounts() {
        return ResponseEntity.ok(itemRequestService.getRequestStatusCounts());
    }
    
    @GetMapping("/department-counts")
    public ResponseEntity<Map<String, Long>> getRequestsByDepartmentCount() {
        return ResponseEntity.ok(itemRequestService.getRequestsByDepartmentCount());
    }
    
    @PostMapping("/{id}/approve")
    public ResponseEntity<ItemRequest> approveRequest(@PathVariable Long id) {
        ItemRequest approvedRequest = itemRequestService.approveRequest(id);
        if (approvedRequest != null) {
            return ResponseEntity.ok(approvedRequest);
        }
        return ResponseEntity.notFound().build();
    }
    
    @PostMapping("/{id}/reject")
    public ResponseEntity<ItemRequest> rejectRequest(@PathVariable Long id) {
        ItemRequest rejectedRequest = itemRequestService.rejectRequest(id);
        if (rejectedRequest != null) {
            return ResponseEntity.ok(rejectedRequest);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/requestor/{name}")
    public ResponseEntity<List<ItemRequest>> getRequestsByRequestor(@PathVariable String name) {
        return ResponseEntity.ok(itemRequestService.getRequestsByRequestor(name));
    }
    
    @GetMapping("/count")
    public ResponseEntity<Long> getTotalRequestsCount() {
        return ResponseEntity.ok(itemRequestService.getTotalRequestsCount());
    }
}