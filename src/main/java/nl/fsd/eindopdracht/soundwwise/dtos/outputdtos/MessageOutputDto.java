package nl.fsd.eindopdracht.soundwwise.dtos.outputdtos;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;
import nl.fsd.eindopdracht.soundwwise.models.User;

@Getter
@Setter
public class MessageOutputDto {
    public Long messageId;
    public String messageText;
    public UserOutputDto messageAuthor;
}
