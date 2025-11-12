package lk.ijse.cmjd.researchprojecttracker.data;

import lk.ijse.cmjd.researchprojecttracker.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, String> {
    Optional<User> findByUsername(String username);
    boolean existsByUsername(String username);
}