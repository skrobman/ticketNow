package skrobman.dev.springstart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skrobman.dev.springstart.entity.TokenEntity;
import skrobman.dev.springstart.entity.UserEntity;

public interface TokenRepository extends JpaRepository<TokenEntity, Long> {
    TokenEntity findByToken(String token);

    TokenEntity findByUser(UserEntity user);
}
