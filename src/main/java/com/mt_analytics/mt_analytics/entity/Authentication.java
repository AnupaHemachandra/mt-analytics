package com.mt_analytics.mt_analytics.entity;

import com.mt_analytics.mt_analytics.enums.LoginType;
import com.mt_analytics.mt_analytics.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Data
public class Authentication implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private List<Role> roles;
    @OneToOne
    private GeneralUser generalUser;
    private String email;
    @Column(length = 512)
    private String passwordHash;
    private String countryCode;
    @Column(length = 10)
    private String mobileNo;
    private String googleToken;
    private String facebookToken;
    private String tiktokToken;
    private LoginType loginType;
    private boolean verified;
    private boolean deleted;
    @CreationTimestamp
    private LocalDateTime createdOn;
    @CreationTimestamp
    private LocalDateTime updatedOn;
    private LocalDateTime deletedOn;
    private LocalDateTime lastLogin;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.name()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.passwordHash;
    }

    @Override
    public String getUsername() {
        return this.email;
    }
}
