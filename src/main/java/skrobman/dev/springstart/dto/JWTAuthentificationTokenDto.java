package skrobman.dev.springstart.dto;

import lombok.Data;

@Data
public class JWTAuthentificationTokenDto {
    private String token;
    private String refreshToken;
}
