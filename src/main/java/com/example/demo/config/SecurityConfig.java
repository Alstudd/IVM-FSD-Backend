package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Public pages
                .requestMatchers("/", "/css/**", "/js/**", "/images/**").permitAll()
                // Request form is accessible to all authenticated users
                .requestMatchers("/request-form").authenticated()
                // Update form, dashboard, and API endpoints need manager or admin access
                .requestMatchers("/update-form", "/dashboard", "/api/items/**", "/api/dashboard/**").hasAnyRole("MANAGER", "ADMIN")
                // Admin-only endpoints
                .requestMatchers("/api/users/**").hasRole("ADMIN")
                // User can see their own request summary
                .requestMatchers("/request-summary").authenticated()
                // User can submit requests
                .requestMatchers("/api/requests").authenticated()
                // Manager/Admin can approve/reject requests
                .requestMatchers("/api/requests/{id}/approve", "/api/requests/{id}/reject").hasAnyRole("MANAGER", "ADMIN")
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/")
                .permitAll()
            )
            .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .permitAll()
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}