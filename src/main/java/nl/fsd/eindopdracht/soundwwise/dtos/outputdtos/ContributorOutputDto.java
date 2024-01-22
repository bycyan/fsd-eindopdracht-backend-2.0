package nl.fsd.eindopdracht.soundwwise.dtos.outputdtos;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import nl.fsd.eindopdracht.soundwwise.models.Authority;

import java.util.Set;

@Getter
@Setter
public class ContributorOutputDto {
    public Long id;
    public String firstName;
    public String lastName;
    public String email;

//    public Boolean workshopOwner;

    public String profileImage;

    //security
    public Boolean enabled;
//    public String apikey;

    @JsonSerialize
    public Set<Authority> authorities;
}
