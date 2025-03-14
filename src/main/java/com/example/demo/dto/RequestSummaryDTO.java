package com.example.demo.dto;

import com.example.demo.entity.ItemRequest;

import java.util.List;

public class RequestSummaryDTO {
    private Long totalRequests;
    private Long pendingRequests;
    private Long approvedRequests;
    private Long rejectedRequests;
    private List<ItemRequest> recentRequests;

    public RequestSummaryDTO(Long totalRequests, Long pendingRequests, Long approvedRequests, Long rejectedRequests, List<ItemRequest> recentRequests) {
        this.totalRequests = totalRequests;
        this.pendingRequests = pendingRequests;
        this.approvedRequests = approvedRequests;
        this.rejectedRequests = rejectedRequests;
        this.recentRequests = recentRequests;
    }

    // Getters and Setters
    public Long getTotalRequests() {
        return totalRequests;
    }

    public void setTotalRequests(Long totalRequests) {
        this.totalRequests = totalRequests;
    }

    public Long getPendingRequests() {
        return pendingRequests;
    }

    public void setPendingRequests(Long pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    public Long getApprovedRequests() {
        return approvedRequests;
    }

    public void setApprovedRequests(Long approvedRequests) {
        this.approvedRequests = approvedRequests;
    }

    public Long getRejectedRequests() {
        return rejectedRequests;
    }

    public void setRejectedRequests(Long rejectedRequests) {
        this.rejectedRequests = rejectedRequests;
    }

    public List<ItemRequest> getRecentRequests() {
        return recentRequests;
    }

    public void setRecentRequests(List<ItemRequest> recentRequests) {
        this.recentRequests = recentRequests;
    }
}