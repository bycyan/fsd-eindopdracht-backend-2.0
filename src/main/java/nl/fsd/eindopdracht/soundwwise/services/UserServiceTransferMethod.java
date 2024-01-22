package nl.fsd.eindopdracht.soundwwise.services;

import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.ContributorInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.ProjectmanagerInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.UserOwnerInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.ContributorOutputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.ProjectmanagerOutputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.UserOwnerOutputDto;
import nl.fsd.eindopdracht.soundwwise.models.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceTransferMethod {
    public static ContributorOutputDto transferUserToContributorOutputDto(User contributor) {
        ContributorOutputDto contributorOutputDto = new ContributorOutputDto();
        contributorOutputDto.id = contributor.getId();
        contributorOutputDto.firstName = contributor.getFirstName();
        contributorOutputDto.lastName = contributor.getLastName();
        contributorOutputDto.email = contributor.getEmail();
//        customerOutputDto.workshopOwner = customer.getWorkshopOwner();
//        customerOutputDto.enabled = customer.isEnabled();
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

    //PM
    public static ProjectmanagerOutputDto transferUserToProjectmanagerOutputDto(User projectmanager) {
        ProjectmanagerOutputDto projectmanagerOutputDto = new ProjectmanagerOutputDto();
        projectmanagerOutputDto.id = projectmanager.getId();
        projectmanagerOutputDto.firstName = projectmanager.getFirstName();
        projectmanagerOutputDto.lastName = projectmanager.getLastName();
        projectmanagerOutputDto.email = projectmanager.getEmail();
//        customerOutputDto.workshopOwner = customer.getWorkshopOwner();
//        customerOutputDto.enabled = customer.isEnabled();
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
    //

    ////////////////////
    public static UserOwnerOutputDto transferUserToCustomerOutputDto(User customer) {
        UserOwnerOutputDto customerOutputDto = new UserOwnerOutputDto();
        customerOutputDto.id = customer.getId();
        customerOutputDto.firstName = customer.getFirstName();
        customerOutputDto.lastName = customer.getLastName();
        customerOutputDto.email = customer.getEmail();
//        customerOutputDto.workshopOwner = customer.getWorkshopOwner();
//        customerOutputDto.enabled = customer.isEnabled();
        customerOutputDto.authorities = customer.getAuthorities();
//        customerOutputDto.profilePicUrl = customer.getProfilePicUrl();

        return customerOutputDto;
    }
    public static User transferCustomerInputDtoToUser(User customer, UserOwnerInputDto customerInputDto, PasswordEncoder passwordEncoder) {
        if (customerInputDto.password != null) {
            customer.setPassword(passwordEncoder.encode(customerInputDto.password));
        }

        customer.setFirstName(customerInputDto.firstName);
        customer.setLastName(customerInputDto.lastName);
        customer.setEmail(customerInputDto.email);
//        customer.setProjectOwner(customerInputDto.workshopOwner);

        return customer;
    }

    ////////////////////

//    public static UserCustomerOutputDto transferUserToCustomerOutputDto(User customer) {
//        UserCustomerOutputDto customerOutputDto = new UserCustomerOutputDto();
//        customerOutputDto.id = customer.getId();
//        customerOutputDto.firstName = customer.getFirstName();
//        customerOutputDto.lastName = customer.getLastName();
//        customerOutputDto.email = customer.getEmail();
//        customerOutputDto.workshopOwner = customer.getWorkshopOwner();
//        customerOutputDto.enabled = customer.isEnabled();
//        customerOutputDto.authorities = customer.getAuthorities();
//        customerOutputDto.profilePicUrl = customer.getProfilePicUrl();
//
//        return customerOutputDto;
//    }
//
//    public static UserWorkshopOwnerOutputDto transferUserToWorkshopOwnerOutputDto(User workshopOwner) {
//        UserWorkshopOwnerOutputDto workshopOwnerOutputDto = new UserWorkshopOwnerOutputDto();
//        workshopOwnerOutputDto.id = workshopOwner.getId();
//        workshopOwnerOutputDto.firstName = workshopOwner.getFirstName();
//        workshopOwnerOutputDto.lastName = workshopOwner.getLastName();
//        workshopOwnerOutputDto.email = workshopOwner.getEmail();
//        workshopOwnerOutputDto.companyName = workshopOwner.getCompanyName();
//        workshopOwnerOutputDto.kvkNumber = workshopOwner.getKvkNumber();
//        workshopOwnerOutputDto.vatNumber = workshopOwner.getVatNumber();
//        workshopOwnerOutputDto.workshopOwnerVerified = workshopOwner.getWorkshopOwnerVerified();
//        workshopOwnerOutputDto.workshopOwner = workshopOwner.getWorkshopOwner();
//        workshopOwnerOutputDto.averageRatingReviews = workshopOwner.calculateAverageRatingWorkshopOwner();
//        workshopOwnerOutputDto.authorities = workshopOwner.getAuthorities();
//        workshopOwnerOutputDto.profilePicUrl = workshopOwner.getProfilePicUrl();
//
//
//
//        return workshopOwnerOutputDto;
//    }

//    public static User transferWorkshopOwnerInputDtoToUser(User workshopOwner, UserWorkshopOwnerInputDto workshopOwnerInputDto, PasswordEncoder passwordEncoder) {
//        if (workshopOwnerInputDto.password != null) {
//            workshopOwner.setPassword(passwordEncoder.encode(workshopOwnerInputDto.password));
//        }
//
//        workshopOwner.setFirstName(workshopOwnerInputDto.firstName);
//        workshopOwner.setLastName(workshopOwnerInputDto.lastName);
//        workshopOwner.setEmail(workshopOwnerInputDto.email);
//        workshopOwner.setCompanyName(workshopOwnerInputDto.companyName);
//        workshopOwner.setKvkNumber(workshopOwnerInputDto.kvkNumber);
//        workshopOwner.setVatNumber(workshopOwnerInputDto.vatNumber);
//        workshopOwner.setWorkshopOwner(workshopOwnerInputDto.workshopOwner);
//        //workshopowner verified is not set in this transfer method, because verifying takes place via the put method containing verify boolean as a request parameter (and only admin can do that)
//        return workshopOwner;
//    }
//
//    public static User transferCustomerInputDtoToUser(User customer, UserCustomerInputDto customerInputDto, PasswordEncoder passwordEncoder) {
//        if (customerInputDto.password != null) {
//            customer.setPassword(passwordEncoder.encode(customerInputDto.password));
//        }
//
//        customer.setFirstName(customerInputDto.firstName);
//        customer.setLastName(customerInputDto.lastName);
//        customer.setEmail(customerInputDto.email);
//        customer.setWorkshopOwner(customerInputDto.workshopOwner);
//
//        return customer;
//    }
}
