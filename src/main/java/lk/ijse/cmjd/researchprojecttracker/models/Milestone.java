package lk.ijse.cmjd.researchprojecttracker.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.util.UUID;


@Entity
@Table(name = "milestones")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Milestone {
    @Id
    private String id = UUID.randomUUID().toString();


    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Project project;


    private String title;
    @Column(length = 2000)
    private String description;


    private LocalDate dueDate;
    private Boolean isCompleted = false;


    @ManyToOne(fetch = FetchType.LAZY)
    private User createdBy;
}