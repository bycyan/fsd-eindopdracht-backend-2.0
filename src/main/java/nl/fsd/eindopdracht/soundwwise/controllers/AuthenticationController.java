package nl.fsd.eindopdracht.soundwwise.controllers;

import nl.fsd.eindopdracht.soundwwise.dtos.inputdtos.AuthenticationInputDto;
import nl.fsd.eindopdracht.soundwwise.dtos.outputdtos.AuthenticationOutputDto;
import nl.fsd.eindopdracht.soundwwise.services.CustomUserDetailsService;
import nl.fsd.eindopdracht.soundwwise.util.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
public class AuthenticationController {

    //INJECT
    private final CustomUserDetailsService userDetailsService;
    private final JwtUtil jwtUtil;
    private AuthenticationManager authenticationManager;

    //CONSTRUCTOR
    public AuthenticationController(CustomUserDetailsService userDetailsService, JwtUtil jwtUtil, AuthenticationManager authenticationManager) {
        this.userDetailsService = userDetailsService;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
    }

    @GetMapping(value = "/authenticated")
    public ResponseEntity<Object> authenticated(Authentication authentication, Principal principal) {
        return ResponseEntity.ok().body(principal);
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> signIn(@RequestBody AuthenticationInputDto authenticationInputDto) {

        String email = authenticationInputDto.getEmail();
        String password = authenticationInputDto.getPassword();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(email, password)
            );
        }
        catch (Exception e) {
            throw new BadCredentialsException("Incorrect username or password", e);
        }

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(email);

        final String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationOutputDto(jwt));
    }
}
