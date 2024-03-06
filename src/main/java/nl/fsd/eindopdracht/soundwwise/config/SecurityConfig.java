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

                //OPEN ENDPOINTS
                .requestMatchers("/user/register").permitAll() //POST USER
                .requestMatchers("/login").permitAll() //LOGIN USER

                //AUTHENTICATED
                .requestMatchers("/authenticated").authenticated() //AUTHENTICATED RESPONSE BODY
                .requestMatchers("/user/{userId}").authenticated() //PUT, DELETE USER
                .requestMatchers("/user/users").authenticated() //GET ALL USERS
                .requestMatchers("/user/password_reset/{userEmail}").authenticated() //PUT USER PASSWORD
                .requestMatchers("/file/user_image/{userId}").authenticated() //POST, GET, DELETE USER IMAGE
                .requestMatchers("/file//project_image/{projectId}").authenticated() //POST, GET, DELETE USER IMAGE
                .requestMatchers("/project/{userId}").authenticated() //POST PROJECT

                //ROLE_CONTRIBUTOR || ROLE_OWNER
                .requestMatchers("/project/{projectId}").hasAnyRole("OWNER", "CONTRIBUTOR") //GET PROJECT
                .requestMatchers("/song/**").hasAnyRole("OWNER", "CONTRIBUTOR") //POST SONG

                //voor test de song file open gezet
//                .requestMatchers("/file/song/{songId}").hasAnyRole("OWNER", "CONTRIBUTOR") //POST GET DELETE SONG FILE
                .requestMatchers("/file/song/{songId}").permitAll() //POST GET DELETE SONG FILE

                .requestMatchers("/task/**").hasAnyRole("OWNER", "CONTRIBUTOR") //POST GET PUT DELETE TASK
                .requestMatchers("/message/**").hasAnyRole("OWNER", "CONTRIBUTOR") //POST GET PUT DELETE MESSAGE

                //ROLE_OWNER
                .requestMatchers("/file/project_image/{projectId}").hasRole("OWNER")
                .requestMatchers("/project/{projectId}").hasRole("OWNER") //PUT, DELETE PROJECT
                .requestMatchers("/project/contributor/{projectId}/{userId}").hasRole("OWNER") //PUT CONTRIBUTOR

                .anyRequest().denyAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
