package com.example.demo.controllers;

import com.example.demo.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<Map<String, Object>> getDashboardData() {
        return ResponseEntity.ok(dashboardService.getDashboardData());
    }

    @GetMapping("/low-stock")
    public ResponseEntity<Map<String, Object>> getLowStockItemsData() {
        return ResponseEntity.ok(dashboardService.getLowStockItemsData());
    }

    @GetMapping("/request-summary")
    public ResponseEntity<Map<String, Object>> getRequestSummaryData() {
        return ResponseEntity.ok(dashboardService.getRequestSummaryData());
    }
}