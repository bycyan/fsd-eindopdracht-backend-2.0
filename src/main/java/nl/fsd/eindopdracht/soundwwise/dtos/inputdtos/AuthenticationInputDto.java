package nl.fsd.eindopdracht.soundwwise.dtos.inputdtos;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthenticationInputDto {
    public String authEmail;
    public String authPassword;

    public AuthenticationInputDto() {
    }
    public AuthenticationInputDto(String email, String password) {
        this.authEmail = email;
        this.authPassword = password;
    }
}
