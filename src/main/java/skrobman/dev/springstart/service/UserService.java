package skrobman.dev.springstart.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import skrobman.dev.springstart.dto.UserDto;
import skrobman.dev.springstart.entity.TokenEntity;
import skrobman.dev.springstart.entity.UserEntity;
import skrobman.dev.springstart.exception.EmailAlreadyExist;
import skrobman.dev.springstart.repository.TokenRepository;
import skrobman.dev.springstart.repository.UserRepository;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private TokenRepository tokenRepository;

    public void registerUser(UserDto userDto){
        boolean userExists = userRepository.findByEmail(userDto.getEmail()) != null;

        if(userExists){
            throw new EmailAlreadyExist("Account with email: " + userDto.getEmail() + "already exist");
        }

        UserEntity user = new UserEntity();
        user.setEmail(userDto.getEmail());
        //user.setUsername(userDto.getUsername());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(false);

        userRepository.save(user);

        String token = UUID.randomUUID().toString();
        OffsetDateTime expiryDate = OffsetDateTime.now().plusMinutes(5);

        TokenEntity verificationToken = new TokenEntity();
        verificationToken.setToken(token);
        verificationToken.setExpiryDate(expiryDate);
        verificationToken.setUser(user);
        tokenRepository.save(verificationToken);

        sendVerificationEmail(user.getEmail(), token);
    }

    private void sendVerificationEmail(String email, String token){
        String subject = "Email Verification";
        String conformationUrl = "http://localhost:8080/user/verify?token=" + token;
        String message = "Please, click the link to activate your account " + conformationUrl;

        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(email);
        emailMessage.setSubject(subject);
        emailMessage.setText(message);
        mailSender.send(emailMessage);

    }


}
