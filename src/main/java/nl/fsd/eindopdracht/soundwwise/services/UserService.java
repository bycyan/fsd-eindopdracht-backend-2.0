package nl.fsd.eindopdracht.soundwwise.services;

import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.PasswordInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.UserInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.UserOutputDto;
import nl.fsd.eindopdracht.soundwwise.exceptions.RecordNotFoundException;
import nl.fsd.eindopdracht.soundwwise.models.Authority;
import nl.fsd.eindopdracht.soundwwise.models.User;
import nl.fsd.eindopdracht.soundwwise.repositories.UserRepository;
import nl.fsd.eindopdracht.soundwwise.util.RandomStringGenerator;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class UserService {

    //INJECT
    private final UserRepository userRepository;
    @Autowired
    @Lazy
    private PasswordEncoder passwordEncoder;

    //CONSTRUCT
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    //SERVICES

    //Endpoint: /user/{userId}
    public UserOutputDto getUserById(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException("user notfound"));
        //todo: check if authorised
        return UserServiceTransferMethod.transferUserToUserOutputDto(user);
    }

    //Endpoint: /user/register
    public UserOutputDto createUser(UserInputDto userInputDto) {
        User user = new User();
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        UserServiceTransferMethod.transferUserInputDtoToUser(user, userInputDto, passwordEncoder);
        user.setApikey(randomString);
        userRepository.save(user);
//        user.addAuthority(new Authority(user.getId() "ROLE_USER"));
//        userRepository.save(user);
        return UserServiceTransferMethod.transferUserToUserOutputDto(user);
    }

    //Endpoint: /user/update/{userId}
    public UserOutputDto updateUser(Long userId, UserInputDto userInputDto) {
            User updatedUser = userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException(""));

        userInputDto.userPassword = null;
        userRepository.save(UserServiceTransferMethod.transferUserInputDtoToUser(updatedUser, userInputDto, passwordEncoder));
        return UserServiceTransferMethod.transferUserToUserOutputDto(updatedUser);
    }

    public String updateUserPassword(String userEmail, PasswordInputDto passwordInputDto) {
        User user = userRepository.findByEmailIgnoreCase(userEmail);

        if (passwordInputDto.newPassword == null) {
            throw new IllegalArgumentException("New password cannot be null");
        }

        // Additional validation or authorization logic if needed

        user.setPassword(passwordEncoder.encode(passwordInputDto.newPassword));
        userRepository.save(user);

        return "The password has been updated successfully.";
    }

    //Endpoint: /user/delete/{userId}
    public void deleteUser(Long userId) throws BadRequestException {
        try {
            User user = userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException("The user with ID " + userId + " doesn't exist."));
            userRepository.delete(user);
        } catch (Exception e) {
            throw new BadRequestException("You can't remove this user before removing the other items.");
        }
    }

}
