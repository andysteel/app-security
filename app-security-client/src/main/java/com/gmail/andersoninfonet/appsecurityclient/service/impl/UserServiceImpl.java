package com.gmail.andersoninfonet.appsecurityclient.service.impl;

import java.time.LocalDateTime;
import java.util.Optional;

import com.gmail.andersoninfonet.appsecurityclient.dto.UserRequest;
import com.gmail.andersoninfonet.appsecurityclient.entity.User;
import com.gmail.andersoninfonet.appsecurityclient.entity.VerificationToken;
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

    public UserServiceImpl(UserRepository userRepository, 
        PasswordEncoder passwordEncoder, VerificationTokenRepository verificationTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.verificationTokenRepository = verificationTokenRepository;
    }

    @Override
    public User registerUser(UserRequest userDTO) {
        var user = new User(userDTO);
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

        Runnable notPresent = () -> {};

        verificationTokenRepository.findByToken(token).ifPresentOrElse(t -> {
            if(!LocalDateTime.now().isAfter(t.getExpirationTime())) {
                wrapperValid.isValid = true;
                var user = t.getUser();
                user.setEnabled(true);
                userRepository.save(user);
                verificationTokenRepository.delete(t);
            } else {
                verificationTokenRepository.delete(t);
            }
        }, notPresent);

        return wrapperValid.isValid;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
    
}
