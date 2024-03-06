package nl.fsd.eindopdracht.soundwwise.controllers;

import jakarta.validation.Valid;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.MessageInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.TaskInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.MessageOutputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.TaskOutputDto;
import nl.fsd.eindopdracht.soundwwise.services.MessageService;
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
@RequestMapping("/message")
public class MessageController {

    //INJECT
    private final MessageService messageService;

    //CONSTRUCT
    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    //////////////////////////////////////////////////////
    //ENDPOINTS
    //////////////////////////////////////////////////////

    //POST
    @PostMapping("/post/{projectId}/{userId}")
    public ResponseEntity<Object> postMessage(
            @PathVariable Long projectId,
            @PathVariable Long userId,
            @Valid @RequestBody MessageInputDto messageInputDto,
            BindingResult bindingResult) {
        try {
            if (bindingResult.hasFieldErrors()) {
                return ResponseEntity.badRequest().body(FieldErrorHandling.getErrorToStringHandling(bindingResult));
            }

            MessageOutputDto messageOutputDto = messageService.postMessage(projectId, messageInputDto, userId);
            URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + messageOutputDto.messageId).toUriString());
            return ResponseEntity.created(uri).body(messageOutputDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred during song creation.");
        }
    }

    //GET
    @GetMapping("/{messageId}")
    public ResponseEntity<MessageOutputDto> getMessage(@PathVariable Long messageId) {
        return new ResponseEntity<>(messageService.getMessage(messageId), HttpStatus.OK);
    }

    //PUT
    @PutMapping("/{messageId}")
    public ResponseEntity<Object> updateMessage(@PathVariable Long messageId, @Valid @RequestBody MessageInputDto messageInputDto, BindingResult bindingResult) {
        MessageOutputDto messageOutputDto = messageService.updateMessage(messageId, messageInputDto);
        return new ResponseEntity<>(messageOutputDto, HttpStatus.OK);
    }

    //DELETE
    @DeleteMapping("/{messageId}")
    public ResponseEntity<String> deleteMessage(@PathVariable Long messageId) throws BadRequestException {
        messageService.deleteMessage(messageId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
