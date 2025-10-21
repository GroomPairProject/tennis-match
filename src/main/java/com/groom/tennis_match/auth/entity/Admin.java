package com.groom.tennis_match.auth.entity;

import com.groom.tennis_match.auth.AdminRole;
import com.groom.tennis_match.common.entity.BaseEntity;
import com.groom.tennis_match.common.entity.BaseTimeEntity;
import com.groom.tennis_match.domain.mypage.dto.AdminProfileUpdateRequestDTO;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collection;
import java.util.List;
import java.util.function.UnaryOperator;

@Getter
@Entity
@Table(name="admins")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString(exclude="password")
public class Admin extends BaseTimeEntity implements UserDetails {

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
    @Setter
    @Builder.Default
    private boolean isActive = true;

    @Column(length = 256)
    private String password;

    @Column
    @Builder.Default
    private short passwordMiss = 0;

    @Column(nullable = false)
    @Builder.Default
    private boolean isLock = false;

    @Column
    private String profileImgUrl;

    // Todo : enum type refactoring
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private AdminRole role;

    @Column(length = 50)
    private Long createdBy;

    @Column(length = 50)
    private Long updatedBy;

    public void applyProfileUpdate(AdminProfileUpdateRequestDTO dto,
                                   PasswordEncoder passwordEncoder) {
        if (dto.getName() != null) this.name = dto.getName();
        if (dto.getUsername() != null) this.username = dto.getUsername();
        if (dto.getPhone() != null) this.phone = dto.getPhone();
        if (dto.getEmail() != null) this.email = dto.getEmail();
        if (dto.getProfileImageUrl() != null) this.profileImgUrl = dto.getProfileImageUrl();

        // 인코더 미전달 시 암호화하지 않음.
        if (dto.getPassword() != null && !dto.getPassword().isBlank()) {
            this.password = passwordEncoder.encode(dto.getPassword());
        }
    }



    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(this.role);
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
