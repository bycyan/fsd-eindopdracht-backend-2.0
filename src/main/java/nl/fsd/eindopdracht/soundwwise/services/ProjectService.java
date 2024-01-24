package nl.fsd.eindopdracht.soundwwise.services;

import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.ProjectInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.ProjectOutputDto;
import nl.fsd.eindopdracht.soundwwise.exceptions.RecordNotFoundException;
import nl.fsd.eindopdracht.soundwwise.models.Project;
import nl.fsd.eindopdracht.soundwwise.models.User;
import nl.fsd.eindopdracht.soundwwise.repositories.ProjectRepository;
import nl.fsd.eindopdracht.soundwwise.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ProjectService {

    //INJECT
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    //CONSTRUCT
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, UserService userService) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }

   //SERVICES

   //Endpoint: /project/new/{userId}
   public ProjectOutputDto createProject(Long userId, ProjectInputDto projectInputDto) {
       User projectOwner = userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException(""));

       Project project = new Project();
       project = transferProjectInputDtoToProject(projectInputDto, project);
       project.setProjectOwner(projectOwner);

       //todo: method add users as contributor
       Set<User> contributor = new HashSet<>();
       contributor.add(projectOwner);
       project.setContributors(contributor);
       //

       projectRepository.save(project);

       //Assign ROLE_OWNER
       userService.addAuthorityForOwner(projectOwner.getId());

       return transferProjectToProjectOutputDto(project);
   }



   //////////////////////////////////////////////////////
   //TRANSFER METHODS
   //////////////////////////////////////////////////////

    public Project transferProjectInputDtoToProject(ProjectInputDto projectInputDto, Project project) {
        project.setProjectName(projectInputDto.projectName);
        project.setProjectCoverImage(projectInputDto.projectCoverImage);
        return project;
    }

    public ProjectOutputDto transferProjectToProjectOutputDto(Project project) {
        ProjectOutputDto projectOutputDto = new ProjectOutputDto();
        projectOutputDto.id = project.getProjectId();
        projectOutputDto.projectName = project.getProjectName();
        projectOutputDto.projectCoverImage = project.getProjectCoverImage();
        projectOutputDto.projectOwnerId = project.getProjectOwner().getId();
        projectOutputDto.contributor = project.getContributors();
        return projectOutputDto;
    }
}