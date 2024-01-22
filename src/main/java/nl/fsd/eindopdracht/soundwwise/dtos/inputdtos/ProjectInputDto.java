package nl.fsd.eindopdracht.soundwwise.dtos.inputdtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectInputDto {
    @NotBlank(message = "Workshop title can't be empty.")
    public String title;
}
