package nl.fsd.eindopdracht.soundwwise.controllers;

import jakarta.validation.Valid;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.SongInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.SongOutputDto;
import nl.fsd.eindopdracht.soundwwise.services.FileService;
import nl.fsd.eindopdracht.soundwwise.services.SongService;
import nl.fsd.eindopdracht.soundwwise.util.FieldErrorHandling;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.util.Objects;

@CrossOrigin
@RestController
@RequestMapping("/song")
public class SongController {

    //INJECT
    private final SongService songService;

    //CONSTRUCT
    public SongController(SongService songService) {
        this.songService = songService;
    }

    //POST
    @PostMapping("/add/{projectId}")
    public ResponseEntity<Object> addSong(
            @PathVariable Long projectId,
            @Valid @RequestBody SongInputDto songInputDto,
//            @RequestParam("file") MultipartFile file,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasFieldErrors()) {
                return ResponseEntity.badRequest().body(FieldErrorHandling.getErrorToStringHandling(bindingResult));
            }

//            SongOutputDto songOutputDto = songService.addSong(projectId, songInputDto, file);
            SongOutputDto songOutputDto = songService.addSong(projectId, songInputDto);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + songOutputDto.songId).toUriString());
            return ResponseEntity.created(uri).body(songOutputDto);
        } catch (Exception e) {
            // Handle the exception here
            // You might want to log the exception for debugging purposes
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during song creation.");
        }
    }
}
