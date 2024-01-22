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

//    userId
//    userName
//    password
//    firstName
//    lastName
//    userImage
//    userJob
//    userRole

    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    //Unique identifier
    @Column(unique=true)
    private String email;
    private String password;
    private String profileImage;

    //Relations


    //Security
    @Column
    private String apikey;

    public void addAuthority(Authority authority) {
        this.authorities.add(authority);
    }
    public void removeAuthority(Authority authority) {
        this.authorities.remove(authority);
    }

    @OneToMany(
            targetEntity = Authority.class,
            mappedBy = "userId",
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    private Set<Authority> authorities = new HashSet<>();
}
