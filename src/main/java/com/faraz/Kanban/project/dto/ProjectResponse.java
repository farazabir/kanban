package com.faraz.Kanban.project.dto;

import com.faraz.Kanban.board.dto.BoardResponse;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProjectResponse {
    private  Long id;
    private String name;
    private LocalDateTime createAt;
    private List<BoardResponse> boards;

}
