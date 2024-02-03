package nl.fsd.eindopdracht.soundwwise.controllers;

import jakarta.servlet.http.HttpServletRequest;
import nl.fsd.eindopdracht.soundwwise.exceptions.BadRequestException;
import nl.fsd.eindopdracht.soundwwise.services.FileService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.util.Objects;

@CrossOrigin
@RestController
public class FileController {

    //INJECT
    private final FileService fileService;

    //CONSTRUCT
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    //ENDPOINTS
    @CrossOrigin
    @PostMapping("/upload/{userId}")
    public ResponseEntity<Object> imageUpload(@PathVariable Long userId, @RequestParam("file") MultipartFile file) throws IOException {
        try {
            //goed
            if (userId == null) {
                return ResponseEntity.badRequest().body("User ID cannot be null");
            }

            String urlOfFile = ServletUriComponentsBuilder.fromCurrentContextPath().path("/userImage/").path(Objects.requireNonNull(userId.toString())).toUriString();

            String fileName = fileService.storeFile(file, urlOfFile, userId);
            return ResponseEntity.ok("File uploaded successfully. URL: " + urlOfFile + ", FileName: " + fileName);
        } catch (Exception e) {
            // Log the exception details
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    //GET
    @GetMapping("/download/{userId}")
    public ResponseEntity<Object> downloadProfilePic(@PathVariable Long userId, HttpServletRequest request) {
        Resource resource = fileService.getFile(userId);
        MediaType contentType = MediaType.IMAGE_JPEG;
        return ResponseEntity.ok().contentType(contentType).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }

    //DELETE
    @DeleteMapping("/deleteprofilepic/{userId}")
    public ResponseEntity<Object> deleteProfilePic(@PathVariable Long userId) {

        if (fileService.deleteProfilePic(userId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
        if (fileService.deleteProfilePic(userId)) {
            return ResponseEntity.ok("Profile picture of user with ID : " + userId + " is deleted");
        } else {
            throw new BadRequestException("file does not exist in the system");
        }

    }

    @CrossOrigin
    @PostMapping("/uploadSong/{songId}")
    public ResponseEntity<Object> songUpload(@PathVariable Long songId, @RequestParam("file") MultipartFile file) throws IOException {
        try {
            //goed
            if (songId == null) {
                return ResponseEntity.badRequest().body("message");
            }

            String urlOfFile = ServletUriComponentsBuilder.fromCurrentContextPath().path("/songFile/").path(Objects.requireNonNull(songId.toString())).toUriString();

            String fileName = fileService.storeSong(file, urlOfFile, songId);
            return ResponseEntity.ok("File uploaded successfully. URL: " + urlOfFile + ", FileName: " + fileName);
        } catch (Exception e) {
            // Log the exception details
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }

    @GetMapping("/downloadSong/{songId}")
    public ResponseEntity<Object> downloadSong(@PathVariable Long songId, HttpServletRequest request) {
        Resource resource = fileService.getSong(songId);
        MediaType contentType = MediaType.parseMediaType("audio/mp3");
        return ResponseEntity.ok().contentType(contentType).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }

    @DeleteMapping("/deleteSong/{songId}")
    public ResponseEntity<Object> deleteSong(@PathVariable Long songId) {

        if (fileService.deleteSong(songId)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        }
        if (fileService.deleteSong(songId)) {
            return ResponseEntity.ok("");
        } else {
            throw new BadRequestException("file does not exist in the system");
        }

    }
}
