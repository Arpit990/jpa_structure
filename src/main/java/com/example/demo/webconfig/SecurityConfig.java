package com.example.demo.webconfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /*@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/authentication/login", "/css/**", "/js/**", "/assets/**").permitAll() // Allow access to log in and static resources
                        .anyRequest()
                        .authenticated() // Protect all other endpoints
                )
                .formLogin(form -> form
                        .loginPage("/authentication/login") // Custom login page
                        .loginProcessingUrl("/authentication/login") // URL for form submission
                        .defaultSuccessUrl("/dashboard", true) // Redirect after successful login
                        .failureUrl("/authentication/login?error=true") // Redirect after failed login
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/authentication/login?logout=true")
                        .permitAll()
                );
        return http.build();
    }*/

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .anyRequest().permitAll() // Allow all requests
                )
                .csrf(csrf -> csrf.disable()); // Disable CSRF protection (optional, for testing purposes)

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails user = User.builder()
                .username("admin") // Default username
                .password(passwordEncoder.encode("password")) // Default password
                .roles("USER") // Role
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}