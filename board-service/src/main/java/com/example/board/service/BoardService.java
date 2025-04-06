package com.example.board.service;

import com.example.board.dto.BoardRequest;
import com.example.board.model.Board;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${user-service.url}")
    private String userServiceUrl;
    
    public void save(BoardRequest request) {
        String userId = request.getUserId();

        Boolean userExists = restTemplate.getForObject(
            userServiceUrl + "/api/users/" + userId + "/exists",
            Boolean.class
        );

        if (Boolean.FALSE.equals(userExists)) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }

        Board board = new Board(request.getTitle(), request.getContent(), userId);
        boardRepository.save(board);
    }
}
