package com.example.userservice.service;

import com.example.userservice.model.User;
import com.example.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    // 생성자를 통한 의존성 주입 (Spring이 UserRepository 빈을 주입)
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /** 회원가입: 새로운 사용자 저장 */
    public User registerUser(User user) {
        // username 중복 체크
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다.");
        }
        // 새 사용자 저장 (비밀번호는 이미 user 객체에 설정되어 있다고 가정)
        return userRepository.save(user);
    }

    /** 로그인 확인: 사용자 인증 */
    public boolean loginUser(String username, String password) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            return false;  // 해당 username 없음 -> 로그인 실패
        }
        User user = optionalUser.get();
        // 입력된 비밀번호와 저장된 비밀번호 비교
        return user.getPassword().equals(password);
    }
}
