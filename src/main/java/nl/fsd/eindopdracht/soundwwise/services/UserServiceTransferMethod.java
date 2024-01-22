package nl.fsd.eindopdracht.soundwwise.services;

import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.ContributorInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.ProjectmanagerInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.ContributorOutputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.ProjectmanagerOutputDto;
import nl.fsd.eindopdracht.soundwwise.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceTransferMethod {

    //CONTRIBUTOR
    public static ContributorOutputDto transferUserToContributorOutputDto(User contributor) {
        ContributorOutputDto contributorOutputDto = new ContributorOutputDto();
        contributorOutputDto.id = contributor.getId();
        contributorOutputDto.firstName = contributor.getFirstName();
        contributorOutputDto.lastName = contributor.getLastName();
        contributorOutputDto.email = contributor.getEmail();
        contributorOutputDto.authorities = contributor.getAuthorities();
        contributorOutputDto.profileImage = contributor.getProfileImage();
        return contributorOutputDto;
    }

    public static User transferContributorInputDtoToUser(User contributor, ContributorInputDto contributorInputDto, PasswordEncoder passwordEncoder) {
        if (contributorInputDto.password != null) {
            contributor.setPassword(passwordEncoder.encode(contributorInputDto.password));
        }
        contributor.setFirstName(contributorInputDto.firstName);
        contributor.setLastName(contributorInputDto.lastName);
        contributor.setEmail(contributorInputDto.email);
        contributor.setProfileImage(contributorInputDto.profileImage);

        return contributor;
    }

    //PROJECTMANAGER
    public static ProjectmanagerOutputDto transferUserToProjectmanagerOutputDto(User projectmanager) {
        ProjectmanagerOutputDto projectmanagerOutputDto = new ProjectmanagerOutputDto();
        projectmanagerOutputDto.id = projectmanager.getId();
        projectmanagerOutputDto.firstName = projectmanager.getFirstName();
        projectmanagerOutputDto.lastName = projectmanager.getLastName();
        projectmanagerOutputDto.email = projectmanager.getEmail();
        projectmanagerOutputDto.authorities = projectmanager.getAuthorities();
        projectmanagerOutputDto.profileImage = projectmanager.getProfileImage();
        return projectmanagerOutputDto;
    }

    public static User transferProjectmanagerInputDtoToUser(User projectmanager, ProjectmanagerInputDto projectmanagerInputDto, PasswordEncoder passwordEncoder) {
        if (projectmanagerInputDto.password != null) {
            projectmanager.setPassword(passwordEncoder.encode(projectmanagerInputDto.password));
        }
        projectmanager.setFirstName(projectmanagerInputDto.firstName);
        projectmanager.setLastName(projectmanagerInputDto.lastName);
        projectmanager.setEmail(projectmanagerInputDto.email);
        projectmanager.setProfileImage(projectmanagerInputDto.profileImage);

        return projectmanager;
    }
}
