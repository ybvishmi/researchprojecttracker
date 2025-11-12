package lk.ijse.cmjd.researchprojecttracker.controllers;

import lk.ijse.cmjd.researchprojecttracker.models.Milestone;
import lk.ijse.cmjd.researchprojecttracker.data.MilestoneRepository;
import lk.ijse.cmjd.researchprojecttracker.data.ProjectRepository;
import lk.ijse.cmjd.researchprojecttracker.data.UserRepository;
import lk.ijse.cmjd.researchprojecttracker.models.dto.MilestoneDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api")
public class MilestoneController {


    private final MilestoneRepository milestoneRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;


    public MilestoneController(MilestoneRepository milestoneRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.milestoneRepository = milestoneRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }


    @GetMapping("/projects/{id}/milestones")
    public List<MilestoneDTO> listByProject(@PathVariable String id){
        var milestones = milestoneRepository.findByProjectId(id);

        return milestones.stream()
                .map(m -> MilestoneDTO.builder()
                        .id(m.getId())
                        .title(m.getTitle())
                        .description(m.getDescription())
                        .dueDate(m.getDueDate())
                        .isCompleted(m.getIsCompleted())
                        .projectId(m.getProject().getId())
                        .projectTitle(m.getProject().getTitle())
                        .createdByName(m.getCreatedBy() != null ? m.getCreatedBy().getFullName() : null)
                        .build())
                .toList();
    }


    @PostMapping("/projects/{id}/milestones")
    @PreAuthorize("hasAnyRole('ROLE_PI','ROLE_MEMBER','ROLE_ADMIN')")
    public ResponseEntity<?> create(@PathVariable String id, @RequestBody Milestone milestone, @RequestParam String username){
        var project = projectRepository.findById(id).orElseThrow();
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found with username: " + username));
        milestone.setProject(project);
        milestone.setCreatedBy(user);
        milestoneRepository.save(milestone);
        return ResponseEntity.ok(MilestoneDTO.builder()
                .id(milestone.getId())
                .title(milestone.getTitle())
                .description(milestone.getDescription())
                .dueDate(milestone.getDueDate())
                .isCompleted(milestone.getIsCompleted())
                .projectId(milestone.getProject().getId())
                .projectTitle(milestone.getProject().getTitle())
                .createdByName(milestone.getCreatedBy() != null ? milestone.getCreatedBy().getFullName() : null)
                .build());
    }


    @PutMapping("/milestones/{id}")
    @PreAuthorize("hasAnyRole('PI','MEMBER','ADMIN')")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Milestone incoming){
        return milestoneRepository.findById(id).map(m -> {
            m.setTitle(incoming.getTitle());
            m.setDescription(incoming.getDescription());
            m.setDueDate(incoming.getDueDate());
            m.setIsCompleted(incoming.getIsCompleted());
            milestoneRepository.save(m);
            return ResponseEntity.ok(MilestoneDTO.builder()
                    .id(m.getId())
                    .title(m.getTitle())
                    .description(m.getDescription())
                    .dueDate(m.getDueDate())
                    .isCompleted(m.getIsCompleted())
                    .projectId(m.getProject().getId())
                    .projectTitle(m.getProject().getTitle())
                    .createdByName(m.getCreatedBy() != null ? m.getCreatedBy().getFullName() : null)
                    .build());
        }).orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/milestones/{id}")
    @PreAuthorize("hasAnyRole('ROLE_PI','ROLE_ADMIN')")
    public ResponseEntity<?> delete(@PathVariable String id){
        if (!milestoneRepository.existsById(id)) return ResponseEntity.notFound().build();
        milestoneRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message","Milestone deleted"));
    }
}