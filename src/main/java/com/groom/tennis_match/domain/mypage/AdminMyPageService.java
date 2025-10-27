package com.groom.tennis_match.domain.mypage;

import com.groom.tennis_match.auth.entity.Admin;
import com.groom.tennis_match.auth.repository.AdminRepository;
import com.groom.tennis_match.common.constant.ErrorCode;
import com.groom.tennis_match.common.exception.BusinessException;
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
   * 당사자가 수정해야 합니다.
   * AuthenticationPrincipal을 통해 adminId를 가져오므로 동일 username을 검증하지 않아도 됩니다.
   * @param requestDTO - 수정할 사용자의 정보입니다.
   * @param requestUserId - 현재 사용자 정보 수정을 위해 접근한 사용자입니다.
   * @return AdminProfileDTO - 수정이 적용된 프로필을 반환합니다.
   */
  @Transactional
  public AdminProfileDTO updateAdminProfile(AdminProfileUpdateRequestDTO requestDTO, Long requestUserId) {
    Admin admin = adminRepository.findById(requestUserId)
            .orElseThrow(() -> {
              log.info("AdminDetailsService - 사용자 없음: UserId={}", requestUserId);
              return new UsernameNotFoundException("User not found: " + requestUserId);
            });

    String username = requestDTO.getUsername();

    if (username != null && adminRepository.findByUsername(username).isPresent()) {
      throw new BusinessException(ErrorCode.USER_UPDATE_FAILED, "username duplicated");
    }

    admin.applyProfileUpdate(requestDTO, passwordEncoder);
    adminRepository.save(admin);

    return getAdminProfile(admin.getUsername());

  }
}
