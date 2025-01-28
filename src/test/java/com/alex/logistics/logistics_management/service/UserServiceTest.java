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

    private User inputUser;
    private User updatedUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        service = new UserService(repository,encoder);

        // initialization test data
        Long id = 1L;
        String name = "Josh";
        String email = "josh@gmail.com";

        String password = "12345";
        String encodedPassword = "encodedPassword";

        Set<Role> inputRoles = new HashSet<>();
        Set<Role> updatedRoles = new HashSet<>(List.of(Role.USER));

        inputUser = new User();
        inputUser.setUsername(name);
        inputUser.setEmail(email);
        inputUser.setPassword(password);
        inputUser.setRoles(inputRoles);

        updatedUser = new User();
        updatedUser.setId(id);
        updatedUser.setEmail(email);
        updatedUser.setUsername(name);
        updatedUser.setPassword(encodedPassword);
        updatedUser.setRoles(updatedRoles);
    }

    @Test
    public void testRegisterUser() {
        // Arrange
        when(encoder.encode(inputUser.getPassword())).thenReturn(updatedUser.getPassword());
        when(repository.save(any(User.class))).thenReturn(updatedUser);

        // Act
        User result = service.registerUser(inputUser);

        // Assert
        assertNotNull(result);
        assertEquals(updatedUser.getId(), result.getId());
        assertEquals(updatedUser.getEmail(), result.getEmail());
        assertEquals(updatedUser.getUsername(), result.getUsername());
        assertEquals(updatedUser.getPassword(), result.getPassword());
        assertEquals(updatedUser.getRoles(), result.getRoles());

        verify(encoder, times(1)).encode(inputUser.getPassword());
        verify(repository, times(1)).save(any(User.class));
    }

    @Test
    public void testFindById() {
        // Arrange
        when(repository.findById(updatedUser.getId())).thenReturn(Optional.of(updatedUser));

        // Act
        Optional<User> result = service.findById(updatedUser.getId());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(updatedUser.getId(), result.get().getId());
        assertEquals(updatedUser.getEmail(), result.get().getEmail());
        assertEquals(updatedUser.getUsername(), result.get().getUsername());
        assertEquals(updatedUser.getPassword(), result.get().getPassword());

        verify(repository, times(1)).findById(updatedUser.getId());
    }

    @Test
    public void testFindByEmail() {
        // Arrange
        when(repository.findByEmail(inputUser.getEmail())).thenReturn(Optional.of(updatedUser));

        // Act
        Optional<User> result = service.findByEmail(inputUser.getEmail());

        // Assert
        assertTrue(result.isPresent());
        assertEquals(updatedUser.getId(), result.get().getId());
        assertEquals(updatedUser.getEmail(), result.get().getEmail());
        assertEquals(updatedUser.getUsername(), result.get().getUsername());
        assertEquals(updatedUser.getPassword(), result.get().getPassword());

        verify(repository, times(1)).findByEmail(updatedUser.getEmail());
    }


    @Test
    public void testDeleteUser() {
        // Arrange
        doNothing().when(repository).deleteById(updatedUser.getId());

        // Act
        service.deleteUser(updatedUser.getId());

        // Assert
        verify(repository, times(1)).deleteById(updatedUser.getId());
    }

    @TestConfiguration
    static class TestConfig {
        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }
    }
}

