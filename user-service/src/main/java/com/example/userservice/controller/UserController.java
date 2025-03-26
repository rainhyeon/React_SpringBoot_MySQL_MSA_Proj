package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.Collections;


@RestController
@RequestMapping("api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

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

}
