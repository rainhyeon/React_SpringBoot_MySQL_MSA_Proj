package com.example.userservice.service;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }
        return userRepository.save(user);
    }

    public boolean loginUser(String username, String password) {
        User found = userRepository.findByUsername(username);
        return found != null && found.getPassword().equals(password);
    }
}
