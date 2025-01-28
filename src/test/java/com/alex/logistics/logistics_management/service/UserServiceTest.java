package com.alex.logistics.logistics_management.service;

import com.alex.logistics.logistics_management.model.Role;
import com.alex.logistics.logistics_management.model.User;
import com.alex.logistics.logistics_management.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@Import({UserServiceTest.TestConfig.class})
public class UserServiceTest {
    @Mock
    private UserRepository repository;

    @Mock
    private PasswordEncoder encoder;

    private UserService service;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Инициализация моков
        service = new UserService(repository,encoder);// Передача мока в сервис

        // Инициализация тестовых данных
        String name = "Josh";
        String password = "12345";
        Set<Role> roles = new HashSet<>();

        // Arrange
        user = new User();
        user.setUsername(name);
        user.setPassword(password);
        user.setRoles(roles);
    }

    @Test
    public void testRegisterUser() {
        // Arrange
        String passwordEncoded = "54321";
        when(encoder.encode(user.getPassword())).thenReturn(passwordEncoded);

        User newUser = new User();
        newUser.setId(1L);
        newUser.setUsername(user.getUsername());
        newUser.setPassword(passwordEncoded);
        newUser.setRoles(new HashSet<>(List.of(Role.USER)));

        when(repository.save(any(User.class))).thenReturn(newUser);

        // Act
        User result = service.registerUser(user);

        // Assert
        assertNotNull(result);
        assertEquals(newUser.getId(), result.getId());
        assertEquals(newUser.getUsername(), result.getUsername());
        assertEquals(newUser.getPassword(), result.getPassword());
        assertEquals(newUser.getRoles(), result.getRoles());

        verify(encoder, times(1)).encode(user.getPassword());
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    public void testFindById() {
        // Arrange
        User newUser = new User();
        newUser.setId(1L);
        newUser.setUsername(user.getUsername());
        newUser.setPassword("54321");
        newUser.setRoles(new HashSet<>(List.of(Role.USER)));

        when(repository.findById(1L)).thenReturn(Optional.of(newUser));

        // Act
        Optional<User> result = service.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(newUser.getId(), result.get().getId());
        assertEquals(newUser.getUsername(), result.get().getUsername());
        assertEquals(newUser.getPassword(), result.get().getPassword());

        verify(repository, times(1)).findById(1L);
    }


    @Test
    public void testDeleteUser() {
        // Arrange
        doNothing().when(repository).deleteById(1L);

        // Act
        service.deleteUser(1L);

        // Assert
        verify(repository, times(1)).deleteById(1L);
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
}

