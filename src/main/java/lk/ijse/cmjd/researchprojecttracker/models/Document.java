package lk.ijse.cmjd.researchprojecttracker.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "documents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Document {
    @Id
    private String id = UUID.randomUUID().toString();


    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;


    private String title;
    @Column(length = 2000)
    private String description;


    private String urlOrPath;


    @ManyToOne(fetch = FetchType.LAZY)
    private User uploadedBy;


    private LocalDateTime uploadedAt = LocalDateTime.now();
}