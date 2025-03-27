package com.example.board.service;

import com.example.board.dto.BoardRequest;
import com.example.board.model.Board;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final RestTemplate restTemplate = new RestTemplate(); // 🔥 생성

    public void save(BoardRequest request) {
        String userId = request.getUserId();

        // 🔍 게시글 저장 전에 user 존재 확인
        Boolean userExists = restTemplate.getForObject(
            "http://user:8081/api/users/" + userId + "/exists",
            Boolean.class
        );

        if (Boolean.FALSE.equals(userExists)) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }

        // ✅ 유효한 사용자면 게시글 저장
        Board board = new Board(request.getTitle(), request.getContent(), userId);
        boardRepository.save(board);
    }
}
