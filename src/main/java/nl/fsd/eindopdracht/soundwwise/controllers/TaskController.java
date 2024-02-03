package nl.fsd.eindopdracht.soundwwise.controllers;

import jakarta.validation.Valid;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.TaskInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.TaskOutputDto;
import nl.fsd.eindopdracht.soundwwise.services.TaskService;
import nl.fsd.eindopdracht.soundwwise.util.FieldErrorHandling;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@CrossOrigin
@RestController
@RequestMapping("/task")
public class TaskController {

    //INJECT
    private final TaskService taskService;

    //CONSTRUCT
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    //POST
    @PostMapping("/add/{projectId}")
    public ResponseEntity<Object> addTask(
            @PathVariable Long projectId,
            @Valid @RequestBody TaskInputDto taskInputDto,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasFieldErrors()) {
                return ResponseEntity.badRequest().body(FieldErrorHandling.getErrorToStringHandling(bindingResult));
            }

            TaskOutputDto taskOutputDto = taskService.addTask(projectId, taskInputDto);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + taskOutputDto.taskId).toUriString());
            return ResponseEntity.created(uri).body(taskOutputDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during song creation.");
        }
    }

    //GET

    //PUT

    //DELETE
}
