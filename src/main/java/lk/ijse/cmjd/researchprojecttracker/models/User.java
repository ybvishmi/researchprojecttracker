package lk.ijse.cmjd.researchprojecttracker.models;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    private String id;


    @Column(unique = true, nullable = false)
    private String username; // email or unique username


    @Column(nullable = false)
    private String password;


    private String fullName;


    @Enumerated(EnumType.STRING)
    private UserRole role;


    private LocalDateTime createdAt = LocalDateTime.now();

    @PrePersist
    public void prePersist() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}