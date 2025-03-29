package com.example.demo.dto;

import com.example.demo.entity.User;

public class LoginResponse {
    private String token;
    private Long userId;
    private String username;
    private User.Role role;
    private String name;
    private String department;

    public LoginResponse(String token, Long userId, String username, User.Role role, String name, String department) {
        this.token = token;
        this.userId = userId;
        this.username = username;
        this.role = role;
        this.name = name;
        this.department = department;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public User.Role getRole() {
        return role;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}