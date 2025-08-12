package skrobman.dev.springstart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skrobman.dev.springstart.entity.ProfileEntity;

import java.util.UUID;

public interface ProfileRepository extends JpaRepository<ProfileEntity, UUID> {
}
