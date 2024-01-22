package nl.fsd.eindopdracht.soundwwise.services;

import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.ProjectInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.ProjectOutputDto;
import nl.fsd.eindopdracht.soundwwise.exceptions.RecordNotFoundException;
import nl.fsd.eindopdracht.soundwwise.models.Authority;
import nl.fsd.eindopdracht.soundwwise.models.Project;
import nl.fsd.eindopdracht.soundwwise.models.User;
import nl.fsd.eindopdracht.soundwwise.repositories.ProjectRepository;
import nl.fsd.eindopdracht.soundwwise.repositories.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    //INJECT
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    //add project en geef user een user rol
    public ProjectOutputDto createProject(Long projectmanagerId, ProjectInputDto projectInputDto) {
        User projectmanager = userRepository.findById(projectmanagerId).orElseThrow(() -> new RecordNotFoundException("The workshop owner with ID " + projectmanagerId + " doesn't exist."));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

//        if (!CheckAuthorization.isAuthorized(projectmanager, (Collection<GrantedAuthority>) authentication.getAuthorities(), authentication.getName())){
//            throw new ForbiddenException("You're not allowed to create a workshops from this workshopowner's account.");
//        }
//        if (workshopOwner.getWorkshopOwnerVerified() != Boolean.TRUE || !workshopOwner.getWorkshopOwner()) {
//            throw new ForbiddenException("You're not allowed to create a new workshop, only a verified owner can publish.");
//        }

        // Add ROLE_PROJECTMANAGER authority to the user
        if (!projectmanager.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PROJECTMANAGER"))) {
            projectmanager.addAuthority(new Authority(projectmanager.getId(), "ROLE_PROJECTMANAGER"));
            userRepository.save(projectmanager); // Save the updated user with the new authority
        }

        Project project = new Project();
        project = transferProjectInputDtoToProject(projectInputDto, project);
        project.setProjectmanager(projectmanager);
        // when creating a new workshop, publishWorkshop, workshopVerified and feedbackAdmin need to get default values.
//        workshop.setPublishWorkshop(null);
//        workshop.setWorkshopVerified(null);
//        workshop.setFeedbackAdmin(null);
        projectRepository.save(project);
//        contributor.addAuthority(new Authority(projectmanager.getId(), "ROLE_PROJECTMANAGER"));
        return transferProjectToProjectOutputDto(project);
    }

    public Project transferProjectInputDtoToProject(ProjectInputDto projectInputDto, Project project) {
        project.setTitle(projectInputDto.title);
        return project;
    }

    public ProjectOutputDto transferProjectToProjectOutputDto(Project project) {
        ProjectOutputDto projectOutputDto = new ProjectOutputDto();
        projectOutputDto.id = project.getId();
        projectOutputDto.title = project.getTitle();
        return projectOutputDto;
    }
}
