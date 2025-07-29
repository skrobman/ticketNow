package skrobman.dev.springstart.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import skrobman.dev.springstart.dto.JWTAuthentificationTokenDto;
import skrobman.dev.springstart.dto.UserDto;
import skrobman.dev.springstart.service.UserService;

import java.time.Duration;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody UserDto userCredentials,
            HttpServletResponse response
    ){
        try {
            JWTAuthentificationTokenDto jwtTokens = userService.login(userCredentials);
            
            //TODO: Access Roles errors exceptions
            //TODO: Log-out

            ResponseCookie refreshCookie = ResponseCookie.from("refreshTokens")
                    .httpOnly(true)
                    .secure(true)
                    .path("/auth/refresh")
                    .maxAge(Duration.ofDays(7))
                    .sameSite("Strict")
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

            //return ResponseEntity.ok(jwtTokens);
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header("Location", "/home")
                    .build();

        } catch (AuthenticationException ex) {
            Map<String, Object> errorBody = Map.of(
                    "status", 401,
                    "error", "Unauthorized",
                    "message", "Invalid username or password"
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody);
        }
    }
}
