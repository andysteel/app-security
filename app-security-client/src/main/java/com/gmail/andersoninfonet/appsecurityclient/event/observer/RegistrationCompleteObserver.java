package com.gmail.andersoninfonet.appsecurityclient.event.observer;

import java.util.UUID;

import com.gmail.andersoninfonet.appsecurityclient.event.RegistrationCompleteEvent;
import com.gmail.andersoninfonet.appsecurityclient.service.UserService;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RegistrationCompleteObserver implements ApplicationListener<RegistrationCompleteEvent> {

    private final UserService userService;

    public RegistrationCompleteObserver(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onApplicationEvent(RegistrationCompleteEvent event) {
        var user = event.getUser();
        var token = UUID.randomUUID().toString();
        userService.saveVerificationTokenForUser(user, token);

        var url = String.format("%s/verifyRegistration?token=%s", event.getApplicationUrl(), token);
        log.info("Click the link to verify your account: {}", url);
    }
    
}
