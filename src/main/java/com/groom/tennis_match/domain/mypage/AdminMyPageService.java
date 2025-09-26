package com.groom.tennis_match.domain.mypage;

import com.groom.tennis_match.auth.entity.Admin;
import com.groom.tennis_match.auth.repository.AdminRepository;
import com.groom.tennis_match.domain.mypage.dto.AdminProfileDTO;
import com.groom.tennis_match.domain.mypage.dto.AdminProfileUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminMyPageService {
  private final AdminRepository adminRepository;
  private final PasswordEncoder passwordEncoder;

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

  /**
   * 사용자 정보 수정 메서드입니다.
   * 사용자 아이디는 수정할 수 없으며, 당사자가 수정해야 합니다.
   * @param requestDTO - 수정할 사용자의 정보입니다.
   * @param requestUsername - 현재 사용자 정보 수정을 위해 접근한 사용자입니다.
   * @return
   */
  public AdminProfileDTO updateAdminProfile(AdminProfileUpdateRequestDTO requestDTO, String requestUsername) {
    String username = requestDTO.getUsername();
    Admin admin = adminRepository.findByUsername(username)
            .orElseThrow(() -> {
              log.warn("AdminDetailsService - 사용자 없음: username={}", username);
              return new UsernameNotFoundException("User not found: " + username);
            });

    admin.applyProfileUpdate(requestDTO, passwordEncoder);
    adminRepository.save(admin);

    return getAdminProfile(requestUsername);

  }
}
