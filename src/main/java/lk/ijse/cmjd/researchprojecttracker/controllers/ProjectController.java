package lk.ijse.cmjd.researchprojecttracker.controllers;

import lk.ijse.cmjd.researchprojecttracker.models.Project;
import lk.ijse.cmjd.researchprojecttracker.data.ProjectRepository;
import lk.ijse.cmjd.researchprojecttracker.models.Status;
import lk.ijse.cmjd.researchprojecttracker.data.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;


    public ProjectController(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }


    @GetMapping
    public List<Project> listAll(){
        return projectRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        return projectRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @PostMapping
    @PreAuthorize("hasAnyRole('PI','ADMIN')")
    public ResponseEntity<?> create(@RequestBody Project project){
        project.setCreatedAt(LocalDateTime.now());
        project.setUpdatedAt(LocalDateTime.now());
        var saved = projectRepository.save(project);
        return ResponseEntity.ok(saved);
    }


    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('PI','ADMIN')")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Project incoming){
        return projectRepository.findById(id).map(p -> {
            p.setTitle(incoming.getTitle());
            p.setSummary(incoming.getSummary());
            p.setTags(incoming.getTags());
            p.setEndDate(incoming.getEndDate());
            p.setStartDate(incoming.getStartDate());
            p.setUpdatedAt(LocalDateTime.now());
            projectRepository.save(p);
            return ResponseEntity.ok(p);
        }).orElse(ResponseEntity.notFound().build());
    }


    @PatchMapping("/{id}/status")
    @PreAuthorize("hasAnyRole('PI','ADMIN')")
    public ResponseEntity<?> updateStatus(@PathVariable String id, @RequestBody Map<String,String> body){
        String status = body.get("status");
        return projectRepository.findById(id).map(p -> {
            p.setStatus(Status.valueOf(status));
            p.setUpdatedAt(LocalDateTime.now());
            projectRepository.save(p);
            return ResponseEntity.ok(p);
        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable String id){
        if (!projectRepository.existsById(id)) return ResponseEntity.notFound().build();
        projectRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message","deleted"));
    }
}