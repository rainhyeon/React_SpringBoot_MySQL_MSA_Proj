package com.example.userservice.repository;

import com.example.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    // username으로 사용자 찾기 (로그인 시 사용)
    Optional<User> findByUsername(String username);
}
