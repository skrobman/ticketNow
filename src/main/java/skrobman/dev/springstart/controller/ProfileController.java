package skrobman.dev.springstart.controller;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import skrobman.dev.springstart.dto.ProfileDto;
import skrobman.dev.springstart.service.ProfileService;

import java.util.Map;

@RestController
public class ProfileController {
    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PatchMapping("/profile")
    public ResponseEntity<?> addProfile(@Valid @RequestBody ProfileDto profileDto) {
        try {
            ProfileDto profile = profileService.updateUserProfile(profileDto);
            return ResponseEntity.ok(profile);
        }catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", "An unexpected error occurred"));
        }

    }

    @GetMapping("/profile")
    public ResponseEntity<ProfileDto> getProfile() {
        ProfileDto profile = profileService.getCurrentUserProfile();
        return ResponseEntity.ok(profile);
    }
}
