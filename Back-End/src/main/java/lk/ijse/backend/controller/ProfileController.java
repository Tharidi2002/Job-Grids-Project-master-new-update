package lk.ijse.backend.controller;

import lk.ijse.backend.dto.formDTO.ProfileDataDTO;
import lk.ijse.backend.service.ProfileService;
import lk.ijse.backend.util.ResponseUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/profile")
@CrossOrigin
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<ResponseUtil> saveProfile(@ModelAttribute ProfileDataDTO profileDataDTO) {
        return ResponseEntity.ok(profileService.saveProfile(profileDataDTO));
    }

    @PutMapping
    public ResponseEntity<ResponseUtil> updateProfile(@ModelAttribute ProfileDataDTO profileDataDTO) {
        return ResponseEntity.ok(profileService.updateProfile(profileDataDTO));
    }

    @GetMapping("/{email}")
    public ResponseEntity<ResponseUtil> getProfile(@PathVariable String email) {
        return ResponseEntity.ok(profileService.getProfileByEmail(email));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<ResponseUtil> deleteProfile(@PathVariable String email) {
        return ResponseEntity.ok(profileService.deleteProfile(email));
    }
}