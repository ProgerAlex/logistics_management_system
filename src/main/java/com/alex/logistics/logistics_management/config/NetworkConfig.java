package com.alex.logistics.logistics_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class NetworkConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/shipments/**").permitAll()
                        .requestMatchers("/api/auth/register/**").permitAll()
                        //.anyRequest().authenticated()
                );
//                .formLogin(form -> form
//                        .loginPage("/api/auth/login")
//                        .permitAll()
//                )
//                .logout(LogoutConfigurer::permitAll);
        return http.build();
    }
}