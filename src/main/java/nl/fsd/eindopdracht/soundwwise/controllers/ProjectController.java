package nl.fsd.eindopdracht.soundwwise.controllers;

import jakarta.validation.Valid;
//import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.ContributorInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.ProjectInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.ProjectOutputDto;
import nl.fsd.eindopdracht.soundwwise.services.ProjectService;
import nl.fsd.eindopdracht.soundwwise.util.FieldErrorHandling;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping("/projects")
public class ProjectController {

    //INJECT
    private final ProjectService projectService;

    //CONSTRUCT
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    //ENDPOINTS
    @PostMapping("/contributor/{contributorId}")
    public ResponseEntity<Object> createProject(@PathVariable Long contributorId, @Valid @RequestBody ProjectInputDto projectInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()){
            return ResponseEntity.badRequest().body(FieldErrorHandling.getErrorToStringHandling(bindingResult));
        }
        ProjectOutputDto projectOutputDto = projectService.createProject(contributorId, projectInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + projectOutputDto.id).toUriString());
        return ResponseEntity.created(uri).body(projectOutputDto);
    }

}
