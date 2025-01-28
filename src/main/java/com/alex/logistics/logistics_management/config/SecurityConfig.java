package com.alex.logistics.logistics_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Profile("default")
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Отключаем CSRF защиту, если она не нужна
                .authorizeHttpRequests(auth -> auth
                        // Разрешаем доступ к API /shipments
                        .requestMatchers("/api/shipments/**").permitAll()//.authenticated()
                        // Все остальные запросы требуют аутентификации
                        .anyRequest().authenticated()
                )
//               .formLogin(form -> form
//                        .loginPage("/login") // Страница логина
//                        .permitAll() // Разрешаем доступ к странице логина всем
//               )
                .logout(LogoutConfigurer::permitAll // Разрешаем доступ к логауту всем
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}