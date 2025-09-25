package com.groom.tennis_match.domain.mypage;

import com.groom.tennis_match.auth.entity.Admin;
import com.groom.tennis_match.auth.repository.AdminRepository;
import com.groom.tennis_match.domain.mypage.dto.AdminProfileDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {
  private final AdminRepository adminRepository;

  public AdminProfileDTO getAdminProfile(String username) {
    Admin currentUser = adminRepository.findByUsername(username)
            .orElseThrow(() -> {
              log.warn("AdminDetailsService - 사용자 없음: username={}", username);
              return new UsernameNotFoundException("User not found: " + username);
            });


    return AdminProfileDTO.builder()
            .username(username)
            .name(currentUser.getName())
            .phone(currentUser.getPhone())
            .email(currentUser.getEmail())
            .profileImageUrl(currentUser.getProfileImgUrl())
            .build();
  }
}
