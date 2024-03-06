package nl.fsd.eindopdracht.soundwwise.controllers;

import jakarta.validation.Valid;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.SongInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.TaskInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.SongOutputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.TaskOutputDto;
import nl.fsd.eindopdracht.soundwwise.services.SongService;
import nl.fsd.eindopdracht.soundwwise.util.FieldErrorHandling;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

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
    @PostMapping("/{projectId}")
    public ResponseEntity<Object> addSong(
            @PathVariable Long projectId,
            @Valid @RequestBody SongInputDto songInputDto,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasFieldErrors()) {
                return ResponseEntity.badRequest().body(FieldErrorHandling.getErrorToStringHandling(bindingResult));
            }

            SongOutputDto songOutputDto = songService.addSong(projectId, songInputDto);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + songOutputDto.songId).toUriString());
            return ResponseEntity.created(uri).body(songOutputDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during song creation.");
        }
    }

    @GetMapping("/{projectId}/songs")
    public ResponseEntity<List<SongOutputDto>> getSongs(@PathVariable Long projectId) {
        return ResponseEntity.ok(songService.getSongs(projectId));
    }

    @PutMapping("/{songId}")
    public ResponseEntity<Object> updateSong(@PathVariable Long songId, @Valid @RequestBody SongInputDto songInputDto, BindingResult bindingResult) {
        SongOutputDto songOutputDto = songService.updateSong(songId, songInputDto);
        return new ResponseEntity<>(songOutputDto, HttpStatus.OK);
    }
}
