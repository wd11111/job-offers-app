package com.joboffers.mongock;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import com.joboffers.security.model.AppUser;
import com.joboffers.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@ChangeLog
@Slf4j
public class DatabaseChangeLog {

    @ChangeSet(order = "001", id = "addUser", author = "wd")
    public void addUser(UserRepository userRepository) {
        AppUser appUser = new AppUser();
        appUser.setUsername("admin");
        appUser.setPassword("$2a$10$cInjKQvFxR4KCxn/aRtI9.0SkkaZD0wnTsMIwfrBymJBtpqAdsIM6");
        userRepository.insert(appUser);
        log.info(String.format("Added user: %s", appUser.getUsername()));
    }
}


