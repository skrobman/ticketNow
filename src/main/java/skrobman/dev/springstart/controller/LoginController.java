package skrobman.dev.springstart.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import skrobman.dev.springstart.dto.JWTAuthentificationTokenDto;
import skrobman.dev.springstart.dto.UserDto;
import skrobman.dev.springstart.security.jwt.JWTService;

@RestController
@RequiredArgsConstructor
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;


    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userCredentials){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userCredentials.getEmail(),
                        userCredentials.getPassword()
                )
        );

        JWTAuthentificationTokenDto jwtTokens = jwtService.generateAuthToken(userCredentials.getEmail());

        return ResponseEntity.ok(jwtTokens);
    }
}
