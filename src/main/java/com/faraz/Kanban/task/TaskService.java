package com.faraz.Kanban.task;

import com.faraz.Kanban.board.Board;
import com.faraz.Kanban.board.BoardRepository;
import com.faraz.Kanban.task.dto.TaskRequest;
import com.faraz.Kanban.task.dto.TaskResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
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

    public List<TaskResponse> getTasks(Long boardId) {
        return taskRepository.findByBoardId(boardId)
                .stream()
                .map(task -> TaskResponse.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .boardId(task.getBoard().getId())
                        .assignedUsers(task.getAssignedUsers())
                        .build())
                .collect(Collectors.toList());
    }



    public Task moveTask(Long taskId, Long newBoardId) {
          taskRepository.moveTask(taskId,newBoardId);
        return taskRepository.findById(taskId).orElseThrow(()-> new RuntimeException("Task not found"));
    }
}
