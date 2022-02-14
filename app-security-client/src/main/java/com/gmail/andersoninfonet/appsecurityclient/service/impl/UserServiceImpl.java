package com.gmail.andersoninfonet.appsecurityclient.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import com.gmail.andersoninfonet.appsecurityclient.dto.UserRequest;
import com.gmail.andersoninfonet.appsecurityclient.entity.PasswordResetToken;
import com.gmail.andersoninfonet.appsecurityclient.entity.User;
import com.gmail.andersoninfonet.appsecurityclient.entity.VerificationToken;
import com.gmail.andersoninfonet.appsecurityclient.repository.PasswordResetTokenRepository;
import com.gmail.andersoninfonet.appsecurityclient.repository.UserRepository;
import com.gmail.andersoninfonet.appsecurityclient.repository.VerificationTokenRepository;
import com.gmail.andersoninfonet.appsecurityclient.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final VerificationTokenRepository verificationTokenRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;

    public UserServiceImpl(
        UserRepository userRepository, 
        PasswordEncoder passwordEncoder, 
        VerificationTokenRepository verificationTokenRepository,
        PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    @Override
    public User registerUser(UserRequest userRequest) {
        var user = new User(userRequest);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    @Override
    public void saveVerificationTokenForUser(User user, String token) {
        var verificationToken = new VerificationToken(user, token);
        
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public boolean validateVerificationToken(String token) {
        var wrapperValid = new Object(){boolean isValid = false;};

        verificationTokenRepository.findByToken(token).ifPresent(t -> {
            if(!LocalDateTime.now().isAfter(t.getExpirationTime())) {
                wrapperValid.isValid = true;
                var user = t.getUser();
                user.setEnabled(true);
                userRepository.save(user);
                verificationTokenRepository.delete(t);
            } else {
                verificationTokenRepository.delete(t);
            }
        });

        return wrapperValid.isValid;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void createPasswordResetTokenForUser(User user, String generateToken) {
        passwordResetTokenRepository.save(new PasswordResetToken(user, generateToken));
    }

    @Override
    public Optional<PasswordResetToken> validatePasswordResetToken(String token) {
        var wrapperResetToken = new Object(){PasswordResetToken resetToken = null;};

        passwordResetTokenRepository.findByToken(token).ifPresent(p -> {
            if(!LocalDateTime.now().isAfter(p.getExpirationTime())) {
                wrapperResetToken.resetToken = p;
                
            } else {
                deletePasswordResetToken(p);
            }
        });
        return Optional.ofNullable(wrapperResetToken.resetToken);
    }

    @Override
    public Optional<User> getUserByPasswordResetToken(String token) {
        
        return passwordResetTokenRepository.findByToken(token).map(PasswordResetToken::getUser);
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public void deletePasswordResetToken(PasswordResetToken passwordResetToken) {
        passwordResetTokenRepository.delete(passwordResetToken);
        
    }

    @Override
    public boolean checkIfValidOldPassword(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }
    
}
