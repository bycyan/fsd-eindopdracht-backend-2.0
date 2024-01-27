package nl.fsd.eindopdracht.soundwwise.services;

import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.SongInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.SongOutputDto;
import nl.fsd.eindopdracht.soundwwise.exceptions.RecordNotFoundException;
import nl.fsd.eindopdracht.soundwwise.models.Project;
import nl.fsd.eindopdracht.soundwwise.models.Song;
import nl.fsd.eindopdracht.soundwwise.repositories.ProjectRepository;
import nl.fsd.eindopdracht.soundwwise.repositories.SongRepository;
import org.springframework.stereotype.Service;

@Service
public class SongService {

    //INJECT
    private final SongRepository songRepository;
    private final ProjectRepository projectRepository;

    //CONSTRUCT
    public SongService(SongRepository songRepository, ProjectRepository projectRepository) {
        this.songRepository = songRepository;
        this.projectRepository = projectRepository;
    }

    //SERVICES
    //Endpoint: /song/add/{projectId}
    public SongOutputDto addSong(Long projectId, SongInputDto songInputDto) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RecordNotFoundException(""));

        Song song = new Song();
        song = transferSongInputDtoToSong(songInputDto, song);
        song.setProject(project);

        songRepository.save(song);
        return transferSongToSongOutputDto(song);
    }


    //////////////////////////////////////////////////////
    //TRANSFER METHODS
    //////////////////////////////////////////////////////
    public Song transferSongInputDtoToSong(SongInputDto songInputDto, Song song) {
        song.setSongName(songInputDto.songName);
        song.setSongUrl(songInputDto.songUrl);
        return song;
    }

    public SongOutputDto transferSongToSongOutputDto(Song song) {
        SongOutputDto songOutputDto = new SongOutputDto();
        songOutputDto.songId = song.getSongId();
        songOutputDto.songName = song.getSongName();
        songOutputDto.songUrl = song.getSongUrl();
        return songOutputDto;
    }


}
