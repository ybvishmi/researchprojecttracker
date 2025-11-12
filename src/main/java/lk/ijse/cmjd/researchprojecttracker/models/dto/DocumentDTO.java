package lk.ijse.cmjd.researchprojecttracker.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DocumentDTO {
    private String id;
    private String title;
    private String description;
    private String urlOrPath;
    private String projectId;
    private String projectTitle;
    private String uploadedByName;
    private LocalDateTime uploadedAt;
}

