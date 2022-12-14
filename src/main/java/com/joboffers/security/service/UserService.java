package com.joboffers.security.service;

import com.joboffers.security.exception.UserDuplicateException;
import com.joboffers.security.model.AppUser;
import com.joboffers.security.model.RegisterCredentials;
import com.joboffers.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private static final String UNAUTHORIZED = "UNAUTHORIZED ";
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    throw new UsernameNotFoundException(UNAUTHORIZED);
                });
        return new User(user.getUsername(), user.getPassword(), Collections.emptyList());
    }

    public void register(RegisterCredentials registerCredentials) {
        String encodedPassword = passwordEncoder.encode(registerCredentials.getPassword());
        AppUser user = new AppUser(null, registerCredentials.getUsername(), encodedPassword);
        try {
            userRepository.save(user);
        } catch (DuplicateKeyException e) {
            throw new UserDuplicateException(user.getUsername());
        }
    }
}
