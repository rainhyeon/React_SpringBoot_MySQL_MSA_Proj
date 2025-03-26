package com.example.userservice.model;

import jakarta.persistence.*;

@Entity
@Table(name = "users")  // 'users' 테이블에 매핑
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;               // 기본 키 (자동 증가)

    @Column(nullable = false, unique = true, length = 50)
    private String username;       // 사용자 이름 (로그인 ID로 사용)

    @Column(nullable = false)
    private String password;       // 비밀번호 (해시하지 않은 평문 예시; 실제로는 해싱 필요)

    @Column(nullable = true, unique = true, length = 100)
    private String email;          // 이메일 (선택 사항)

    // 기본 생성자 (JPA 사용을 위해 필요)
    public User() {}

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    // Getter & Setter (생략 가능: Lombok 등으로 대체 가능)
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}
