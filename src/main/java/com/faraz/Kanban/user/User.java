package com.faraz.Kanban.user;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

 private String name;
 private String email;
 private String password;
 private boolean isActive;
 private LocalDateTime createdAt;
 private LocalDateTime updatedAt;


}
