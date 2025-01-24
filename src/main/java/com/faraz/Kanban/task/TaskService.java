package com.faraz.Kanban.task;

import com.faraz.Kanban.board.Board;
import com.faraz.Kanban.board.BoardRepository;
import com.faraz.Kanban.task.dto.TaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

    @Autowired
    private final TaskRepository taskRepository;
    @Autowired
    private final BoardRepository boardRepository;


    public TaskService(TaskRepository taskRepository, BoardRepository boardRepository) {
        this.taskRepository = taskRepository;
        this.boardRepository = boardRepository;
    }

    public Task createTask(Long boardId, TaskRequest taskRequest){
        Board board = boardRepository.findById(boardId).orElseThrow(()-> new RuntimeException("Board not found"));

        Task task = Task.builder().title(taskRequest.getTitle()).description(taskRequest.getDescription()).board(board).build();

        return taskRepository.save(task);

    }

}
