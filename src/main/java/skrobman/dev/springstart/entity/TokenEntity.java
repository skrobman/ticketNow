package skrobman.dev.springstart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "verification_token")
public class TokenEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter
    @Getter
    @Column(name = "token")
    private String token;

    @Setter
    @Getter
    @Column(name = "expiry_date")
    private OffsetDateTime expiryDate;

    @Getter
    @Setter
    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private UserEntity user;

    public TokenEntity() {
    }

    public TokenEntity(String token, OffsetDateTime expiryDate) {
        this.token = token;
        this.expiryDate = expiryDate;
    }

}
