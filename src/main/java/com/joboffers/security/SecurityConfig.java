package com.joboffers.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.joboffers.security.filter.AuthenticationFilter;
import com.joboffers.security.filter.JwtAuthorizationFilter;
import com.joboffers.security.handler.FailureHandler;
import com.joboffers.security.handler.SuccessHandler;
import com.joboffers.security.service.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserService userService;
    private final SuccessHandler successHandler;
    private final PasswordEncoder passwordEncoder;
    private final ObjectMapper objectMapper;
    private final FailureHandler failureHandler;
    private final String secret;
    private final AuthenticationEntryPoint authenticationEntryPoint;

    public SecurityConfig(UserService userService, SuccessHandler successHandler, PasswordEncoder passwordEncoder,
                          ObjectMapper objectMapper, FailureHandler failureHandler,
                          @Value("${jwt.secret:secretkey123}") String secret,
                          AuthenticationEntryPoint authenticationEntryPoint) {
        this.userService = userService;
        this.successHandler = successHandler;
        this.passwordEncoder = passwordEncoder;
        this.objectMapper = objectMapper;
        this.failureHandler = failureHandler;
        this.secret = secret;
        this.authenticationEntryPoint = authenticationEntryPoint;
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
                .antMatchers("/register").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
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
