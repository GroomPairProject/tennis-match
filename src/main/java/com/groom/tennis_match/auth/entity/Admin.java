package com.groom.tennis_match.auth.entity;

import com.groom.tennis_match.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Entity
@Table(name="admins")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude="password")
public class Admin extends BaseEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long adminId;

    @Column(length = 20, nullable = false)
    private String username;

    @Column(length = 20, nullable = false)
    private String name;

    @Column(length = 20, nullable = false)
    private String phone;

    @Column(length = 100, nullable = false)
    private String email;

    @Column
    @Builder.Default
    private boolean isActive = true;

    @Column(length = 256)
    private String password;

    @Column
    @Builder.Default
    private short passwordMiss = 0;

    @Column
    @Builder.Default
    private boolean isLock = false;

    @Column
    private String profileImageUrl;

    // Todo : enum type refactoring
    @Column(length = 20)
    @Builder.Default
    private String role = "ADMIN";


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
