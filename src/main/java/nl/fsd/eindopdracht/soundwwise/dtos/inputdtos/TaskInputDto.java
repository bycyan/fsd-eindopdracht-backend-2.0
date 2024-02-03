package nl.fsd.eindopdracht.soundwwise.dtos.inputdtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskInputDto {
    @NotBlank(message = "")
    public String taskName;

    public Boolean taskComplete;
}
