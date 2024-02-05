package nl.fsd.eindopdracht.soundwwise.controllers;

import jakarta.validation.Valid;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.ProjectInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.UserInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.ProjectOutputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.UserOutputDto;
import nl.fsd.eindopdracht.soundwwise.services.ProjectService;
import nl.fsd.eindopdracht.soundwwise.util.FieldErrorHandling;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping("/project")
public class ProjectController {

    //INJECT
    private final ProjectService projectService;

    //CONSTRUCT
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    //ENDPOINTS

    //POST
    @PostMapping("/new/{userId}")
    public ResponseEntity<Object> createProject(@PathVariable Long userId, @Valid @RequestBody ProjectInputDto projectInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()){
            return ResponseEntity.badRequest().body(FieldErrorHandling.getErrorToStringHandling(bindingResult));
        }
        ProjectOutputDto projectOutputDto = projectService.createProject(userId, projectInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + projectOutputDto.id).toUriString());
        return ResponseEntity.created(uri).body(projectOutputDto);
    }

    //GET
    @GetMapping("/{projectId}")
    public ResponseEntity<ProjectOutputDto> getCustomerById(@PathVariable Long projectId) {
        return new ResponseEntity<>(projectService.getProject(projectId), HttpStatus.OK);
    }


    //PUT
    @PutMapping("/update/{projectId}")
    public ResponseEntity<Object> updateProject(@PathVariable Long projectId, @Valid @RequestBody ProjectInputDto projectInputDto, BindingResult bindingResult) {
        ProjectOutputDto projectOutputDto = projectService.updateProject(projectId, projectInputDto);
        return new ResponseEntity<>(projectOutputDto, HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/delete/{projectId}")
    public ResponseEntity<String> deleteProject(@PathVariable Long projectId) throws BadRequestException {
    projectService.deleteProject(projectId);
    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
