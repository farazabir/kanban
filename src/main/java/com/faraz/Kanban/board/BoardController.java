package com.faraz.Kanban.board;

import com.faraz.Kanban.board.dto.BoardRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(path="api/v1/board")
@RestController
public class BoardController {
    @Autowired
    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @PostMapping("/{projectId}")
    public ResponseEntity<Board> createBoard(@PathVariable Long projectId, @RequestBody BoardRequest boardRequest){
        return ResponseEntity.ok(boardService.createBoard(projectId,boardRequest));
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<Object> getBoardByProjectId(@PathVariable Long projectId){
        return  ResponseEntity.ok(boardService.getBoardByProjectId(projectId));
    }

}
