package com.alex.logistics.logistics_management.controller;

import com.alex.logistics.logistics_management.config.SecurityConfig;
import com.alex.logistics.logistics_management.dto.RegistrationDto;
import com.alex.logistics.logistics_management.model.Role;
import com.alex.logistics.logistics_management.model.User;
import com.alex.logistics.logistics_management.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(AuthController.class)
@Import({NetworkConfigTest.class, AuthControllerTest.TestConfig.class})
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testRegistration() throws Exception {
        // Arrange
        String name = "testUser";
        String inputPassword = "password123";
        String encodedPassword = "encodedPassword";

        User inputUser = new User();
        inputUser.setUsername(name);
        inputUser.setPassword(inputPassword);

        User createdUser = new User();
        createdUser.setId(1L);
        createdUser.setUsername(name);
        createdUser.setPassword(encodedPassword);
        createdUser.setRoles(Set.of(Role.USER));


        when(userService.registerUser(any(User.class))).thenReturn(createdUser);

        // Act
        // Assert
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(inputUser)))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(createdUser)));

        verify(userService).registerUser(any(User.class));
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public UserService userService() {
            return mock(UserService.class);
        }
    }
}

