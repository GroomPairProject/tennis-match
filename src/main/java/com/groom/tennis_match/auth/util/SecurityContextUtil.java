package com.groom.tennis_match.auth.util;

import com.groom.tennis_match.auth.AdminRole;
import com.groom.tennis_match.auth.entity.Admin;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Security Context에서 현재 인증된 사용자 정보를 가져오는 유틸리티 클래스
 */
@Slf4j
@Component
public class SecurityContextUtil {

    /**
     * 현재 인증된 사용자 정보를 가져옵니다.
     * @return 현재 로그인한 Admin 객체
     * @throws IllegalStateException 인증되지 않은 경우
     */
    public Admin getCurrentAdmin() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (authentication == null || !authentication.isAuthenticated() || 
            "anonymousUser".equals(authentication.getPrincipal())) {
            throw new IllegalStateException("현재 인증된 사용자가 없습니다.");
        }

        if (authentication.getPrincipal() instanceof Admin) {
            return (Admin) authentication.getPrincipal();
        }

        throw new IllegalStateException("예상치 못한 인증 객체 타입입니다.");
    }

    /**
     * 현재 사용자의 권한을 가져옵니다.
     * @return 현재 사용자의 AdminRole
     * @throws IllegalStateException 인증되지 않은 경우
     */
    public AdminRole getCurrentUserRole() {
        Admin currentAdmin = getCurrentAdmin();
        return AdminRole.valueOf(currentAdmin.getRole());
    }

    /**
     * 현재 사용자의 사용자명을 가져옵니다.
     * @return 현재 사용자의 사용자명
     * @throws IllegalStateException 인증되지 않은 경우
     */
    public String getCurrentUsername() {
        return getCurrentAdmin().getUsername();
    }

    /**
     * 현재 사용자가 인증되어 있는지 확인합니다.
     * @return true: 인증됨, false: 인증되지 않음
     */
    public boolean isAuthenticated() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication != null && authentication.isAuthenticated() && 
               !"anonymousUser".equals(authentication.getPrincipal());
    }
}
