package nl.fsd.eindopdracht.soundwwise.dtos.outputdtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationOutputDto {
    private final String jwt;

    public AuthenticationOutputDto(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
