package skrobman.dev.springstart.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    private Short id;

    @Getter
    @Setter
    @Column(name = "code")
    private String code;


    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users = new HashSet<>();
}
