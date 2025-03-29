package com.example.demo.services;

import com.example.demo.entity.User;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User addUser(User user) {
        // Hash the password before saving the user
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        // If password is being updated, hash it before saving
        Optional<User> existingUser = userRepository.findById(user.getId());
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setUsername(user.getUsername());
            updatedUser.setName(user.getName());
            updatedUser.setRole(user.getRole());
            updatedUser.setDepartment(user.getDepartment());
            // Only update password if it's provided
            if (user.getPassword() != null && !user.getPassword().isEmpty()) {
                updatedUser.setPassword(passwordEncoder.encode(user.getPassword()));
            }
            return userRepository.save(updatedUser);
        } else {
            throw new RuntimeException("User not found");
        }
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public List<User> getUsersByRole(User.Role role) {
        return userRepository.findByRole(role);
    }

    public List<User> getUsersByDepartment(String department) {
        return userRepository.findByDepartment(department);
    }

    public boolean hasAdminAccess(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.map(user -> user.getRole() == User.Role.ADMIN).orElse(false);
    }

    public boolean hasManagerAccess(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        return optionalUser.map(user -> user.getRole() == User.Role.ADMIN || user.getRole() == User.Role.MANAGER)
                .orElse(false);
    }

    // New methods to support the missing endpoints

    public List<String> getAllDepartments() {
        return userRepository.findAll().stream()
                .map(User::getDepartment)
                .distinct()
                .collect(Collectors.toList());
    }

    public Map<String, Long> getDepartmentUserCounts() {
        return userRepository.findAll().stream()
                .collect(Collectors.groupingBy(User::getDepartment, Collectors.counting()));
    }

    public Map<String, Long> getRoleCounts() {
        return userRepository.findAll().stream()
                .collect(Collectors.groupingBy(user -> user.getRole().toString(), Collectors.counting()));
    }
}