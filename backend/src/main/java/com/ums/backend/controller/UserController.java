package com.ums.backend.controller;

import com.ums.backend.entity.User;
import com.ums.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173", allowCredentials = "true")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    @Autowired
    private UserRepository userRepository;

   //get profile
    @GetMapping("/profile")
    public ResponseEntity<Map<String, Object>> getProfile(Authentication auth) {
        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Map<String, Object> safeUser = new HashMap<>();
        safeUser.put("email", user.getEmail());
        safeUser.put("name", user.getName());
        safeUser.put("phone", user.getPhone());
        safeUser.put("bio", user.getBio());
        safeUser.put("address", user.getAddress());
        safeUser.put("dob", user.getDob());
        safeUser.put("profileImage", user.getProfileImage());

        return ResponseEntity.ok(safeUser);
    }

   //update profile
    @PutMapping(
            value = "/profile",
            consumes = "multipart/form-data"
    )
    public ResponseEntity<?> updateProfile(
            Authentication auth,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "phone", required = false) String phone,
            @RequestParam(value = "bio", required = false) String bio,
            @RequestParam(value = "address", required = false) String address,
            @RequestParam(value = "dob", required = false) String dob,
            @RequestPart(value = "profileImage", required = false) MultipartFile profileImage
    ) throws IOException {

        User user = userRepository.findByEmail(auth.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (name != null) user.setName(name);
        if (phone != null) user.setPhone(phone);
        if (bio != null) user.setBio(bio);
        if (address != null) user.setAddress(address);
        if (dob != null) user.setDob(LocalDate.parse(dob));

        if (profileImage != null && !profileImage.isEmpty()) {
            String fileName = System.currentTimeMillis() + "_" + profileImage.getOriginalFilename();
            Path uploadDir = Paths.get("uploads/profile-images");
            if (!Files.exists(uploadDir)) Files.createDirectories(uploadDir);
            Files.copy(profileImage.getInputStream(),
                    uploadDir.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING);
            user.setProfileImage(fileName);
        }

        userRepository.save(user);

        return ResponseEntity.ok(Map.of("message", "Profile updated successfully"));
    }
}
