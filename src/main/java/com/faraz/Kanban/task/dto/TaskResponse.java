package com.faraz.Kanban.task.dto;

import com.faraz.Kanban.user.User;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TaskResponse {
    private Long id;
    private String title;
    private String description;
    private Long boardId;
    private Set<User> assignedUsers;
}
