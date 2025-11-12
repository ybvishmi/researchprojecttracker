package lk.ijse.cmjd.researchprojecttracker.data;

import lk.ijse.cmjd.researchprojecttracker.models.Milestone;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;


public interface MilestoneRepository extends JpaRepository<Milestone, String> {
    List<Milestone> findByProjectId(String projectId);
}
