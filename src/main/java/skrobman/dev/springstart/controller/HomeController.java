package skrobman.dev.springstart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class HomeController {
    @GetMapping("/home")
    public ResponseEntity<Map<String, String>> home(){
        return ResponseEntity.ok(Map.of("message: ", "Welcome to home page!"));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/admin")
    public ResponseEntity<Map<String, String>> adminHome(){
        return ResponseEntity.ok(Map.of("message: ", "Welcome to home page admin!"));
    }
}
