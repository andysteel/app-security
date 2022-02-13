package com.gmail.andersoninfonet.appsecurityclient.event.observer;

import com.gmail.andersoninfonet.appsecurityclient.event.PasswordResetCompleteEvent;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class PasswordResetCompleteObserver implements ApplicationListener<PasswordResetCompleteEvent>  {

    @Override
    public void onApplicationEvent(PasswordResetCompleteEvent event) {

        var message = String.format("token to reset password: %s", event.getToken());
        log.info(message);
    }
    
}
