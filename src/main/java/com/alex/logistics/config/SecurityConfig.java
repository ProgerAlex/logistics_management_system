package com.alex.logistics.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Отключаем CSRF защиту, если необходимо
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/shipments").permitAll() // Разрешаем доступ к /api/shipments
                        .anyRequest().authenticated() // Требуем аутентификацию для остальных запросов
                )
                .formLogin(form -> form
                        .loginPage("/login") // Настройка страницы входа, если нужно
                        .permitAll() // Разрешаем доступ к странице входа всем
                )
                .logout(LogoutConfigurer::permitAll); // Разрешаем доступ к логауту всем


        return http.build();
    }
}