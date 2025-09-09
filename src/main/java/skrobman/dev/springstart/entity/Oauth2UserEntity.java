package skrobman.dev.springstart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.lang.annotation.Documented;
import java.util.UUID;

/// A user that decided to log in using OAuth 2.0 OpenID Connect
@Entity
@Table(name = "oauth2_users")
@RequiredArgsConstructor
public class Oauth2UserEntity extends AuditableEntity implements User {
    /// Unique generated ID
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    /// User's email. Must be unique
    @Setter
    @Getter
    @Column(name = "email", nullable = false, unique = true)
    private String email;

    /// Provider name (e.g. Google, Facebook, etc.)
    @Getter
    @Column(name = "provider", nullable = false)
    private String provider;

    /// Provider ID
    @Getter
    @Column(name = "provider_id", nullable = false)
    private String providerId;

    public Oauth2UserEntity(String email) {
        this.email = email;
    }

    public Oauth2UserEntity(String provider, String providerId) {
        this.provider = provider;
        this.providerId = providerId;
    }
}
