package com.gmail.andersoninfonet.appsecurityclient.dto;

import com.gmail.andersoninfonet.appsecurityclient.entity.User;

public record UserResponse(Long id, String firstName, String lastName, 
String email) {
    public UserResponse(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getEmail());
    }
}
