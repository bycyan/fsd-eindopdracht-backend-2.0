package nl.fsd.eindopdracht.soundwwise.dtos.outputdtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SongOutputDto {

    public Long songId;

    public String songName;
    public String songUrl;

    public Long projectId;

}
