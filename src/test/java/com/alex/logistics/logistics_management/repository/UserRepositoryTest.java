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
class UserRepositoryTest {

    @Autowired
    private UserRepository repository;

    private User user;

    @BeforeEach
    public void setUp(){
        String name = "Josh";
        String password = "12345";
        String email = "hello@gmail.com";
        Set<Role> roles = new HashSet<>(List.of(Role.USER));

        // Arrange
        user = new User();
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(password);
        user.setRoles(roles);
    }

    @BeforeEach
    public void deleteData(){
        repository.deleteAll();
    }

    @Test
    public void testSaveUser() {
        // Act
        User savedUser = repository.save(user);

        // Assert
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(savedUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(savedUser.getRoles()).isEqualTo(user.getRoles());

        assertThat(repository.findById(savedUser.getId()).isPresent()).isEqualTo(true);
        assertThat(repository.findById(savedUser.getId()).get()).isEqualTo(savedUser);
    }

    @Test
    public void testFindUserById() {
        //Arrange
        User savedUser = repository.save(user);

        // Act
        User foundUser = repository.findById(savedUser.getId()).orElse(null);

        // Assert
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(foundUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(foundUser.getRoles()).isEqualTo(user.getRoles());
    }

    @Test
    public void testFindUserByUsername() {
        //Arrange
        User savedUser = repository.save(user);

        // Act
        User foundUser = repository.findByUsername(savedUser.getUsername()).orElse(null);

        // Assert
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(foundUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(foundUser.getRoles()).isEqualTo(user.getRoles());
    }

    @Test
    public void testFindUserByEmail() {
        //Arrange
        User savedUser = repository.save(user);

        // Act
        User foundUser = repository.findByEmail(savedUser.getEmail()).orElse(null);

        // Assert
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isNotNull();
        assertThat(foundUser.getUsername()).isEqualTo(user.getUsername());
        assertThat(foundUser.getPassword()).isEqualTo(user.getPassword());
        assertThat(foundUser.getRoles()).isEqualTo(user.getRoles());
    }


    @Test
    public void testDeleteUser() {
        //Arrange
        User savedUser = repository.save(user);

        // Act
        repository.deleteById(savedUser.getId());
        User foundUser = repository.findById(savedUser.getId()).orElse(null);

        // Assert
        assertThat(foundUser).isNull();
    }

    @Test
    public void testUpdateUser() {
        //Arrange
        String updatedPassword = "qwerty";
        User savedUser = repository.save(user);

        // Act
        savedUser.setPassword(updatedPassword);
        User updatedUser = repository.save(savedUser);

        // Assert
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getPassword()).isEqualTo(updatedPassword);
    }
}

