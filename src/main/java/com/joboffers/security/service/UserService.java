package com.joboffers.security.service;

import com.joboffers.security.model.AppUser;
import com.joboffers.security.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@AllArgsConstructor
@Slf4j
public class UserService implements UserServiceInterface {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.info("User not found");
                    throw new UsernameNotFoundException("User not found");
                });
        return (userToLoad(user));
    }

    private User userToLoad(AppUser user) {
        return new User(user.getUsername(), passwordEncoder.encode(user.getPassword()), Collections.emptyList());
    }
}
