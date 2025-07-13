package skrobman.dev.springstart.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import skrobman.dev.springstart.entity.TokenEntity;
import skrobman.dev.springstart.entity.UserEntity;
import skrobman.dev.springstart.repository.TokenRepository;
import skrobman.dev.springstart.repository.UserRepository;

import java.time.OffsetDateTime;

@Service
public class ActivationService {
    private final UserRepository userRepository;
    private final TokenRepository tokenRepository;

    public ActivationService(UserRepository userRepository, TokenRepository tokenRepository) {
        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
    }

    public ResponseEntity<String> activateToken(String token){
        TokenEntity verificationToken = tokenRepository.findByToken(token);

        if(verificationToken == null){
            return ResponseEntity.badRequest().body("Invalid Token");
        }
        if(verificationToken.getExpiryDate().isBefore(OffsetDateTime.now())){
            return ResponseEntity.badRequest().body("Token expired");
        }

        UserEntity user = verificationToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        return ResponseEntity.ok("Account verified");
    }
}
