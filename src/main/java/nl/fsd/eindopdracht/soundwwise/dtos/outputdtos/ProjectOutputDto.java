package nl.fsd.eindopdracht.soundwwise.dtos.outputdtos;

import lombok.Getter;
import lombok.Setter;
import nl.fsd.eindopdracht.soundwwise.models.User;

import java.util.Set;

@Getter
@Setter
public class ProjectOutputDto {
    public Long projectId;
    public String projectName;
    public String projectCoverImage;
    public String projectArtist;
    public String projectRelease;
    public Long projectOwnerId;
    public Set<User> contributors;

}
