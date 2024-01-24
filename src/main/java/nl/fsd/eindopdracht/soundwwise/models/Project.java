package nl.fsd.eindopdracht.soundwwise.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "projects")
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    //GENERAL
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    private String projectName;
    private String projectCoverImage;

    //RELATIONS
    @ManyToOne
    @JsonIgnore
    private User projectOwner;

    @ManyToMany
    @JoinTable(
            name = "project_contributors",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "contributor_id")
    )
    private Set<User> contributors;

}
