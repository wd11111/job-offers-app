package com.joboffers.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joboffers.security.filter.AuthenticationFilter;
import com.joboffers.security.filter.JwtAuthorizationFilter;
import com.joboffers.security.handler.FailureHandler;
import com.joboffers.security.handler.SuccessHandler;
import com.joboffers.security.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final SuccessHandler successHandler;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    private final FailureHandler failureHandler;
    private final String secret;

    public SecurityConfig(UserService userService, SuccessHandler successHandler, PasswordEncoder passwordEncoder,
                          ObjectMapper objectMapper, FailureHandler failureHandler, @Value("${jwt.secret}") String secret) {
        this.userService = userService;
        this.successHandler = successHandler;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
        this.failureHandler = failureHandler;
        this.secret = secret;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/swagger-ui.html").permitAll()
                .antMatchers("/v2/api-docs").permitAll()
                .antMatchers("/webjars/**").permitAll()
                .antMatchers("/swagger-resources/**").permitAll()
                .antMatchers("/login").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilter(filter())
                .addFilter(new JwtAuthorizationFilter(authenticationManager(), userService, secret));
    }

    public AuthenticationFilter filter() throws Exception {
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(objectMapper);
        authenticationFilter.setAuthenticationManager(super.authenticationManager());
        authenticationFilter.setAuthenticationSuccessHandler(successHandler);
        authenticationFilter.setAuthenticationFailureHandler(failureHandler);
        return authenticationFilter;
    }
}
