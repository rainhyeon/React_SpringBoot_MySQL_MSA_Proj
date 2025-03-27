package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import com.example.userservice.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Collections;
import lombok.RequiredArgsConstructor; // ✅ 이거 반드시 필요



@RestController
@RequestMapping("api/users")
@RequiredArgsConstructor // ✅ 이거 반드시 필요!
public class UserController {

    private final UserService userService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User newUser) {
        try {
            User saved = userService.registerUser(newUser);
            saved.setPassword(null); // 비밀번호는 응답에서 제거
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        boolean success = userService.loginUser(
                loginRequest.getUsername(), loginRequest.getPassword());

        if (success) return ResponseEntity.ok("Login success!");
        else return ResponseEntity.status(401).body("Invalid credentials");
    }

    @GetMapping("/status")
    public ResponseEntity<?> loginStatus(HttpSession session) {
        Boolean loggedIn = session.getAttribute("user") != null;
        return ResponseEntity.ok(Collections.singletonMap("loggedIn", loggedIn));
    }

    // UserController.java
    @GetMapping("/{username}/exists")
    public ResponseEntity<Boolean> existsByUsername(@PathVariable String username) {
        boolean exists = userRepository.existsByUsername(username);
        return ResponseEntity.ok(exists);
    }



}
