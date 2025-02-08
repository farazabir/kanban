package com.faraz.Kanban.project;

import com.faraz.Kanban.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project,Long> {
      List<Project> findByOwner(User owner);
}
