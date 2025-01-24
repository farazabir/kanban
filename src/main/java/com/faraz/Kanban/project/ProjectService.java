package com.faraz.Kanban.project;

import com.faraz.Kanban.project.dto.ProjectRequest;
import com.faraz.Kanban.user.User;
import com.faraz.Kanban.user.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {
private final ProjectRepository projectRepository;
private final UserRepository userRepository;


    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public Project createProject(ProjectRequest projectRequest){
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(email);
       Project project =  Project.builder().owner(user).name(projectRequest.getName()).build();

        return projectRepository.save(project);
    }



}
