package lk.ijse.cmjd.researchprojecttracker.controllers;

import lk.ijse.cmjd.researchprojecttracker.models.User;
import lk.ijse.cmjd.researchprojecttracker.data.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/users")
public class UserController {


    private final UserRepository userRepository;


    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public List<User> listAll(){
        return userRepository.findAll();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id){
        return userRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable String id){
        if (!userRepository.existsById(id)) return ResponseEntity.notFound().build();
        userRepository.deleteById(id);
        return ResponseEntity.ok(Map.of("message","User deleted"));
    }
}
