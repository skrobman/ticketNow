package skrobman.dev.springstart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skrobman.dev.springstart.entity.TokenEntity;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    TokenEntity findByToken(String token);
}
