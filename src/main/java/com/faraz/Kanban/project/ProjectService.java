package com.faraz.Kanban.project;

import com.faraz.Kanban.board.BoardRepository;
import com.faraz.Kanban.board.dto.BoardResponse;
import com.faraz.Kanban.email.EmailService;
import com.faraz.Kanban.project.dto.ProjectRequest;
import com.faraz.Kanban.project.dto.ProjectResponse;
import com.faraz.Kanban.stripe.SubscriptionPlan;
import com.faraz.Kanban.user.User;
import com.faraz.Kanban.user.UserRepository;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final BoardRepository boardRepository;
    private final Environment env;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository,
                          EmailService emailService, BoardRepository boardRepository, Environment env) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.emailService = emailService;
        this.boardRepository = boardRepository;
        this.env = env;
    }

    public Project createProject(ProjectRequest projectRequest) {
        String email = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User user = userRepository.findByEmail(email);
        SubscriptionPlan subscriptionPlan = user.getSubscriptionPlan();
        long userProjectCount = projectRepository.findByOwner(user).size();

        int maxProjects = getPlanLimit(subscriptionPlan, "project");
        if (maxProjects != -1 && userProjectCount >= maxProjects) {
            throw new RuntimeException("Project limit reached for your subscription plan.");
        }

        Project project = Project.builder()
                .owner(user)
                .name(projectRequest.getName())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        return projectRepository.save(project);
    }

    public List<Project> getProjectByUser(String email) {
        User user = userRepository.findByEmail(email);
        return projectRepository.findByOwnerOrMembersContaining(user, user);
    }

    public ProjectResponse getProjectById(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        List<BoardResponse> boards = boardRepository.findByProjectId(projectId)
                .stream()
                .map(board -> new BoardResponse(board.getId(), board.getName()))
                .collect(Collectors.toList());

        return ProjectResponse.builder()
                .id(project.getId())
                .name(project.getName())
                .createAt(project.getCreatedAt())
                .boards(boards)
                .build();
    }

    public String inviteUserToProject(Long projectId, String email) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new IllegalArgumentException("Project not found"));

        int maxMembers = getPlanLimit(project.getOwner().getSubscriptionPlan(), "members");
        if (maxMembers != -1 && project.getMembers().size() >= maxMembers) {
            throw new RuntimeException("Member limit reached for this project.");
        }

        if (project.getInvitedEmails().contains(email)) {
            throw new IllegalArgumentException("User already invited.");
        }

        String token = UUID.randomUUID().toString();
        String inviteLink = "http://localhost:3000/signup?email=" + email + "&projectId=" + projectId;

        project.getInvitedEmails().add(email);
        projectRepository.save(project);

        try {
            emailService.sendEmail(email, "Kanban Project Invitation",
                    "You've been invited to join the project: " + project.getName() +
                            "\nClick here to sign up: " + inviteLink);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send invitation email.");
        }

        return "Invitation sent successfully to " + email;
    }

    public void checkAndAddUserToProject(User user) {
        List<Project> projects = projectRepository.findAll();
        for (Project project : projects) {
            if (project.getInvitedEmails().contains(user.getEmail())) {
                project.getMembers().add(user);
                project.getInvitedEmails().remove(user.getEmail());
                projectRepository.save(project);
            }
        }
    }

    private int getPlanLimit(SubscriptionPlan plan, String type) {
        try {
            return Integer.parseInt(env.getProperty("stripe.plan." + plan.name().toLowerCase() + "." + type, "-1"));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
