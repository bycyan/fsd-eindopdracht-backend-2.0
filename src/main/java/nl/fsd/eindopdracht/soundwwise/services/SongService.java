package nl.fsd.eindopdracht.soundwwise.services;

import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.SongInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.SongOutputDto;
import nl.fsd.eindopdracht.soundwwise.exceptions.RecordNotFoundException;
import nl.fsd.eindopdracht.soundwwise.models.Project;
import nl.fsd.eindopdracht.soundwwise.models.Song;
import nl.fsd.eindopdracht.soundwwise.repositories.ProjectRepository;
import nl.fsd.eindopdracht.soundwwise.repositories.SongRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.time.Instant;

@Service
public class SongService {

    //INJECT
    private final SongRepository songRepository;
    private final ProjectRepository projectRepository;
    private final String fileStorageLocation;
    private final FileService fileService;

    //CONSTRUCT
    public SongService(SongRepository songRepository, ProjectRepository projectRepository, @Value("${file.storage.location}") String fileStorageLocation, FileService fileService) {
        this.songRepository = songRepository;
        this.projectRepository = projectRepository;
        this.fileStorageLocation = fileStorageLocation;
        this.fileService = fileService;
    }

    //SERVICES
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
