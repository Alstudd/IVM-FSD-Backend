package com.example.demo.controllers; // Your main controller package

// Spring Framework Imports
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

// Java Utility Imports
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// Logging Imports
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// Validation Imports
import jakarta.validation.Valid;
import jakarta.servlet.http.HttpServletRequest;

// Your Project Specific Imports
import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.LoginResponse;
import com.example.demo.entity.User;
import com.example.demo.services.UserService;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {
    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    private String generateJwtToken(Authentication authentication) {
        // Implement JWT token generation logic
        // This is a placeholder - you'll need to use a library like JJWT
        return "dummy-jwt-token-" + authentication.getName();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        try {
            // Authenticate the user
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword())
            );

            // Set the authentication in the security context
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Generate JWT token
            String jwt = generateJwtToken(authentication);

            // Get the current user
            Optional<User> optionalUser = userService.getUserByUsername(loginRequest.getUsername());
            
            if (optionalUser.isPresent()) {
                User currentUser = optionalUser.get();
                
                // Create a more structured response
                LoginResponse loginResponse = new LoginResponse(
                    jwt, 
                    currentUser.getId(),
                    currentUser.getUsername(), 
                    currentUser.getRole(),
                    currentUser.getName(),
                    currentUser.getDepartment()
                );
                
                return ResponseEntity.ok(loginResponse);
            } else {
                logger.error("User not found: {}", loginRequest.getUsername());
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "User not found"));
            }
        } catch (BadCredentialsException e) {
            logger.error("Invalid login attempt: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(Map.of("message", "Invalid username or password"));
        } catch (Exception e) {
            logger.error("Login error", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Map.of("message", "An error occurred during login"));
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        // Optional: Implement token invalidation if using stateless JWT
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
}