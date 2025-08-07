package skrobman.dev.springstart.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import skrobman.dev.springstart.dto.EmailDto;
import skrobman.dev.springstart.dto.JWTAuthentificationTokenDto;
import skrobman.dev.springstart.dto.UserDto;
import skrobman.dev.springstart.entity.TokenEntity;
import skrobman.dev.springstart.entity.UserEntity;
import skrobman.dev.springstart.exception.AlreadyActivated;
import skrobman.dev.springstart.exception.EmailAlreadyExist;
import skrobman.dev.springstart.exception.EmailDoesNotExist;
import skrobman.dev.springstart.exception.TooManyRequestsException;
import skrobman.dev.springstart.repository.TokenRepository;
import skrobman.dev.springstart.repository.UserRepository;
import skrobman.dev.springstart.security.jwt.JWTService;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;
    private final TokenRepository tokenRepository;
    private final EmailRateLimiterService emailRateLimiterService;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;


    public void registerUser(UserDto userDto){
        boolean userExists = userRepository.findByEmail(userDto.getEmail()) != null;

        if(userExists){
            throw new EmailAlreadyExist("Account with email: " + userDto.getEmail() + "already exist");
        }

        //User Registration Logic
        UserEntity user = new UserEntity();
        user.setEmail(userDto.getEmail());
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEnabled(false);

        userRepository.save(user);

        //Token generation
        TokenEntity token = createOrUpdateToken(user);
        sendVerificationEmail(user.getEmail(), token.getToken());
    }

    private void sendVerificationEmail(String email, String token){
        String subject = "Email Verification";
        String conformationUrl = "http://localhost:8080/user/verify?token=" + token;
        String message = "Please, click the link to activate your account " + conformationUrl
                + "\n Your token will be valid for the next 5 minutes. " +
                "If your token has expired, you can resend email. " +
                "\n Please, don't answer this email";

        //Email Message Body
        SimpleMailMessage emailMessage = new SimpleMailMessage();
        emailMessage.setTo(email);
        emailMessage.setSubject(subject);
        emailMessage.setText(message);

        mailSender.send(emailMessage);
    }

    public void resendVerificationEmail(EmailDto emailDto) throws EmailDoesNotExist, TooManyRequestsException {
        UserEntity user = userRepository.findByEmail(emailDto.getEmail());

        if(user == null){
            throw new EmailDoesNotExist("Account with email: " + emailDto.getEmail() + "does not exist");
        }

        if (!emailRateLimiterService.canSendEmail(user.getEmail())) {
            throw new TooManyRequestsException("You have exceeded the email sending limit. Please try again later.");
        }

        if(user.isEnabled()){
            throw new AlreadyActivated("You have an activated account for email: " + user.getEmail());
        }

        TokenEntity token = createOrUpdateToken(user);
        sendVerificationEmail(user.getEmail(), token.getToken());
    }

    private TokenEntity createOrUpdateToken(UserEntity user){
        TokenEntity token = tokenRepository.findByUser(user);
        String newToken = UUID.randomUUID().toString();
        OffsetDateTime expiry = OffsetDateTime.now().plusMinutes(5);
        if (token == null) {
            token = new TokenEntity();
            token.setUser(user);
        }
        token.setToken(newToken);
        token.setExpiryDate(expiry);
        return tokenRepository.save(token);
    }

    public JWTAuthentificationTokenDto login(UserDto userCredentials) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userCredentials.getEmail(),
                        userCredentials.getPassword()
                )
        );

        return jwtService.generateAuthToken(userCredentials.getEmail());
    }
}
