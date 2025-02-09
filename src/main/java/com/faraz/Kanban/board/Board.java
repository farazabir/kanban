package com.faraz.Kanban.board;
import com.faraz.Kanban.project.Project;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Board")
public class Board {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private  Long id;
  private String name;

  @ManyToOne
  @JoinColumn(name = "project_id",nullable = false)
  private Project project;

}
