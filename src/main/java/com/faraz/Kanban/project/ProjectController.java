package com.faraz.Kanban.project;

import com.faraz.Kanban.project.dto.ProjectRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {
    private final ProjectService projectService;


    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
     public Project createProject(@RequestBody ProjectRequest projectRequest){
        return ResponseEntity.ok(projectService.createProject(projectRequest)).getBody();
     }


}
