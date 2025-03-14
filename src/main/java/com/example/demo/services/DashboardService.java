package com.example.demo.services;

import com.example.demo.entity.Item;
import com.example.demo.entity.ItemRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DashboardService {
    @Autowired
    private ItemService itemService;
    
    @Autowired
    private ItemRequestService requestService;

    public Map<String, Object> getDashboardData() {
        Map<String, Object> dashboardData = new HashMap<>();
        
        // Get all inventory items
        List<Item> allItems = itemService.getAllItems();
        dashboardData.put("totalItemsCount", allItems.size());
        
        // Get low stock items
        List<Item> lowStockItems = itemService.getLowStockItems();
        dashboardData.put("lowStockItemsCount", lowStockItems.size());
        dashboardData.put("lowStockItems", lowStockItems);
        
        // Get category distribution
        Map<String, Integer> categoryCounts = itemService.getItemCategoryCounts();
        dashboardData.put("categoryCounts", categoryCounts);
        
        // Get request statistics
        Map<String, Long> requestStatusCounts = requestService.getRequestStatusCounts();
        dashboardData.put("requestStatusCounts", requestStatusCounts);
        
        // Get department request distribution
        Map<String, Long> departmentRequestCounts = requestService.getRequestsByDepartmentCount();
        dashboardData.put("departmentRequestCounts", departmentRequestCounts);
        
        // Calculate total inventory quantity
        int totalQuantity = 0;
        for (Item item : allItems) {
            totalQuantity += item.getQuantityAvailable();
        }
        dashboardData.put("totalInventoryQuantity", totalQuantity);
        
        // Get total requests
        Long totalRequests = requestService.getTotalRequestsCount();
        dashboardData.put("totalRequestsCount", totalRequests);
        
        return dashboardData;
    }
    
    public Map<String, Object> getLowStockItemsData() {
        Map<String, Object> lowStockData = new HashMap<>();
        
        List<Item> lowStockItems = itemService.getLowStockItems();
        lowStockData.put("lowStockItems", lowStockItems);
        lowStockData.put("lowStockItemsCount", lowStockItems.size());
        
        return lowStockData;
    }
    
    public Map<String, Object> getRequestSummaryData() {
        Map<String, Object> requestSummary = new HashMap<>();
        
        // Get pending requests
        List<ItemRequest> pendingRequests = requestService.getRequestsByStatus(ItemRequest.RequestStatus.PENDING);
        requestSummary.put("pendingRequests", pendingRequests);
        requestSummary.put("pendingRequestsCount", pendingRequests.size());
        
        // Get approved requests
        List<ItemRequest> approvedRequests = requestService.getRequestsByStatus(ItemRequest.RequestStatus.APPROVED);
        requestSummary.put("approvedRequests", approvedRequests);
        requestSummary.put("approvedRequestsCount", approvedRequests.size());
        
        // Get rejected requests
        List<ItemRequest> rejectedRequests = requestService.getRequestsByStatus(ItemRequest.RequestStatus.REJECTED);
        requestSummary.put("rejectedRequests", rejectedRequests);
        requestSummary.put("rejectedRequestsCount", rejectedRequests.size());
        
        // Get total requests
        Long totalRequests = requestService.getTotalRequestsCount();
        requestSummary.put("totalRequestsCount", totalRequests);
        
        return requestSummary;
    }
}