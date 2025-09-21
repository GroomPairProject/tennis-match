package com.groom.tennis_match.auth.service;

import com.groom.tennis_match.auth.entity.Admin;
import com.groom.tennis_match.auth.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class AdminDetailsService  implements UserDetailsService {

  private final AdminRepository adminRepository;

  @Override
  public Admin loadUserByUsername(String username) throws UsernameNotFoundException {
    log.info("AdminDetailsService loadUserByUsername 호출: username={}", username);

    Admin user = adminRepository.findByUsername(username)
            .orElseThrow(() -> {
              log.warn("AdminDetailsService - 사용자 없음: username={}", username);
              return new UsernameNotFoundException("User not found: " + username);
            });

    log.info("AdminDetailsService - 사용자 로드 성공: username={}, authorities={}",
            username, user.getAuthorities());
    return user;
  }
}
