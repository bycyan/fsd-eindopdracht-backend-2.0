package nl.fsd.eindopdracht.soundwwise.dtos.inputdtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongInputDto {
    @NotBlank(message = "Firstname field shouldn't be empty.")
    public String songName;

    public String songUrl;
}
