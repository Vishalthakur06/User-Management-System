package com.ums.backend.controller;

import com.ums.backend.entity.User;
import com.ums.backend.repository.UserRepository;
import com.ums.backend.util.JwtUtil;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    public AuthController(UserRepository userRepository,
                          JwtUtil jwtUtil,
                          PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.passwordEncoder = passwordEncoder;
    }

     //register
    @PostMapping(
            value = "/register",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<?> register(
            @RequestParam @NotBlank String name,
            @RequestParam @Email String email,
            @RequestParam @NotBlank String password,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) String dob,
            @RequestParam(required = false) MultipartFile profileImage,
            @RequestParam(required = false) String bio,
            @RequestParam(required = false) String address
    ) throws IOException {

        if (userRepository.findByEmail(email).isPresent()) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("message", "Email already exists"));
        }

        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setPhone(phone);
        user.setBio(bio);
        user.setAddress(address);

        if (dob != null && !dob.isBlank()) {
            user.setDob(LocalDate.parse(dob));
        }

        if (profileImage != null && !profileImage.isEmpty()) {
            Path uploadDir = Paths.get("uploads/profile-images");
            if (!Files.exists(uploadDir)) {
                Files.createDirectories(uploadDir);
            }

            String fileName = System.currentTimeMillis() + "_" + profileImage.getOriginalFilename();
            Path filePath = uploadDir.resolve(fileName);
            Files.copy(profileImage.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            user.setProfileImage(fileName);
        }

        userRepository.save(user);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(Map.of("message", "User registered successfully"));
    }

    //login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) {

        User dbUser = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!passwordEncoder.matches(user.getPassword(), dbUser.getPassword())) {
            return ResponseEntity
                    .status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("message", "Invalid credentials"));
        }

        String token = jwtUtil.generateToken(dbUser.getEmail());

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("email", dbUser.getEmail());
        response.put("name", dbUser.getName());
        response.put("profileImage", dbUser.getProfileImage());

        return ResponseEntity.ok(response);
    }

   //logout
    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(Map.of("message", "Logout successful"));
    }
}
