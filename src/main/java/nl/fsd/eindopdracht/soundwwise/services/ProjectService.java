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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProjectService {

    //INJECT
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private UserService userService;

    //CONSTRUCT
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;

        this.userRepository = userRepository;
    }

   //SERVICES
   public ProjectOutputDto createProject(Long projectmanagerId, ProjectInputDto projectInputDto) {
       User projectmanager = userRepository.findById(projectmanagerId).orElseThrow(() -> new RecordNotFoundException("The workshop owner with ID " + projectmanagerId + " doesn't exist."));
       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

       Project project = new Project();
       project = transferProjectInputDtoToProject(projectInputDto, project);
       project.setProjectmanager(projectmanager);

       Set<User> contributor = new HashSet<>();
       contributor.add(projectmanager);
       project.setContributors(contributor);

       projectRepository.save(project);
       return transferProjectToProjectOutputDto(project);
   }
//    public ProjectOutputDto createProject(Long projectmanagerId, ProjectInputDto projectInputDto) {
//        User projectmanager = userRepository.findById(projectmanagerId).orElseThrow(() -> new RecordNotFoundException("The workshop owner with ID " + projectmanagerId + " doesn't exist."));
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//
//        // Add ROLE_PROJECTMANAGER authority to the user
//        if (!projectmanager.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_PROJECTMANAGER"))) {
//            projectmanager.addAuthority(new Authority(projectmanager.getId(), "ROLE_PROJECTMANAGER"));
//            userRepository.save(projectmanager); // Save the updated user with the new authority
//        }
//
//        userService.addRoleProjectManagerAuthority(projectmanager);
//
//        Project project = new Project();
//        project = transferProjectInputDtoToProject(projectInputDto, project);
////        project.setProjectmanager(projectmanager);
//
//        //Add the user that initialises the project to the contributor list
////        Set<User> contributor = new HashSet<>();
////        contributor.add(projectmanager);
////        project.setContributors(contributor);
//
//        projectRepository.save(project);
//        return transferProjectToProjectOutputDto(project);
//    }

    //// Add other contributors based on their user IDs
//        for (Long contributorId : projectInputDto.getContributorIds()) {
//            User contributor = userRepository.findById(contributorId)
//                    .orElseThrow(() -> new RecordNotFoundException("Contributor with ID " + contributorId + " not found."));
//            contributors.add(contributor);
//        }

    //TRANSFER METHODS
    public Project transferProjectInputDtoToProject(ProjectInputDto projectInputDto, Project project) {
        project.setProjectName(projectInputDto.projectName);
        return project;
    }

    public ProjectOutputDto transferProjectToProjectOutputDto(Project project) {
        ProjectOutputDto projectOutputDto = new ProjectOutputDto();
        projectOutputDto.id = project.getId();
        projectOutputDto.projectName = project.getProjectName();
        return projectOutputDto;
    }
}
