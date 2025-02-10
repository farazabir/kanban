package com.faraz.Kanban.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task,Long> {

    List<Task> findByBoardId(Long boardId);

    @Modifying
    @Query("UPDATE Task t SET t.board.id = :newBoardId WHERE t.id = :taskId")
    void moveTask(Long taskId, Long newBoardId);
}
