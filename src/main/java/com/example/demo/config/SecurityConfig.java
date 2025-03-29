package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

        // Optional: Add JWT filter if you're using JWT authentication
        // @Autowired
        // private JwtAuthenticationFilter jwtAuthenticationFilter;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http
                                // CORS Configuration
                                .cors(cors -> cors.configurationSource(corsConfigurationSource()))

                                // CSRF Protection (Disabled for API, but consider enabling for web forms)
                                .csrf(csrf -> csrf.disable())

                                // Session Management
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                                // Authorization Rules
                                .authorizeHttpRequests(auth -> auth
                                                // Public Endpoints (No Authentication Required)
                                                .requestMatchers(
                                                                "/",
                                                                "/index.html",
                                                                "/error",
                                                                "/css/**",
                                                                "/js/**",
                                                                "/images/**",
                                                                "/public/**",
                                                                "/login",
                                                                "/register",
                                                                "/api/auth/login",
                                                                "/api/auth/register",
                                                                // Permit all item-related endpoints for now
                                                                "/api/items/**",
                                                                "/api/requests/**",
                                                                "/api/users/**")
                                                .permitAll()

                                                // Authenticated User Endpoints
                                                .requestMatchers(
                                                                "/dashboard",
                                                                "/profile")
                                                .authenticated()

                                                // Manager & Admin Endpoints
                                                .requestMatchers(
                                                                "/admin/**",
                                                                "/manager/**",
                                                                "/api/admin/**",
                                                                "/api/dashboard/**")
                                                .hasAnyAuthority("ROLE_MANAGER", "ROLE_ADMIN")

                                                // Admin-Only Endpoints
                                                .requestMatchers(
                                                                "/api/system/**")
                                                .hasAuthority("ROLE_ADMIN")

                                                // Default: All other requests require authentication
                                                .anyRequest().authenticated())

                                // Form Login Configuration
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .defaultSuccessUrl("/dashboard", true)
                                                .failureUrl("/login?error=true")
                                                .permitAll())

                                // Logout Configuration
                                .logout(logout -> logout
                                                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                                                .logoutSuccessUrl("/login?logout=true")
                                                .invalidateHttpSession(true)
                                                .deleteCookies("JSESSIONID")
                                                .permitAll())

                                // Security Headers
                                .headers(headers -> headers
                                                .frameOptions(frameOptions -> frameOptions.sameOrigin())
                                                .xssProtection(Customizer.withDefaults())
                                                .contentSecurityPolicy(csp -> csp
                                                                .policyDirectives(
                                                                                "default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval'")))

                // Optional: Add JWT filter if using JWT
                // .addFilterBefore(jwtAuthenticationFilter,
                // UsernamePasswordAuthenticationFilter.class)
                ;

                return http.build();
        }

        @Bean
        public PasswordEncoder passwordEncoder() {
                // BCrypt with 12 rounds of hashing for stronger password encryption
                return new BCryptPasswordEncoder(12);
        }

        @Bean
        public AuthenticationManager authenticationManager(
                        AuthenticationConfiguration authenticationConfiguration) throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        public CorsConfigurationSource corsConfigurationSource() {
                CorsConfiguration configuration = new CorsConfiguration();
                configuration.setAllowedOrigins(List.of(
                                "http://localhost:4200", // Angular Frontend
                                "http://localhost:3000", // React Frontend
                                "http://localhost:8080", // Backend
                                "http://localhost:8081" // Additional services
                ));
                configuration.setAllowedMethods(Arrays.asList(
                                "GET", "POST", "PUT", "PATCH",
                                "DELETE", "OPTIONS", "HEAD"));
                configuration.setAllowCredentials(true);
                configuration.setAllowedHeaders(Arrays.asList(
                                "Authorization",
                                "Content-Type",
                                "X-Requested-With",
                                "Accept",
                                "Origin"));
                configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                source.registerCorsConfiguration("/**", configuration);
                return source;
        }

        // Optional: If you want to add a CORS filter
        @Bean
        public CorsFilter corsFilter() {
                UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowCredentials(true);
                config.addAllowedOrigin("http://localhost:4200");
                config.addAllowedHeader("*");
                config.addAllowedMethod("*");
                source.registerCorsConfiguration("/**", config);
                return new CorsFilter(source);
        }
}