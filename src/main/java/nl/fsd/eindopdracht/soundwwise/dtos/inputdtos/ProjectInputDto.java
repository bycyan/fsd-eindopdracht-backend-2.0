package nl.fsd.eindopdracht.soundwwise.dtos.inputdtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import nl.fsd.eindopdracht.soundwwise.models.Authority;

import java.util.List;
import java.util.Set;

@Getter
@Setter
public class ProjectInputDto {

    @NotBlank(message = "")
    public String projectName;

    private String projectCoverImage;

    private Long projectManagerId;

    private Set<Long> contributorIds;
}
