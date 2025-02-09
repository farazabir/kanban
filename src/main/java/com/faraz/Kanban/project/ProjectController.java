package com.faraz.Kanban.project;

import com.faraz.Kanban.project.dto.ProjectRequest;
import com.faraz.Kanban.project.dto.ProjectResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {
    private final ProjectService projectService;


    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/create")
     public ResponseEntity<?> createProject(@RequestBody ProjectRequest projectRequest){
        try{
           Project project =  projectService.createProject(projectRequest);
           return ResponseEntity.ok(project);
        }catch (RuntimeException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
     }

     @GetMapping("/get")
     public ResponseEntity<Object> getProject(){
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ResponseEntity.ok(projectService.getProjectByUser(email));
     }

    @PostMapping("/{projectId}/invite")
    public ResponseEntity<?> inviteUser(@PathVariable Long projectId, @RequestParam String email) {
        try {
            String message = projectService.inviteUserToProject(projectId, email);
            return ResponseEntity.ok().body("{\"message\": \"" + message + "\"}");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }

    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long projectId){
        System.out.println("id"+projectId);
       return ResponseEntity.ok(projectService.getProjectById(projectId));
    }

}
