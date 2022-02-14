package com.gmail.andersoninfonet.appsecurityclient.service;

import java.util.Optional;

import com.gmail.andersoninfonet.appsecurityclient.dto.UserRequest;
import com.gmail.andersoninfonet.appsecurityclient.entity.PasswordResetToken;
import com.gmail.andersoninfonet.appsecurityclient.entity.User;

public interface UserService {

    User registerUser(UserRequest userDTO);

    void saveVerificationTokenForUser(User user, String token);

    boolean validateVerificationToken(String token);

    Optional<User> findUserByEmail(String email);

    void createPasswordResetTokenForUser(User user, String generateToken);

    Optional<PasswordResetToken> validatePasswordResetToken(String token);

    Optional<User> getUserByPasswordResetToken(String token);

    void changePassword(User user, String newPassword);

    void deletePasswordResetToken(PasswordResetToken passwordResetToken);

    boolean checkIfValidOldPassword(User user, String oldPassword);
    
}
