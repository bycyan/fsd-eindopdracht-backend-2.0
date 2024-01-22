package nl.fsd.eindopdracht.soundwwise.config;

import nl.fsd.eindopdracht.soundwwise.filter.JwtRequestFilter;
import nl.fsd.eindopdracht.soundwwise.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final CustomUserDetailsService customUserDetailService;
    private final JwtRequestFilter jwtRequestFilter;

    public SecurityConfig(CustomUserDetailsService customUserDetailService, JwtRequestFilter jwtRequestFilter){
        this.customUserDetailService = customUserDetailService;
        this.jwtRequestFilter = jwtRequestFilter;
    }

    //Authenticatie CustomUserDetailService en passwordEncoder
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception{
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(customUserDetailService)
                .passwordEncoder(passwordEncoder())
                .and()
                .build();
    }

    //PasswordEncoderBean. Kan ik hergebruiken in de applicatie
    @Bean
    public PasswordEncoder passwordEncoder(){return new BCryptPasswordEncoder();
    }

    //jwt authorization
    @Bean
    protected SecurityFilterChain filter(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .httpBasic().disable()
                .cors().and()
                .authorizeHttpRequests()

                //AUTH
                .requestMatchers("/login").permitAll()
                .requestMatchers("/authenticated").authenticated()

                //OPEN ENDPOINTS
                .requestMatchers(HttpMethod.POST, "/users/contributor").permitAll() //todo: verander naar register
                .requestMatchers(HttpMethod.POST, "/users/projectmanager").permitAll() //todo: deze vervalt wanneer ik alle users contributors maak en bij het aanmaken van een project een role toevoeg

                //AUTHENTICATED
                //eigen profiel aanpassen

                //project aanmaken > dan krijg je een extra rol
                .requestMatchers(HttpMethod.POST, "/projects/contributor/{contributorId}").authenticated()  //post

                //project wijzigen
                //items toevoegen aan het project c.q. muziek, messages, tasks

                //PROJECTMANAGER ROLE
                //put lijst met contributors dus: toevoegen en verwijderen
                //delete project

                .anyRequest().denyAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
