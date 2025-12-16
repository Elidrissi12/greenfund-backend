package com.greenfund.greenfund_backend.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${app.upload.dir:uploads}")
    private String uploadDir;

    // Whitelist stricte des formats autorisés
    private static final List<String> ALLOWED_CONTENT_TYPES = Arrays.asList(
        "image/jpeg",
        "image/jpg",
        "image/png",
        "image/webp"
    );

    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(
        ".jpg",
        ".jpeg",
        ".png",
        ".webp"
    );

    /**
     * Stocke un fichier image pour un projet spécifique
     * Structure : uploads/projects/{projectId}/{uuid}.{extension}
     * 
     * @param file Le fichier à stocker
     * @param projectId L'ID du projet
     * @return Le chemin relatif stocké en base (ex: projects/12/uuid.jpg)
     * @throws RuntimeException Si le fichier est invalide
     */
    public String storeProjectImage(MultipartFile file, Long projectId) throws IOException {
        if (file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        // Validation du content-type
        String contentType = file.getContentType();
        if (contentType == null || !ALLOWED_CONTENT_TYPES.contains(contentType.toLowerCase())) {
            throw new RuntimeException(
                String.format("Invalid file type. Allowed types: %s", ALLOWED_CONTENT_TYPES)
            );
        }

        // Validation de l'extension
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.isEmpty()) {
            throw new RuntimeException("Filename is required");
        }

        String extension = "";
        int lastDotIndex = originalFilename.lastIndexOf(".");
        if (lastDotIndex > 0 && lastDotIndex < originalFilename.length() - 1) {
            extension = originalFilename.substring(lastDotIndex).toLowerCase();
        }

        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            throw new RuntimeException(
                String.format("Invalid file extension. Allowed extensions: %s", ALLOWED_EXTENSIONS)
            );
        }

        // Normaliser l'extension (jpg/jpeg -> jpg)
        if (extension.equals(".jpeg")) {
            extension = ".jpg";
        }

        // Générer un nom unique
        String filename = UUID.randomUUID().toString() + extension;

        // Créer la structure de dossiers : uploads/projects/{projectId}/
        Path projectDir = Paths.get(uploadDir, "projects", projectId.toString());
        if (!Files.exists(projectDir)) {
            Files.createDirectories(projectDir);
        }

        // Sauvegarder le fichier
        Path filePath = projectDir.resolve(filename);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Retourner le chemin relatif pour stockage en base
        return String.format("projects/%d/%s", projectId, filename);
    }

    /**
     * Supprime un fichier image
     * 
     * @param relativePath Le chemin relatif stocké en base (ex: projects/12/uuid.jpg)
     */
    public void deleteFile(String relativePath) throws IOException {
        if (relativePath == null || relativePath.isEmpty()) {
            return;
        }
        Path filePath = Paths.get(uploadDir, relativePath);
        Files.deleteIfExists(filePath);
    }

    /**
     * Génère l'URL publique d'accès à l'image
     * 
     * @param relativePath Le chemin relatif stocké en base
     * @return L'URL publique (ex: /uploads/projects/12/uuid.jpg)
     */
    public String getFileUrl(String relativePath) {
        if (relativePath == null || relativePath.isEmpty()) {
            return null;
        }
        return "/uploads/" + relativePath;
    }
}

