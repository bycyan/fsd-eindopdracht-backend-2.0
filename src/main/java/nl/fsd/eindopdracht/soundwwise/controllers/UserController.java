package nl.fsd.eindopdracht.soundwwise.controllers;

import jakarta.validation.Valid;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.PasswordInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.UserInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.UserOutputDto;
import nl.fsd.eindopdracht.soundwwise.services.UserService;
import nl.fsd.eindopdracht.soundwwise.util.FieldErrorHandling;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/user")
public class UserController {

    //INJECT
    private final UserService userService;

    //CONSTRUCT
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //ENDPOINTS

    //POST
    @PostMapping("/register")
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserInputDto userInputDto, BindingResult bindingResult){
        UserOutputDto userOutputDto = userService.createUser(userInputDto);
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentRequest().path("/" + userOutputDto.userId).toUriString());
        return ResponseEntity.created(uri).body(userOutputDto);
    }

    //GET
    @GetMapping("/{userId}")
    public ResponseEntity<UserOutputDto> getCustomerById(@PathVariable Long userId) {
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserOutputDto>> getAllUsers() {
        List<UserOutputDto> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    //PUT
    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable Long userId, @Valid @RequestBody UserInputDto userInputDto, BindingResult bindingResult) {
        UserOutputDto userOutputDto = userService.updateUser(userId, userInputDto);
        return new ResponseEntity<>(userOutputDto, HttpStatus.OK);
    }

    @PutMapping("/password_reset/{userEmail}")
    public ResponseEntity<String> updatePassword(@PathVariable("userEmail") String userEmail,
                                                 @Valid @RequestBody PasswordInputDto passwordInputDto, BindingResult bindingResult) {
        if (bindingResult.hasFieldErrors()) {
            return ResponseEntity.badRequest().body(FieldErrorHandling.getErrorToStringHandling(bindingResult));
        }
        return new ResponseEntity<>(userService.updateUserPassword(userEmail, passwordInputDto), HttpStatus.ACCEPTED);
    }

    //DELETE
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) throws BadRequestException {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}