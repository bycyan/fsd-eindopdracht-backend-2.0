package nl.fsd.eindopdracht.soundwwise.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "songs")
@NoArgsConstructor
@AllArgsConstructor
public class Song {
    @Id
    @GeneratedValue
    private Long songId;
    private String songName;
    private String songUrl;

    //RELATIONS
    @ManyToOne
    @JsonIgnore
    private Project project;
}
