package com.example.userservice.controller;

import com.example.userservice.model.User;
import com.example.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /** 회원가입 엔드포인트 */
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User newUser) {
        try {
            User saved = userService.registerUser(newUser);
            // 비밀번호 제외한 정보로 응답하거나, 전체 객체를 반환 (예제에선 객체 반환)
            saved.setPassword(null);  // 비밀번호는 응답에서 숨기기
            return ResponseEntity.ok(saved);
        } catch (IllegalArgumentException e) {
            // 이미 존재하는 사용자명 등 오류 시
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body(e.getMessage());
        }
    }

    /** 로그인 엔드포인트 */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        boolean success = userService.loginUser(
                loginRequest.getUsername(), loginRequest.getPassword());
        if (success) {
            // 로그인 성공 (간단히 메시지 반환; 실제로는 세션 관리나 JWT 토큰 발급)
            return ResponseEntity.ok("Login successful");
        } else {
            // 로그인 실패 - 401 Unauthorized 응답
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                                 .body("Invalid credentials");
        }
    }
}
