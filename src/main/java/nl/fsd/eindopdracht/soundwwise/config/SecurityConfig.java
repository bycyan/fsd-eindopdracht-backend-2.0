package nl.fsd.eindopdracht.soundwwise.config;

import nl.fsd.eindopdracht.soundwwise.filter.JwtRequestFilter;
import nl.fsd.eindopdracht.soundwwise.services.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
    //Deze klasse stelt de beveiligingsconfiguratie in voor een Spring Security-beveiligingsconfiguratie. Het stelt ontwikkelaars in staat om de configure(HttpSecurity http) -methode te overschrijven om beveiligingsregels te definiëren.
    //todo: classes invullen
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

    //PasswordEncoderBean. Kan evt. hergebruiken in applicatie
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

                //authenticatie
                .requestMatchers("/contributor").permitAll()
                .requestMatchers("/projectmanager").permitAll()

                .requestMatchers("/authenticated").authenticated()

                .requestMatchers("/login").permitAll()
                //todo: user roles and permissions
                //All
                //Role: ProjectOwner
                .requestMatchers("/users/customer/**").authenticated() //get, put
                //Role: ProjectContributor

                .anyRequest().denyAll()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }
}
