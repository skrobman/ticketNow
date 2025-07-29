package skrobman.dev.springstart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "oauth2_users")
@RequiredArgsConstructor
public class Oauth2UserEntity extends AuditableEntity implements User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Setter
    @Getter
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    public Oauth2UserEntity(String email) {
        this.email = email;
    }
}
