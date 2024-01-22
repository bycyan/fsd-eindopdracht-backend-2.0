package nl.fsd.eindopdracht.soundwwise.controllers;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.ContributorInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.ProjectmanagerInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.ContributorOutputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.ProjectmanagerOutputDto;
import nl.fsd.eindopdracht.soundwwise.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping("/users")
public class UserController {

    //INJECT
    private final UserService userService;

    //CONSTRUCT
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //ENDPOINTS

    //GET

    //@GetMapping("/contributor/{contributorId}") userService.getContributorById(contributorId)
    @GetMapping("/contributor/{contributorId}")
    @Transactional
    public ResponseEntity<ContributorOutputDto> getContributorById(@PathVariable Long contributorId) {
        return new ResponseEntity<>(userService.getContributorById(contributorId), HttpStatus.OK);
    }

    //@GetMapping("/projectmanagers/{projectmanagerId}") userService.getProjectmanagerById(projectmanagerId)

    //POST

    //@PostMapping("/contributor") userService.createContributor(contributorInputDto)
    @PostMapping("/contributor")
    public ResponseEntity<Object> createContributor(@Valid @RequestBody ContributorInputDto contributorInputDto, BindingResult bindingResult){
        ContributorOutputDto contributorOutputDto = userService.createContributor(contributorInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + contributorOutputDto.id).toUriString());
        return ResponseEntity.created(uri).body(contributorOutputDto);
    }

    //@PostMapping("/projectmanager") userService.createProjectmanager(projectmanagerInputDto)
    @PostMapping("/projectmanager")
    public ResponseEntity<Object> createProjectmanager(@Valid @RequestBody ProjectmanagerInputDto projectmanagerInputDto, BindingResult bindingResult){
        ProjectmanagerOutputDto projectmanagerOutputDto = userService.createProjectmanager(projectmanagerInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + projectmanagerOutputDto.id).toUriString());
        return ResponseEntity.created(uri).body(projectmanagerOutputDto);
    }

    //PUT
    //@PutMapping("/contributor/{contributorId}") userService.updateContributor(contributorId, contributorInputDto)
    //@PutMapping("/projectmanager/{projectmanagerId}") userService.updateProjectmanager(projectmanagerId, projectmanagerInputDto)
    //@PutMapping ("/passwordreset/{email}") userService.updatePassword(email, passwordInputDto)

    //DELETE
    //@DeleteMapping("contributor/{userId}")  userService.deleteContributorFromProject(contributorId)
}
