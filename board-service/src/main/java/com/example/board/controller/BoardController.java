package com.example.board.controller;

import com.example.board.dto.BoardRequest;
import com.example.board.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/board")
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @PostMapping
    public ResponseEntity<String> create(@RequestBody BoardRequest request) {
        boardService.save(request);
        return ResponseEntity.ok("게시글 작성 완료");
    }
}

