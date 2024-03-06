package nl.fsd.eindopdracht.soundwwise.dtos.inputdtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskInputDto {
    @NotBlank(message = "")
    public String taskName;
    public Date taskDue;
    public Boolean taskComplete;
}
