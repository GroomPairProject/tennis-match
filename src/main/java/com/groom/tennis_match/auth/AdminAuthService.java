package com.groom.tennis_match.auth;

import com.groom.tennis_match.auth.dto.AdminAccountCreateDTO;
import com.groom.tennis_match.auth.dto.AdminAccountRegisterDTO;
import com.groom.tennis_match.auth.entity.Admin;
import com.groom.tennis_match.auth.repository.AdminRepository;
import com.groom.tennis_match.common.constant.ErrorCode;
import com.groom.tennis_match.common.exception.BusinessException;
import com.groom.tennis_match.auth.util.PasswordUtil;
import com.groom.tennis_match.auth.util.SecurityContextUtil;
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

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityContextUtil securityContextUtil;
    private final PasswordUtil passwordUtil;

    /**
     * 계정 발급 메소드입니다.
     * 자신의 권한 이상으로 생성할 수 없습니다.
     * @param createDTO 계정 생성 정보
     * @return 발급된 계정 정보 (아이디, 임시 비밀번호 포함)
     */
    public AdminAccountRegisterDTO registerAccount(AdminAccountCreateDTO createDTO) {
        // 현재 사용자 권한 확인
        AdminRole currentUserRole = securityContextUtil.getCurrentUserRole();
        String currentUsername = securityContextUtil.getCurrentUsername();
        
        // 권한 검증: 자신의 권한 이상으로 계정을 생성할 수 없음
        if (!currentUserRole.canManage(createDTO.getRole())) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE, "권한이 부족합니다. 자신의 권한 이상으로 계정을 생성할 수 없습니다.");
        }
        
        // 임시 비밀번호 생성
        String temporaryPassword = passwordUtil.generateTemporaryPassword();
        String encodedPassword = passwordEncoder.encode(temporaryPassword);
        
        // 사용자명 생성 (이메일 기반 또는 다른 로직)
        String username = generateUsername(createDTO.getEmail());
        
        // Admin 엔티티 생성
        Admin admin = Admin.builder()
                .username(username)
                .password(encodedPassword)
                .email(createDTO.getEmail())
                .name(createDTO.getName())
                .role(createDTO.getRole().name())
                .phone(createDTO.getPhone())
                .build();

        Admin savedAdmin = adminRepository.save(admin);
        
        // 임시 비밀번호 생성 로그
        passwordUtil.logPasswordGeneration(username);
        
        /*
         * TODO: 보안 정책 관련 이슈 처리 예정
         * 1. 이메일로 계정 정보 전송 및 비밀번호 변경 링크 발송
         *    - 발급된 아이디와 임시 비밀번호를 이메일로 안전하게 전송
         *    - 초기 로그인 후 비밀번호 변경 강제
         *    - 비밀번호 변경 링크 유효시간 설정 (예: 24시간)
         * 
         * 2. 회원 비밀번호 정보 변경 보안 정책 적용
         *    - 임시 비밀번호 사용 기간 제한 (예: 7일)
         *    - 비밀번호 변경 히스토리 관리
         *    - 의심스러운 로그인 시도 감지 및 알림
         * 
         * 3. 계정 발급 시 추가 보안 조치
         *    - 발급자 권한 로그 기록
         *    - 계정 발급 이력 추적
         *    - 발급된 계정의 초기 상태 관리
         */
        
        return AdminAccountRegisterDTO.builder()
                .username(savedAdmin.getUsername())
                .temporaryPassword(temporaryPassword) // 발급된 임시 비밀번호 노출
                .name(savedAdmin.getName())
                .email(savedAdmin.getEmail())
                .phone(savedAdmin.getPhone())
                .role(createDTO.getRole())
                .createdBy(currentUsername)
                .build();
    }
    
    /**
     * 사용자명 생성 로직
     * @param email 이메일 주소
     * @return 생성된 사용자명
     */
    private String generateUsername(String email) {
        // 이메일의 @ 앞 부분을 사용자명으로 사용
        String baseUsername = email.split("@")[0];
        
        // 중복 체크 및 사용자명 생성
        String username = baseUsername;
        int counter = 1;

        if (adminRepository.existsByUsername(username)) {
            throw new BusinessException(ErrorCode.USER_CREATE_FAILED, "중복된 사용자 이름입니다.");
        }
        
        return username;
    }

}
