package lk.ijse.cmjd.researchprojecttracker.models.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MilestoneDTO {
    private String id;
    private String title;
    private String description;
    private LocalDate dueDate;
    private Boolean isCompleted;

    // Project info
    private String projectId;
    private String projectTitle;

    // Creator info
    private String createdByName;
}
