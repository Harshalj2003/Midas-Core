package com.jpmc.midascore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/h2/**").permitAll()       // allow h2 console
                        .requestMatchers("/balance/**").permitAll()  // ✅ allow balance API
                        .anyRequest().authenticated()                  // others still require login
                )
                .csrf(csrf -> csrf.ignoringRequestMatchers("/h2/**", "/balance/**"))
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()) // ✅ new style
                );

        return http.build();
    }
}
