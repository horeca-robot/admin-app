package edu.fontys.horecarobot.adminappbackend.security;

import edu.fontys.horecarobot.adminappbackend.filters.JwtRequestFilter;
import edu.fontys.horecarobot.adminappbackend.services.SignInService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

    private final SignInService signInService;
    private final JwtRequestFilter jwtRequestFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(signInService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and()
                .csrf().disable().authorizeRequests()
                .antMatchers("/api/sign-in").permitAll() //API endpoint with /api/sign-in permits all users
                .antMatchers(HttpMethod.GET, "/api/info/**").permitAll() //Restaurant info is unauthorized
                .antMatchers("/api/password").permitAll() //API endpoint with /api/password permits all users
                .antMatchers("/v3/api-docs/**").permitAll() //Allows OpenAPI documentation access
                .antMatchers("/swagger-ui/**").permitAll() //Allows Swagger documentation access
                .anyRequest().authenticated() //Every other API endpoint is authenticated
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class); //Use custom filter
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
