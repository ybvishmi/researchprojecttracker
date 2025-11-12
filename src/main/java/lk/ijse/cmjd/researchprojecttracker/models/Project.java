package lk.ijse.cmjd.researchprojecttracker.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Project {
    @Id
    private String id = UUID.randomUUID().toString();


    private String title;
    @Column(length = 2000)
    private String summary;


    @Enumerated(EnumType.STRING)
    private Status status;


    @ManyToOne(fetch = FetchType.LAZY)
    private User pi;


    private String tags; // comma-separated


    private LocalDate startDate;
    private LocalDate endDate;


    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();
}
