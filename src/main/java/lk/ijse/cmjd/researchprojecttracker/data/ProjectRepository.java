package lk.ijse.cmjd.researchprojecttracker.data;

import lk.ijse.cmjd.researchprojecttracker.models.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface ProjectRepository extends JpaRepository<Project, String> {
    List<Project> findByPiId(String piId);
}