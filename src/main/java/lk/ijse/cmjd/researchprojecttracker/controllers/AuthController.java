package lk.ijse.cmjd.researchprojecttracker.controllers;

import lk.ijse.cmjd.researchprojecttracker.util.JwtUtil;
import lk.ijse.cmjd.researchprojecttracker.models.User;
import lk.ijse.cmjd.researchprojecttracker.data.UserRepository;
import lk.ijse.cmjd.researchprojecttracker.models.UserRole;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.Map;


@RestController
@RequestMapping("/api/auth")
public class AuthController {


    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;


    public AuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody Map<String, String> body){
        String username = body.get("username");
        String password = body.get("password");
        String fullName = body.getOrDefault("fullName", "");
        String roleStr = body.getOrDefault("role", "MEMBER").toUpperCase(); // default = MEMBER

        if (userRepository.existsByUsername(username)){
            return ResponseEntity.badRequest().body(Map.of("error","Username already exists"));
        }

        UserRole role;
        try {
            role = UserRole.valueOf(roleStr);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(Map.of("error","Invalid role. Allowed roles: ADMIN, PI, MEMBER, VIEWER"));
        }

        User user = User.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .fullName(fullName)
                .role(role)
                .build();

        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message","User registered successfully as " + role));
    }



    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String,String> body){
        String username = body.get("username");
        String password = body.get("password");
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        var user = userRepository.findByUsername(username).orElseThrow();
        String token = jwtUtil.generateToken(username, user.getRole().name());
        return ResponseEntity.ok(Map.of("token", token));
    }
}
