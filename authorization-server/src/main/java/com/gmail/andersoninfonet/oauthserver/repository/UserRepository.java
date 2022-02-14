package com.gmail.andersoninfonet.oauthserver.repository;

import java.util.Optional;

import com.gmail.andersoninfonet.oauthserver.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);
    
}
