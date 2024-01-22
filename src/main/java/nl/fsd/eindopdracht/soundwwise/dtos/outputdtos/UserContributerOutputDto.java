package nl.fsd.eindopdracht.soundwwise.dtos.outputdtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import nl.fsd.eindopdracht.soundwwise.models.Authority;

import java.util.Set;

@Getter
@Setter
public class UserContributerOutputDto {
    //later verwijderen als security goed aangepast is
//    public String username;

    public Long id;

    public String firstName;
    public String lastName;

    public String email;
    public String companyName;
    public String kvkNumber;
    public String vatNumber;
    public Boolean workshopOwnerVerified;

    public Boolean workshopOwner;
    public Double averageRatingReviews;
    public String profilePicUrl;

    //security
    public Boolean enabled;
    public String apikey;

    @JsonSerialize
    public Set<Authority> authorities;
}
