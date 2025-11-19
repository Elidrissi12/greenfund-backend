package com.greenfund.greenfund_backend.service;

import com.greenfund.greenfund_backend.model.dto.request.CreateProjectRequest;
import com.greenfund.greenfund_backend.model.dto.request.UpdateProjectRequest;
import com.greenfund.greenfund_backend.model.dto.response.ProjectResponse;
import com.greenfund.greenfund_backend.model.entity.Project;
import com.greenfund.greenfund_backend.model.entity.User;
import com.greenfund.greenfund_backend.model.enums.EnergyType;
import com.greenfund.greenfund_backend.model.enums.ProjectStatus;
import com.greenfund.greenfund_backend.repository.ProjectRepository;
import com.greenfund.greenfund_backend.repository.UserRepository;
import com.greenfund.greenfund_backend.repository.specification.ProjectSpecifications;
import com.greenfund.greenfund_backend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {
    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ProjectResponse createProject(CreateProjectRequest request) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User owner = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = new Project();
        project.setTitle(request.getTitle());
        project.setCity(request.getCity());
        project.setEnergyType(request.getEnergyType());
        project.setDescription(request.getDescription());
        project.setTargetAmount(request.getTargetAmount());
        project.setRaisedAmount(java.math.BigDecimal.ZERO);
        project.setStatus(ProjectStatus.PENDING);
        project.setOwner(owner);

        project = projectRepository.save(project);
        return convertToResponse(project);
    }

    public List<ProjectResponse> getAllProjects() {
        return projectRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ProjectResponse> getProjectsByStatus(ProjectStatus status) {
        return projectRepository.findByStatus(status).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<ProjectResponse> getProjectsByOwner(Long ownerId) {
        return projectRepository.findByOwnerId(ownerId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public ProjectResponse getProjectById(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        return convertToResponse(project);
    }

    public List<ProjectResponse> searchProjects(String keyword,
                                                String city,
                                                EnergyType energyType,
                                                ProjectStatus status,
                                                BigDecimal minTargetAmount,
                                                BigDecimal maxTargetAmount,
                                                BigDecimal minRaisedAmount,
                                                BigDecimal maxRaisedAmount) {
        Specification<Project> specification = ProjectSpecifications.build(
                keyword,
                city,
                energyType,
                status,
                minTargetAmount,
                maxTargetAmount,
                minRaisedAmount,
                maxRaisedAmount
        );

        return projectRepository.findAll(specification, Sort.by(Sort.Direction.DESC, "createdAt"))
                .stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    @Transactional
    public ProjectResponse updateProject(Long id, UpdateProjectRequest request) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        
        // Vérifier que l'utilisateur est le propriétaire du projet
        if (!project.getOwner().getId().equals(userPrincipal.getId())) {
            throw new RuntimeException("You are not authorized to update this project");
        }
        
        // Ne pas permettre la modification si le projet a déjà des investissements
        if (project.getRaisedAmount().compareTo(java.math.BigDecimal.ZERO) > 0) {
            throw new RuntimeException("Cannot update project that has already received investments");
        }
        
        project.setTitle(request.getTitle());
        project.setCity(request.getCity());
        project.setEnergyType(request.getEnergyType());
        project.setDescription(request.getDescription());
        project.setTargetAmount(request.getTargetAmount());
        
        project = projectRepository.save(project);
        return convertToResponse(project);
    }

    @Transactional
    public ProjectResponse updateProjectStatus(Long id, ProjectStatus status) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found"));
        project.setStatus(status);
        project = projectRepository.save(project);
        return convertToResponse(project);
    }

    private ProjectResponse convertToResponse(Project project) {
        ProjectResponse response = new ProjectResponse();
        response.setId(project.getId());
        response.setTitle(project.getTitle());
        response.setCity(project.getCity());
        response.setEnergyType(project.getEnergyType());
        response.setDescription(project.getDescription());
        response.setTargetAmount(project.getTargetAmount());
        response.setRaisedAmount(project.getRaisedAmount());
        response.setProgress(project.getProgress());
        response.setStatus(project.getStatus());
        response.setOwnerId(project.getOwner().getId());
        response.setOwnerName(project.getOwner().getName());
        response.setCreatedAt(project.getCreatedAt());
        response.setUpdatedAt(project.getUpdatedAt());
        return response;
    }
}

