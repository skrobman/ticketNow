package skrobman.dev.springstart.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import skrobman.dev.springstart.dto.EmailDto;
import skrobman.dev.springstart.dto.UserDto;
import skrobman.dev.springstart.exception.AlreadyActivated;
import skrobman.dev.springstart.exception.EmailAlreadyExist;
import skrobman.dev.springstart.exception.EmailDoesNotExist;
import skrobman.dev.springstart.exception.TooManyRequestsException;
import skrobman.dev.springstart.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("user/register")
public class RegistrationController {
    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<Map<String, String>> register(@Valid @RequestBody UserDto userDto){
        try {
            userService.registerUser(userDto);
            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .body(Map.of(
                            "message", "Registration Successful. Please, check your email to verify your email."
                    ));
        }catch (EmailAlreadyExist e){
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(Map.of(
                            "error", "Email is already exist"
                    ));
        }

    }

    @PostMapping("/resend-token")
    public ResponseEntity<Map<String, String>> resendToken(@RequestBody EmailDto request) {
        try {
            userService.resendVerificationEmail(request);
            return ResponseEntity.ok(Map.of("status", "Verification email re-sent"));
        } catch (EmailDoesNotExist | AlreadyActivated e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of("error", e.getMessage()));
        } catch (TooManyRequestsException e) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).body(Map.of(
                    "error", "Too Many Requests",
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of("error", "Internal Server Error"));
        }
    }
}
