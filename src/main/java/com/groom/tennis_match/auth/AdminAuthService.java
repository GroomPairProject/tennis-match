package com.groom.tennis_match.auth;

import com.groom.tennis_match.auth.dto.AdminSignUpDTO;
import com.groom.tennis_match.auth.entity.Admin;
import com.groom.tennis_match.auth.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AdminAuthService {

    private AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 계정 발급 메소드입니다.
     * 매개변수가 없을 시 최고 권한으로 반환합니다.
     * @return 계정
     */
    public AdminSignUpDTO registerAdmin() {
        Admin admin = Admin.builder()
                .username("admin")
                .password(passwordEncoder.encode("admin"))
                .email("admin@admin.com")
                .name("최고 권한")
                .role("ADMIN")
                .build();


        Admin savedAdmin = adminRepository.save(admin);

        AdminSignUpDTO resultAccount = AdminSignUpDTO.builder()
                .username(savedAdmin.getUsername())
                .name(savedAdmin.getName())
                .email(savedAdmin.getEmail())
                .phone(savedAdmin.getPhone())
                .build();
    // conversion 과정 필요

        return resultAccount;

    }

}
