package lk.ijse.cmjd.researchprojecttracker.controllers;

import lk.ijse.cmjd.researchprojecttracker.models.Document;
import lk.ijse.cmjd.researchprojecttracker.data.DocumentRepository;
import lk.ijse.cmjd.researchprojecttracker.data.ProjectRepository;
import lk.ijse.cmjd.researchprojecttracker.data.UserRepository;
import lk.ijse.cmjd.researchprojecttracker.models.dto.DocumentDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class DocumentController {


    private final DocumentRepository documentRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;


    public DocumentController(DocumentRepository documentRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.documentRepository = documentRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("/projects/{id}/documents")
    public List<DocumentDTO> listByProject(@PathVariable String id){
        return documentRepository.findByProjectId(id).stream()
                .map(document -> DocumentDTO.builder()
                        .id(document.getId())
                        .title(document.getTitle())
                        .description(document.getDescription())
                        .urlOrPath(document.getUrlOrPath())
                        .projectId(document.getProject() != null ? document.getProject().getId() : null)
                        .projectTitle(document.getProject() != null ? document.getProject().getTitle() : null)
                        .uploadedByName(document.getUploadedBy() != null ? document.getUploadedBy().getFullName() : null)
                        .uploadedAt(document.getUploadedAt())
                        .build())
                .toList();
    }


    @PostMapping(value = "/projects/{id}/documents", consumes = "multipart/form-data")
    @PreAuthorize("hasAnyRole('ROLE_PI','ROLE_MEMBER','ROLE_ADMIN')")
    public ResponseEntity<?> uploadDocument(
            @PathVariable String id,
            @RequestParam("file") MultipartFile file,
            @RequestParam("userId") String userId,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "description", required = false) String description
    ) throws IOException, IOException {

        var project = projectRepository.findById(id).orElseThrow();
        var user = userRepository.findByUsername(userId).orElseThrow();

        // Define upload directory
        String uploadDir = "uploads/";
        java.nio.file.Path uploadPath = java.nio.file.Paths.get(uploadDir);
        if (!java.nio.file.Files.exists(uploadPath)) {
            java.nio.file.Files.createDirectories(uploadPath);
        }

        // Save file to disk
        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        java.nio.file.Path filePath = uploadPath.resolve(fileName);
        java.nio.file.Files.copy(file.getInputStream(), filePath, java.nio.file.StandardCopyOption.REPLACE_EXISTING);

        // Save document metadata in DB
        Document doc = new Document();
        doc.setProject(project);
        doc.setUploadedBy(user);
        doc.setTitle(title != null ? title : file.getOriginalFilename());
        doc.setDescription(description);
        doc.setUrlOrPath(filePath.toString());
        doc.setUploadedAt(java.time.LocalDateTime.now());

        documentRepository.save(doc);

        return ResponseEntity.ok(Map.of(
                "message", "File uploaded successfully",
                "filePath", filePath.toString()
        ));
    }




    @DeleteMapping("/documents/{id}")
    @PreAuthorize("hasAnyRole('PI','ADMIN')")
    public ResponseEntity<?> delete(@PathVariable String id){
        if (!documentRepository.existsById(id)) return ResponseEntity.notFound().build();
        documentRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message","Document deleted"));
    }
}