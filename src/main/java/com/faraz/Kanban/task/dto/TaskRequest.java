package com.faraz.Kanban.task.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TaskRequest {
    private String title;
    private String description;
}
