package nl.fsd.eindopdracht.soundwwise.services;

import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.ContributorInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.ProjectmanagerInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.UserOwnerInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.ContributorOutputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.ProjectmanagerOutputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.UserContributerOutputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.UserOwnerOutputDto;
import nl.fsd.eindopdracht.soundwwise.exceptions.RecordNotFoundException;
import nl.fsd.eindopdracht.soundwwise.models.Authority;
import nl.fsd.eindopdracht.soundwwise.models.User;
import nl.fsd.eindopdracht.soundwwise.repositories.UserRepository;
import nl.fsd.eindopdracht.soundwwise.util.RandomStringGenerator;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
    public ContributorOutputDto createContributor(ContributorInputDto contributorInputDto) {
        User contributor = new User();
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        UserServiceTransferMethod.transferContributorInputDtoToUser(contributor, contributorInputDto, passwordEncoder);
        contributor.setApikey(randomString);
        userRepository.save(contributor);
        contributor.addAuthority(new Authority(contributor.getId(), "ROLE_CONTRIBUTOR"));
        userRepository.save(contributor);
        return UserServiceTransferMethod.transferUserToContributorOutputDto(contributor);
    }

    public ProjectmanagerOutputDto createProjectmanager(ProjectmanagerInputDto projectmanagerInputDto) {
        User projectmanager = new User();
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        UserServiceTransferMethod.transferProjectmanagerInputDtoToUser(projectmanager, projectmanagerInputDto, passwordEncoder);
        projectmanager.setApikey(randomString);
        userRepository.save(projectmanager);
        projectmanager.addAuthority(new Authority(projectmanager.getId(), "ROLE_PROJECTMANAGER"));
        userRepository.save(projectmanager);
        return UserServiceTransferMethod.transferUserToProjectmanagerOutputDto(projectmanager);
    }

}
