package skrobman.dev.springstart.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import skrobman.dev.springstart.dto.JWTAuthentificationTokenDto;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.logging.LogManager;

@Component
public class JWTService {
    private static final Logger LOGGER = (Logger) LogManager.getLogManager().getLogger(String.valueOf(JWTService.class));

    @Value("${security.jwt.secret_key}")
    private final String JWT_SECRET;

    public JWTService(@Value("${security.jwt.secret_key}") String jwtSecret) {
        JWT_SECRET = jwtSecret;
    }


    public JWTAuthentificationTokenDto generateAuthToken(String email){
        JWTAuthentificationTokenDto jwtDto = new JWTAuthentificationTokenDto();
        jwtDto.setToken(generateJWTToken(email));
        jwtDto.setRefreshToken(generateRefreshToken(email));
        return jwtDto;
    }

    public JWTAuthentificationTokenDto refreshBaseToken(String email, String refreshToken){
        JWTAuthentificationTokenDto jwtDto = new JWTAuthentificationTokenDto();
        jwtDto.setToken(generateJWTToken(email));
        jwtDto.setRefreshToken(refreshToken);
        return jwtDto;
    }

    public String getEmailFromToken(String token){
        Claims claims = Jwts.parser()
                .verifyWith(getSignInKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();

        return claims.getSubject();
    }

    public String generateJWTToken(String email){
        Date date = Date.from(LocalDateTime.now().plusMinutes(15).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .subject(email)
                .expiration(date)
                .signWith(getSignInKey())
                .compact();
    }

    public String generateRefreshToken(String email){
        Date date = Date.from(LocalDateTime.now().plusDays(7).atZone(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .subject(email)
                .expiration(date)
                .signWith(getSignInKey())
                .compact();
    }

    public boolean validateJWTToken(String token){
        try {
            Jwts.parser()
                    .verifyWith(getSignInKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return true;
        } catch (ExpiredJwtException e) {
            LOGGER.error("Expired JWTException", e);
        } catch (UnsupportedJwtException e){
            LOGGER.error("Unsupported JWTException", e);
        } catch (MalformedJwtException e){
            LOGGER.error("MalformedJwtException", e);
        } catch (SecurityException e){
            LOGGER.error("Security exception", e);
        }catch (Exception e){
            LOGGER.error("Exception", e);
        }
        return false;
    }

    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
