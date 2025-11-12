package lk.ijse.cmjd.researchprojecttracker.data;

import lk.ijse.cmjd.researchprojecttracker.models.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface DocumentRepository extends JpaRepository<Document, String> {
    List<Document> findByProjectId(String projectId);
}