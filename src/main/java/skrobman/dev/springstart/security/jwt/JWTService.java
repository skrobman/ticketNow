package skrobman.dev.springstart.security.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import skrobman.dev.springstart.dto.JWTAuthentificationTokenDto;

import javax.crypto.SecretKey;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
@Log4j2
@Component
public class JWTService {
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
            log.error("Expired JWTException", e);
        } catch (UnsupportedJwtException e){
            log.error("Unsupported JWTException", e);
        } catch (MalformedJwtException e){
            log.error("MalformedJwtException", e);
        } catch (SecurityException e){
            log.error("Security exception", e);
        }catch (Exception e){
            log.error("Exception", e);
        }
        return false;
    }

    private SecretKey getSignInKey(){
        byte[] keyBytes = Decoders.BASE64.decode(JWT_SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
