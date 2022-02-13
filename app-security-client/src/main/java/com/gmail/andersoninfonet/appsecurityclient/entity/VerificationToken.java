package com.gmail.andersoninfonet.appsecurityclient.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(schema = "user_registration", name = "verification_token")
@Data
@NoArgsConstructor
public class VerificationToken implements Serializable {

    private static final int EXPIRATION_TIME = 10;

    public VerificationToken(User user, String token) {
        this.token = token;
        this.user = user;
        this.expirationTime = calculateExpirationTime();
    }

    public VerificationToken(String token) {
        this.token = token;
        this.expirationTime = calculateExpirationTime();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private LocalDateTime expirationTime;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false, 
    foreignKey = @ForeignKey(name = "FK_USER"))
    private User user;

    private LocalDateTime calculateExpirationTime() {
        return LocalDateTime.now().plusMinutes(EXPIRATION_TIME);
    }
}
