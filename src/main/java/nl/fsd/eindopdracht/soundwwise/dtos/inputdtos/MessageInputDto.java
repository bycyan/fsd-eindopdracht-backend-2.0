package nl.fsd.eindopdracht.soundwwise.dtos.inputdtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageInputDto {
    @NotBlank(message = "")
    public String messageText;
}
