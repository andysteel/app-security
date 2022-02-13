package com.gmail.andersoninfonet.appsecurityclient.repository;

import com.gmail.andersoninfonet.appsecurityclient.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
}
