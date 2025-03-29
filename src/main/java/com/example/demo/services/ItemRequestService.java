package com.example.demo.services;

import com.example.demo.entity.ItemRequest;
import com.example.demo.repositories.ItemRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ItemRequestService {
    @Autowired
    private ItemRequestRepository itemRequestRepository;

    @Autowired
    private ItemService itemService;

    public List<ItemRequest> getAllRequests() {
        return itemRequestRepository.findAll();
    }

    public Optional<ItemRequest> getRequestById(Long id) {
        return itemRequestRepository.findById(id);
    }

    public ItemRequest submitRequest(ItemRequest itemRequest) {
        return itemRequestRepository.save(itemRequest);
    }

    public ItemRequest updateRequest(ItemRequest itemRequest) {
        return itemRequestRepository.save(itemRequest);
    }

    public void deleteRequest(Long id) {
        itemRequestRepository.deleteById(id);
    }

    public List<ItemRequest> getRequestsByStatus(ItemRequest.RequestStatus status) {
        return itemRequestRepository.findByStatus(status);
    }

    public List<ItemRequest> getRequestsByDepartment(String department) {
        return itemRequestRepository.findByDepartment(department);
    }

    public Map<String, Long> getRequestStatusCounts() {
        Map<String, Long> statusCounts = new HashMap<>();
        statusCounts.put("PENDING", itemRequestRepository.countByStatus(ItemRequest.RequestStatus.PENDING));
        statusCounts.put("APPROVED", itemRequestRepository.countByStatus(ItemRequest.RequestStatus.APPROVED));
        statusCounts.put("REJECTED", itemRequestRepository.countByStatus(ItemRequest.RequestStatus.REJECTED));
        return statusCounts;
    }

    public Map<String, Long> getRequestsByDepartmentCount() {
        List<Object[]> departmentCounts = itemRequestRepository.countRequestsByDepartment();
        Map<String, Long> departmentCountMap = new HashMap<>();

        for (Object[] result : departmentCounts) {
            String department = (String) result[0];
            Long count = (Long) result[1];
            departmentCountMap.put(department, count);
        }

        return departmentCountMap;
    }

    public ItemRequest approveRequest(Long requestId) {
        Optional<ItemRequest> optionalRequest = itemRequestRepository.findById(requestId);
        if (!optionalRequest.isPresent()) {
            throw new RuntimeException("Request with ID " + requestId + " not found");
        }

        ItemRequest request = optionalRequest.get();

        // Check if the item is available in the required quantity
        if (!itemService.checkItemAvailability(request.getItemName(), request.getQuantityRequired())) {
            throw new RuntimeException("Insufficient quantity available for " + request.getItemName());
        }

        request.setStatus(ItemRequest.RequestStatus.APPROVED);
        // Update the item quantity in inventory
        itemService.updateItemQuantity(request.getItemName(), -request.getQuantityRequired());
        return itemRequestRepository.save(request);
    }

    public ItemRequest rejectRequest(Long requestId) {
        Optional<ItemRequest> optionalRequest = itemRequestRepository.findById(requestId);
        if (optionalRequest.isPresent()) {
            ItemRequest request = optionalRequest.get();
            request.setStatus(ItemRequest.RequestStatus.REJECTED);
            return itemRequestRepository.save(request);
        }
        return null;
    }

    public List<ItemRequest> getRequestsByRequestor(String requestorName) {
        return itemRequestRepository.findByRequestorName(requestorName);
    }

    public Long getTotalRequestsCount() {
        return itemRequestRepository.count();
    }
}