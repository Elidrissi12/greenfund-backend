package com.greenfund.greenfund_backend.controller;

import com.greenfund.greenfund_backend.model.dto.request.CreateProjectRequest;
import com.greenfund.greenfund_backend.model.dto.request.UpdateProjectRequest;
import com.greenfund.greenfund_backend.model.dto.response.ProjectResponse;
import com.greenfund.greenfund_backend.model.enums.ProjectStatus;
import com.greenfund.greenfund_backend.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
@CrossOrigin(origins = "*")
public class ProjectController {
    @Autowired
    private ProjectService projectService;

    @PostMapping
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody CreateProjectRequest request) {
        ProjectResponse response = projectService.createProject(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponse>> getAllProjects(
            @RequestParam(required = false) ProjectStatus status) {
        List<ProjectResponse> projects = status != null
                ? projectService.getProjectsByStatus(status)
                : projectService.getAllProjects();
        return ResponseEntity.ok(projects);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponse> getProjectById(@PathVariable Long id) {
        ProjectResponse response = projectService.getProjectById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-projects")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<List<ProjectResponse>> getMyProjects() {
        // Récupérer l'ID de l'utilisateur depuis le contexte de sécurité
        org.springframework.security.core.Authentication authentication = 
            org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
        com.greenfund.greenfund_backend.security.UserPrincipal userPrincipal = 
            (com.greenfund.greenfund_backend.security.UserPrincipal) authentication.getPrincipal();
        
        List<ProjectResponse> projects = projectService.getProjectsByOwner(userPrincipal.getId());
        return ResponseEntity.ok(projects);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('OWNER')")
    public ResponseEntity<ProjectResponse> updateProject(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProjectRequest request) {
        ProjectResponse response = projectService.updateProject(id, request);
        return ResponseEntity.ok(response);
    }
}

