package com.faraz.Kanban.task;

import com.faraz.Kanban.task.dto.TaskRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequestMapping("api/v1/task")
@RestController
public class TaskController {
    @Autowired
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<Task> createTask(
            @PathVariable Long boardId,
            @RequestBody TaskRequest taskRequest
            ){
        return  ResponseEntity.ok(taskService.createTask(boardId,taskRequest));
    }


}
