package nl.fsd.eindopdracht.soundwwise.dtos.outputdtos;

import lombok.Getter;
import lombok.Setter;
import nl.fsd.eindopdracht.soundwwise.models.User;

import java.util.Set;

@Getter
@Setter
public class ProjectOutputDto {
    public Long id;
    public String projectName;
    public String projectCoverImage;

    public Long projectManagerId;
    public Set<User> contributorIds;
}
