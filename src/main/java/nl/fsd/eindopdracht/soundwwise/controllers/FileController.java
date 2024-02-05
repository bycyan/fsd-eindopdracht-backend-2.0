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
@RequestMapping("/file")
public class FileController {

    //INJECT
    private final FileService fileService;

    //CONSTRUCT
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    //ENDPOINTS

    //POST
    @CrossOrigin
    @PostMapping("/user_image/{userId}")
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
    @GetMapping("/user_image/{userId}")
    public ResponseEntity<Object> downloadProfilePic(@PathVariable Long userId, HttpServletRequest request) {
        Resource resource = fileService.getFile(userId);
        MediaType contentType = MediaType.IMAGE_JPEG;
        return ResponseEntity.ok().contentType(contentType).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }

    //DELETE
    @DeleteMapping("/user_image/{userId}")
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
    @PostMapping("/song/{songId}")
    public ResponseEntity<Object> postSong(@PathVariable Long songId, @RequestParam("file") MultipartFile file) throws IOException {
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

    @GetMapping("/song/{songId}")
    public ResponseEntity<Object> getSong(@PathVariable Long songId, HttpServletRequest request) {
        Resource resource = fileService.getSong(songId);
        MediaType contentType = MediaType.parseMediaType("audio/mp3");
        return ResponseEntity.ok().contentType(contentType).header(HttpHeaders.CONTENT_DISPOSITION, "inline;fileName=" + resource.getFilename()).body(resource);
    }

    @DeleteMapping("/song/{songId}")
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

    //POST
    @CrossOrigin
    @PostMapping("/project_image/{projectId}")
    public ResponseEntity<Object> storeProjectImage(@PathVariable Long projectId, @RequestParam("file") MultipartFile file) throws IOException {
        try {
            if (projectId == null) {
                return ResponseEntity.badRequest().body("");
            }

            String urlOfFile = ServletUriComponentsBuilder.fromCurrentContextPath().path("/projectImage/").path(Objects.requireNonNull(projectId.toString())).toUriString();

            String fileName = fileService.storeProjectImage(file, urlOfFile, projectId);
            return ResponseEntity.ok("File uploaded successfully. URL: " + urlOfFile + ", FileName: " + fileName);
        } catch (Exception e) {
            // Log the exception details
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }
    }
}
