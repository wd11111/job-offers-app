package com.joboffers.mongock;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.joboffers.security.model.AppUser;
import com.joboffers.security.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@ChangeLog
@RequiredArgsConstructor
public class DatabaseChangeLog {

    private final PasswordEncoder passwordEncoder;

    @ChangeSet(order = "001", id = "addUser", author = "wd")
    public void addUser(UserRepository userRepository) {
        AppUser appUser = new AppUser();
        appUser.setUsername("admin");
        appUser.setPassword(passwordEncoder.encode("password"));
        userRepository.insert(appUser);
    }
}


