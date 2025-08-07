package skrobman.dev.springstart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HomeController {
    @GetMapping("/home")
    public ResponseEntity<Map<String, String>> home() {
        return ResponseEntity.ok(Map.of("message: ", "Welcome to home page via login+password!"));
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, String>> index() {
        return ResponseEntity.ok(Map.of("message: ", "Welcome to home page via oauth2!"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<Map<String, String>> adminHome() {
        return ResponseEntity.ok(Map.of("message: ", "Welcome to home page admin!"));
    }
}
