package com.greenfund.greenfund_backend.controller;

import com.greenfund.greenfund_backend.model.entity.Project;
import com.greenfund.greenfund_backend.repository.ProjectRepository;
import com.greenfund.greenfund_backend.security.UserPrincipal;
import com.greenfund.greenfund_backend.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
@CrossOrigin(origins = "*")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @Autowired
    private ProjectRepository projectRepository;

    @PostMapping("/projects/{projectId}/image")
    @PreAuthorize("hasRole('OWNER') or hasRole('ADMIN')")
    public ResponseEntity<Map<String, String>> uploadProjectImage(
            @PathVariable Long projectId,
            @RequestParam("file") MultipartFile file) {
        
        try {
            // Vérifier que le projet existe
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            
            // Vérifier que l'utilisateur est le propriétaire du projet (sauf ADMIN)
            UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
                    .getAuthentication().getPrincipal();
            
            boolean isAdmin = userPrincipal.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
            
            if (!isAdmin && !project.getOwner().getId().equals(userPrincipal.getId())) {
                throw new RuntimeException("You are not authorized to update this project");
            }

            // Supprimer l'ancienne image si elle existe
            if (project.getImageFilename() != null && !project.getImageFilename().isEmpty()) {
                try {
                    fileStorageService.deleteFile(project.getImageFilename());
                } catch (Exception e) {
                    // Logger l'erreur mais continuer
                    System.err.println("Error deleting old image: " + e.getMessage());
                }
            }

            // Sauvegarder la nouvelle image (validation incluse dans le service)
            String relativePath = fileStorageService.storeProjectImage(file, projectId);
            project.setImageFilename(relativePath);
            projectRepository.save(project);

            Map<String, String> response = new HashMap<>();
            response.put("imageUrl", fileStorageService.getFileUrl(relativePath));
            response.put("message", "Image uploaded successfully");

            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            throw e;  // Re-lancer les RuntimeException (validation, etc.)
        } catch (Exception e) {
            throw new RuntimeException("Error uploading image: " + e.getMessage(), e);
        }
    }
}

