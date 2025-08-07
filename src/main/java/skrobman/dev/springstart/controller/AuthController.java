package skrobman.dev.springstart.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
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
public class AuthController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(
            @RequestBody UserDto userCredentials,
            HttpServletResponse response
    ) {
        try {
            JWTAuthentificationTokenDto jwtTokens = userService.login(userCredentials);
            ResponseCookie refreshCookie = ResponseCookie.from("refreshTokens", jwtTokens.getRefreshToken())
                    .httpOnly(true)
                    .secure(true)
                    .path("/auth/refresh")
                    .maxAge(Duration.ofDays(7))
                    .sameSite("Strict")
                    .build();

            response.addHeader(HttpHeaders.SET_COOKIE, refreshCookie.toString());

            return ResponseEntity.ok(jwtTokens);

        } catch (AuthenticationException ex) {
            Map<String, Object> errorBody = Map.of(
                    "status", 401,
                    "error", "Unauthorized",
                    "message", "Invalid username or password"
            );
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorBody);
        }
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        ResponseCookie deleteCookie = ResponseCookie.from("refreshTokens", "")
                .path("/auth/refresh")
                .httpOnly(true)
                .secure(true)
                .maxAge(0)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, deleteCookie.toString());

        SecurityContextHolder.clearContext();

        return ResponseEntity.ok(Map.of("message", "Logged out successfully"));
    }
}
