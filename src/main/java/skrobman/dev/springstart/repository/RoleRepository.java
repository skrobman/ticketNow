package skrobman.dev.springstart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import skrobman.dev.springstart.entity.UserEntity;

public interface RoleRepository extends JpaRepository<UserEntity, Short> {
}
