package com.example.userservice.service;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // 회원가입
    public User registerUser(User user) {
        Optional<User> existing = userRepository.findByUsername(user.getUsername());
        if (existing.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }
        return userRepository.save(user);
    }

    // 로그인
    public boolean loginUser(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.map(u -> u.getPassword().equals(password))
                   .orElse(false);
    }
}
