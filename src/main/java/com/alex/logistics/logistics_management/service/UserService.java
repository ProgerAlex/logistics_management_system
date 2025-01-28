package com.alex.logistics.logistics_management.service;

import com.alex.logistics.logistics_management.dto.RegistrationDto;
import com.alex.logistics.logistics_management.model.Role;
import com.alex.logistics.logistics_management.model.User;
import com.alex.logistics.logistics_management.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Optional<User> findById(Long id) {return userRepository.findById(id);}

    @Transactional
    public Optional<User> findByEmail(String email) {return userRepository.findByEmail(email);}

    @Transactional
    public void deleteUser(Long id) {userRepository.deleteById(id);}

    @Transactional
    public User registerUser(RegistrationDto userInfo) {
        User newUser = new User();
        newUser.setUsername(userInfo.getUsername());
        newUser.setEmail(userInfo.getEmail());
        newUser.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        newUser.setRoles(Set.of(Role.USER));
        return userRepository.save(newUser);
    }


}
