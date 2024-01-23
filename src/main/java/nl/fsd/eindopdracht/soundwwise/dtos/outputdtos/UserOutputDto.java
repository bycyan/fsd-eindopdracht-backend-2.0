package nl.fsd.eindopdracht.soundwwise.dtos.outputdtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import nl.fsd.eindopdracht.soundwwise.models.Authority;

import java.util.Set;

@Getter
@Setter
public class UserOutputDto {

    //GENERAL VARS
    public Long userId;
    public String userFirstName;
    public String userLastName;
    public String userEmail;
    public String userProfileImage;

    //SECURITY
    public Boolean userEnabled;
    public String userApikey;

    @JsonSerialize
    public Set<Authority> authorities;
}
