package com.example.board.service;

import com.example.board.dto.BoardRequest;
import com.example.board.model.Board;
import com.example.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardRequest request) {
        Board board = Board.builder()
                .title(request.getTitle())
                .content(request.getContent())
                .userId(request.getUserId())
                .build();

        boardRepository.save(board);
    }
}

