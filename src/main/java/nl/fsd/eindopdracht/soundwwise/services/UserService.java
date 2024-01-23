package nl.fsd.eindopdracht.soundwwise.services;

//import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.ContributorInputDto;
//import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.ProjectmanagerInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.UserInputDto;
//import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.ContributorOutputDto;
//import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.ProjectmanagerOutputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.UserOutputDto;
import nl.fsd.eindopdracht.soundwwise.exceptions.RecordNotFoundException;
import nl.fsd.eindopdracht.soundwwise.models.Authority;
import nl.fsd.eindopdracht.soundwwise.models.User;
import nl.fsd.eindopdracht.soundwwise.repositories.UserRepository;
import nl.fsd.eindopdracht.soundwwise.util.RandomStringGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    public UserOutputDto createUser(UserInputDto userInputDto) {
        User user = new User();
        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
        UserServiceTransferMethod.transferUserInputDtoToUser(user, userInputDto, passwordEncoder);
        user.setApikey(randomString);
        userRepository.save(user);
        user.addAuthority(new Authority(user.getId(), "ROLE_CONTRIBUTOR"));
        userRepository.save(user);
        return UserServiceTransferMethod.transferUserToUserOutputDto(user);
    }

    //todo: laten vervallen
//    public ContributorOutputDto createContributor(ContributorInputDto contributorInputDto) {
//        User contributor = new User();
//        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
//        UserServiceTransferMethod.transferContributorInputDtoToUser(contributor, contributorInputDto, passwordEncoder);
//        contributor.setApikey(randomString);
//        userRepository.save(contributor);
//        contributor.addAuthority(new Authority(contributor.getId(), "ROLE_CONTRIBUTOR"));
//        userRepository.save(contributor);
//        return UserServiceTransferMethod.transferUserToContributorOutputDto(contributor);
//    }
//
//    public ProjectmanagerOutputDto createProjectmanager(ProjectmanagerInputDto projectmanagerInputDto) {
//        User projectmanager = new User();
//        String randomString = RandomStringGenerator.generateAlphaNumeric(20);
//        UserServiceTransferMethod.transferProjectmanagerInputDtoToUser(projectmanager, projectmanagerInputDto, passwordEncoder);
//        projectmanager.setApikey(randomString);
//        userRepository.save(projectmanager);
//        projectmanager.addAuthority(new Authority(projectmanager.getId(), "ROLE_PROJECTMANAGER"));
//        userRepository.save(projectmanager);
//        return UserServiceTransferMethod.transferUserToProjectmanagerOutputDto(projectmanager);
//    }
//
//    public ContributorOutputDto getContributorById(Long contributorId) {
//        User contributor = userRepository.findById(contributorId).orElseThrow(() -> new RecordNotFoundException("The user with ID " + contributorId + " doesn't exist."));
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        if (!CheckAuthorization.isAuthorized(customer, (Collection<GrantedAuthority>) authentication.getAuthorities(), authentication.getName())) {
////            throw new ForbiddenException("You're not allowed to view this profile.");
////        }
////        if (customer.getWorkshopOwner() == true) {
////            throw new RecordNotFoundException("The user with ID " + customerId + " is a workshop owner and not a customer.");
////        }
//        return UserServiceTransferMethod.transferUserToContributorOutputDto(contributor);
//    }


    //todo: tot hier laten vervallen

    public void addRoleProjectManagerAuthority(User user) {
        if (!user.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PROJECTMANAGER"))) {
            user.addAuthority(new Authority(user.getId(), "ROLE_PROJECTMANAGER"));
            userRepository.save(user);
        }
    }
}
