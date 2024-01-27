package nl.fsd.eindopdracht.soundwwise.controllers;

import nl.fsd.eindopdracht.soundwwise.services.FileService;
import org.springframework.http.HttpStatus;
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
    @PostMapping("/uploadUserImage/{userId}")
    public ResponseEntity<Object> imageUpload(@PathVariable Long userId, @RequestParam("file") MultipartFile file) throws IOException {
        try {
            if (userId == null) {
                return ResponseEntity.badRequest().body("User ID cannot be null");
            }
            String url = ServletUriComponentsBuilder.fromCurrentContextPath().path("/userImage/").path(Objects.requireNonNull(userId.toString())).toUriString();

            String fileName = fileService.storeFile(file, url, userId);
            return ResponseEntity.ok("File uploaded successfully. URL: " + url + ", FileName: " + fileName);
        } catch (Exception e) {
            // Log the exception details
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error");
        }

    }

    //GET
    //DELETE
}
