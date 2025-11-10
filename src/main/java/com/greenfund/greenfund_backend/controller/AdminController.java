package com.greenfund.greenfund_backend.controller;

import com.greenfund.greenfund_backend.model.dto.response.InvestmentResponse;
import com.greenfund.greenfund_backend.model.dto.response.ProjectResponse;
import com.greenfund.greenfund_backend.model.dto.response.UserResponse;
import com.greenfund.greenfund_backend.model.entity.User;
import com.greenfund.greenfund_backend.model.enums.ProjectStatus;
import com.greenfund.greenfund_backend.repository.UserRepository;
import com.greenfund.greenfund_backend.service.InvestmentService;
import com.greenfund.greenfund_backend.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {
    @Autowired
    private ProjectService projectService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InvestmentService investmentService;

    @GetMapping("/projects/pending")
    public ResponseEntity<List<ProjectResponse>> getPendingProjects() {
        List<ProjectResponse> projects = projectService.getProjectsByStatus(ProjectStatus.PENDING);
        return ResponseEntity.ok(projects);
    }

    @PutMapping("/projects/{id}/validate")
    public ResponseEntity<ProjectResponse> validateProject(
            @PathVariable Long id,
            @RequestParam ProjectStatus status) {
        ProjectResponse response = projectService.updateProjectStatus(id, status);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        List<UserResponse> users = userRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PutMapping("/users/{id}/status")
    public ResponseEntity<UserResponse> updateUserStatus(
            @PathVariable Long id,
            @RequestParam Boolean active) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setActive(active);
        user = userRepository.save(user);
        return ResponseEntity.ok(convertToResponse(user));
    }

    @GetMapping("/transactions")
    public ResponseEntity<List<InvestmentResponse>> getAllTransactions() {
        List<InvestmentResponse> transactions = investmentService.getAllInvestments();
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/stats")
    public ResponseEntity<java.util.Map<String, Object>> getStats() {
        java.util.Map<String, Object> stats = new java.util.HashMap<>();
        stats.put("totalProjects", projectService.getAllProjects().size());
        stats.put("pendingProjects", projectService.getProjectsByStatus(ProjectStatus.PENDING).size());
        stats.put("activeProjects", projectService.getProjectsByStatus(ProjectStatus.ACTIVE).size());
        stats.put("completedProjects", projectService.getProjectsByStatus(ProjectStatus.COMPLETED).size());
        stats.put("totalUsers", userRepository.count());
        return ResponseEntity.ok(stats);
    }

    private UserResponse convertToResponse(User user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setRole(user.getRole());
        response.setActive(user.getActive());
        response.setCreatedAt(user.getCreatedAt());
        return response;
    }
}

