package com.example.demo.dto;

import com.example.demo.entity.User;

public class LoginResponse {
    private String token;
    private String username;
    private User.Role role;

    // Full constructor
    public LoginResponse(String token, String username, User.Role role) {
        this.token = token;
        this.username = username;
        this.role = role;
    }

    // Getters
    public String getToken() {
        return token;
    }

    public String getUsername() {
        return username;
    }

    public User.Role getRole() {
        return role;
    }

    // Setters
    public void setToken(String token) {
        this.token = token;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRole(User.Role role) {
        this.role = role;
    }
}