package skrobman.dev.springstart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import skrobman.dev.springstart.entity.Oauth2UserEntity;

import java.util.UUID;

@Repository
public interface Oauth2UserRepository extends JpaRepository<Oauth2UserEntity, UUID> {
    Oauth2UserEntity findByEmail(String email);
}
