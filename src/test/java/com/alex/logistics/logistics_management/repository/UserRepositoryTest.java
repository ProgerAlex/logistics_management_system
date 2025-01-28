package com.alex.logistics.logistics_management.repository;

import com.alex.logistics.logistics_management.model.Role;
import com.alex.logistics.logistics_management.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    @BeforeEach
    public void deleteData(){
        repository.deleteAll();
    }

    @Test
    public void testSaveUser() {

        String name = "Josh";
        String password = "12345";
        Set<Role> roles = new HashSet<>(List.of(Role.USER));

        // Arrange
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        user.setRoles(roles);

        // Act
        User savedUser = repository.save(user);

        // Assert
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo(name);
        assertThat(savedUser.getPassword()).isEqualTo(password);
        assertThat(savedUser.getRoles()).isEqualTo(roles);

        assertThat(repository.findById(savedUser.getId()).isPresent()).isEqualTo(true);
        assertThat(repository.findById(savedUser.getId()).get()).isEqualTo(savedUser);
    }

    @Test
    public void testFindUserById() {
        String name = "Josh";
        String password = "12345";
        Set<Role> roles = new HashSet<>(List.of(Role.USER));

        // Arrange
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        user.setRoles(roles);

        // Act
        User savedUser = repository.save(user);

        // Act
        User foundUser = repository.findById(savedUser.getId()).orElse(null);

        // Assert
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo(name);
        assertThat(foundUser.getPassword()).isEqualTo(password);
        assertThat(foundUser.getRoles()).isEqualTo(roles);
    }

    @Test
    public void testFindUserByUsername() {
        String name = "Josh";
        String password = "12345";
        Set<Role> roles = new HashSet<>(List.of(Role.USER));

        // Arrange
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        user.setRoles(roles);

        // Act
        User savedUser = repository.save(user);

        // Act
        User foundUser = repository.findByUsername(savedUser.getUsername()).orElse(null);

        // Assert
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo(name);
        assertThat(foundUser.getPassword()).isEqualTo(password);
        assertThat(foundUser.getRoles()).isEqualTo(roles);
    }

    @Test
    public void testDeleteUser() {
        String name = "Josh";
        String password = "12345";
        Set<Role> roles = new HashSet<>(List.of(Role.USER));

        // Arrange
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        user.setRoles(roles);
        User savedUser = repository.save(user);

        // Act
        repository.deleteById(savedUser.getId());
        User foundUser = repository.findById(savedUser.getId()).orElse(null);

        // Assert
        assertThat(foundUser).isNull();
    }

    @Test
    public void testUpdateUser() {
        String name = "Josh";
        String password = "12345";
        Set<Role> roles = new HashSet<>(List.of(Role.USER));

        String updatedPassword = "qwerty";

        // Arrange
        User user = new User();
        user.setUsername(name);
        user.setPassword(password);
        user.setRoles(roles);
        User savedUser = repository.save(user);

        // Act
        savedUser.setPassword(updatedPassword);
        User updatedUser = repository.save(savedUser);

        // Assert
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getPassword()).isEqualTo(updatedPassword);
    }
}

