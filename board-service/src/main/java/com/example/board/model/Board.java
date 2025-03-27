package com.example.board.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "board")
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Lob
    private String content;

    private String userId;

    // ✅ 추가 생성자
    public Board(String title, String content, String userId) {
        this.title = title;
        this.content = content;
        this.userId = userId;
    }
}
