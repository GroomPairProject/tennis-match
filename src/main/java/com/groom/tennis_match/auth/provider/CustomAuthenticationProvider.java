package com.groom.tennis_match.auth.provider;

import com.groom.tennis_match.auth.entity.Admin;
import com.groom.tennis_match.auth.repository.AdminRepository;
import com.groom.tennis_match.auth.service.AdminDetailsService;
import com.groom.tennis_match.common.constant.ErrorCode;
import com.groom.tennis_match.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
  private final AdminDetailsService adminDetailsService;
  private final PasswordEncoder passwordEncoder;
  private final AdminRepository adminRepository;

  @Override
  public Authentication authenticate(Authentication authentication) throws AuthenticationException {

    log.debug("principal : {}", authentication.getPrincipal());

    String username=(String) authentication.getPrincipal();
    String password=(String) authentication.getCredentials();

    Admin user = adminDetailsService.loadUserByUsername(username);
    if(!passwordEncoder.matches(password, user.getPassword())) {
      user.increasePasswordMiss();
      user.setLock(user.getPasswordMiss() >= 5); // 5회 이상 넘길 경우 잠금

      adminRepository.save(user);
      throw new BusinessException(ErrorCode.LOGIN_FAILED);
    }

    if(!user.isActive()) {
      throw new BusinessException(ErrorCode.ACCOUNT_DISABLED);
    }

    if(user.isLock()) {
      throw new BusinessException(ErrorCode.ACCOUNT_LOCKED);
    }

    return new UsernamePasswordAuthenticationToken(user,
            null, // for security
            user.getAuthorities());
  }

  @Override
  public boolean supports(Class<?> authentication) {
    // AuthenticationProvider가 특정 유형의 인증 객체를 처리할 수 있는지 여부를 결정
    return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
  }

}
