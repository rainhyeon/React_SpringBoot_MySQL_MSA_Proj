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
    
    @PostMapping("/write")
    public ResponseEntity<?> write(@RequestBody BoardRequest request) {
        boardService.save(request);
        return ResponseEntity.ok("글쓰기 성공");
    }
}

