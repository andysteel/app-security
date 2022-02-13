package com.gmail.andersoninfonet.appsecurityclient.repository;

import java.util.Optional;

import com.gmail.andersoninfonet.appsecurityclient.entity.PasswordResetToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    
    Optional<PasswordResetToken> findByToken(String token);
}
