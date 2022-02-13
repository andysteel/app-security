package com.gmail.andersoninfonet.appsecurityclient.service;

import com.gmail.andersoninfonet.appsecurityclient.dto.UserRequest;
import com.gmail.andersoninfonet.appsecurityclient.entity.User;

public interface UserService {

    User registerUser(UserRequest userDTO);

    void saveVerificationTokenForUser(User user, String token);

    boolean validateVerificationToken(String token);
    
}
