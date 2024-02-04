package nl.fsd.eindopdracht.soundwwise.dtos.outputdtos;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class TaskOutputDto {

    public Long taskId;
    public String taskName;
    public Date taskDue;
    public Boolean taskComplete;
    public UserOutputDto taskUser;
}
