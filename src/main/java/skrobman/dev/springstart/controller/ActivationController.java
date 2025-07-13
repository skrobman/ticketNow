package skrobman.dev.springstart.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import skrobman.dev.springstart.service.ActivationService;

import java.util.Map;

@RestController
@RequestMapping("/user/verify")
public class ActivationController {
    private final ActivationService activationService;

    public ActivationController(ActivationService activationService) {
        this.activationService = activationService;
    }

    @GetMapping
    public ResponseEntity<Map<String, String>> verifyAccount(@RequestParam("token") String token) {
        return activationService.activateToken(token);
    }
}
