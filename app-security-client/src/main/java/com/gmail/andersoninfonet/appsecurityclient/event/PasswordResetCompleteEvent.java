package com.gmail.andersoninfonet.appsecurityclient.event;

import com.gmail.andersoninfonet.appsecurityclient.entity.User;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetCompleteEvent extends ApplicationEvent {

    private String token;
    
    public PasswordResetCompleteEvent(User user, String token) {
        super(user);
        this.token = token;
    }
    
}
