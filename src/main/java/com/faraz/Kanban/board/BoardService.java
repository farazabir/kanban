package com.faraz.Kanban.board;

import com.faraz.Kanban.board.dto.BoardRequest;
import com.faraz.Kanban.project.Project;
import com.faraz.Kanban.project.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardService {
    @Autowired
    private final BoardRepository boardRepository;

    @Autowired
    private final ProjectRepository projectRepository;


    public BoardService(BoardRepository boardRepository, ProjectRepository projectRepository) {
        this.boardRepository = boardRepository;
        this.projectRepository = projectRepository;
    }

    public Board createBoard(Long projectId, BoardRequest boardRequest){
        Project project = projectRepository.findById(projectId).orElseThrow(()-> new RuntimeException("Project not found"));
      Board board = Board.builder().name(boardRequest.getName()).project(project).build();
       return boardRepository.save(board);
    }


    public List<Board> getBoardByProjectId(Long projectId){
        return boardRepository.findByProjectId(projectId);
    }
}
