package nl.fsd.eindopdracht.soundwwise.services;

import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.ProjectInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.ProjectOutputDto;
import nl.fsd.eindopdracht.soundwwise.exceptions.BadRequestException;
import nl.fsd.eindopdracht.soundwwise.exceptions.RecordNotFoundException;
import nl.fsd.eindopdracht.soundwwise.models.Authority;
import nl.fsd.eindopdracht.soundwwise.models.Project;
import nl.fsd.eindopdracht.soundwwise.models.User;
import nl.fsd.eindopdracht.soundwwise.repositories.AuthorityRepository;
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
    private final AuthorityRepository authorityRepository;

    //CONSTRUCT
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, AuthorityRepository authorityRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.authorityRepository = authorityRepository;
    }

   //SERVICES


    public ProjectOutputDto getProject(Long projectId) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RecordNotFoundException("user notfound"));
        return transferProjectToProjectOutputDto(project);
    }

    public ProjectOutputDto addContributorToProject(Long projectId, Long userId,ProjectInputDto projectInputDto){
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RecordNotFoundException(""));
        User contributor = userRepository.findById(userId).orElseThrow(() -> new RecordNotFoundException(""));

        Set<User> contributors = project.getContributors();
        contributors.add(contributor);

        project.setContributors(contributors);

        Authority newAuthority = new Authority(contributor.getId(), project.getProjectId(), "ROLE_CONTRIBUTOR");
        contributor.addAuthority(newAuthority);
        userRepository.save(contributor);

        projectRepository.save(transferProjectInputDtoToProject(projectInputDto, project));
        return transferProjectToProjectOutputDto(project);
    }

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

       Authority newAuthority = new Authority(projectOwner.getId(), project.getProjectId(), "ROLE_OWNER");
       projectOwner.addAuthority(newAuthority);
       userRepository.save(projectOwner);
       //Assign ROLE_OWNER
//       projectOwner.addAuthority(new Authority(projectOwner.getId(), "ROLE_OWNER"));
//       userService.addAuthorityForOwner(projectOwner.getId());

       return transferProjectToProjectOutputDto(project);
   }

    //Endpoint: /project/update/{projectId}
    public ProjectOutputDto updateProject(Long projectId, ProjectInputDto projectInputDto) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RecordNotFoundException(""));

//        userInputDto.userPassword = null;
        projectRepository.save(transferProjectInputDtoToProject(projectInputDto, project));
        return transferProjectToProjectOutputDto(project);
    }


    //Endpoint: /project/{projectId} //todo: only when owner role, nu is het werkbaar met USER.
    //De USER krijgt wel een extra rol bij het project, maar niet bij de user zelf in de response
    public void deleteProject(Long projectId) throws BadRequestException {
        try{
            Project project = projectRepository.findById(projectId)
                    .orElseThrow(() -> new RecordNotFoundException("Project not found with id: " + projectId));
//            authorityRepository.deleteByProjectId(projectId);
            projectRepository.delete(project);
        } catch (Exception e){
            throw new BadRequestException("");
        }
    }





   //////////////////////////////////////////////////////
   //TRANSFER METHODS
   //////////////////////////////////////////////////////

    public Project transferProjectInputDtoToProject(ProjectInputDto projectInputDto, Project project) {
        project.setProjectName(projectInputDto.projectName);
        return project;
    }

    public ProjectOutputDto transferProjectToProjectOutputDto(Project project) {
        ProjectOutputDto projectOutputDto = new ProjectOutputDto();
        projectOutputDto.projectId = project.getProjectId();
        projectOutputDto.projectName = project.getProjectName();
        projectOutputDto.projectCoverImage = project.getProjectCoverImage();
        projectOutputDto.projectOwnerId = project.getProjectOwner().getId();
        projectOutputDto.contributors = project.getContributors();
        return projectOutputDto;
    }
}