package nl.fsd.eindopdracht.soundwwise.controllers;

import nl.fsd.eindopdracht.soundwwise.services.CustomUserDetailsService;
import nl.fsd.eindopdracht.soundwwise.util.JwtUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

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
}
