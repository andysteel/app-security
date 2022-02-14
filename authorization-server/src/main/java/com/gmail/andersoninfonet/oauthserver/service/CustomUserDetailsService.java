package com.gmail.andersoninfonet.oauthserver.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.gmail.andersoninfonet.oauthserver.repository.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found !"));
        return new User(user.getEmail(), user.getPassword(), user.isEnabled(), true, true, true, getAuthorities(List.of(user.getRole())));
    }
    
    private Collection<? extends GrantedAuthority> getAuthorities(List<String> roles) {
        var authorities = new ArrayList<GrantedAuthority>();

        roles.forEach(r -> authorities.add(new SimpleGrantedAuthority(r)));

        return authorities;
    } 

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(11);
    }
}
