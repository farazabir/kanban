package com.faraz.Kanban.board;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository  extends JpaRepository<Board,Long> {
    List<Board> findByProjectId(Long projectId);
}
