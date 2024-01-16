package nl.fsd.eindopdracht.soundwwise.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String firstName;
    private String lastName;

    //Unique user identifier
    @Column(unique=true)
    private String email;

    private String password;

    //Relations

    //Security
    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "userId",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();
}
