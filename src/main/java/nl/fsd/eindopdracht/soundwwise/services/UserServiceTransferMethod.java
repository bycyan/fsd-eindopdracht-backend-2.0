package nl.fsd.eindopdracht.soundwwise.services;

import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.UserInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.UserOutputDto;
import nl.fsd.eindopdracht.soundwwise.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceTransferMethod {

    //CONTRIBUTOR
    public static UserOutputDto transferUserToUserOutputDto(User user) {
        UserOutputDto userOutputDto = new UserOutputDto();
        userOutputDto.userId = user.getId();
        userOutputDto.userFirstName = user.getFirstName();
        userOutputDto.userLastName = user.getLastName();
        userOutputDto.userEmail = user.getEmail();
        userOutputDto.authorities = user.getAuthorities();
        userOutputDto.userProfileImage = user.getProfileImage();
        return userOutputDto;
    }

    public static User transferUserInputDtoToUser(User user, UserInputDto userInputDto, PasswordEncoder passwordEncoder) {
        if (userInputDto.userPassword != null) {
            user.setPassword(passwordEncoder.encode(userInputDto.userPassword));
        }
        user.setFirstName(userInputDto.userFirstName);
        user.setLastName(userInputDto.userLastName);
        user.setEmail(userInputDto.userEmail);
        user.setProfileImage(userInputDto.userProfileImage);

        return user;
    }
}
