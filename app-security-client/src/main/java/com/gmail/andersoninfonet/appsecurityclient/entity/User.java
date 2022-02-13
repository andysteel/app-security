package com.gmail.andersoninfonet.appsecurityclient.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.gmail.andersoninfonet.appsecurityclient.dto.UserRequest;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "user_registration", name = "user")
@Data
@NoArgsConstructor
public class User implements Serializable {
    
    public User(UserRequest userDTO) {
        this.firstName = userDTO.firstName();
        this.lastName = userDTO.lastName();
        this.password = userDTO.password();
        this.email = userDTO.email();
        this.role = "USER";
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    @Column(length = 60)
    private String password;
    private String role;
    private boolean enabled = false;
}
