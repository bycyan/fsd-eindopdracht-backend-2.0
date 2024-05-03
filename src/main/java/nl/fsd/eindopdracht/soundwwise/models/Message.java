package nl.fsd.eindopdracht.soundwwise.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "messages")
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    @Id
    @GeneratedValue
    private Long messageId;
    private String messageText;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User messageAuthor;

    @ManyToOne
    @JsonIgnore
    @JoinColumn(name = "project_id")
    private Project project;
}
