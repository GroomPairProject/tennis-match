package com.groom.tennis_match.domain.manage.service;

import com.groom.tennis_match.domain.manage.dto.AdminListResponseDTO;
import com.groom.tennis_match.domain.manage.repository.AdminRepository;
import com.groom.tennis_match.auth.entity.Admin;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 관리자 목록 조회 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminService {

    @Qualifier("manageAdminRepository")
    private final AdminRepository adminRepository;

    /**
     * 모든 관리자 목록 조회 (활성화된 관리자만)
     */
    public List<AdminListResponseDTO> getAllAdmins() {
        log.info("활성화된 관리자 목록 조회 요청");
        
        List<Admin> admins = adminRepository.findByIsActiveTrue();
        return admins.stream()
                .map(AdminListResponseDTO::from)
                .collect(Collectors.toList());
    }


    
    // ==================== 검색 기능 ====================
    
    /**
     * 사용자명으로 관리자 검색
     */
    public List<AdminListResponseDTO> searchAdminsByUsername(String username) {
        log.info("사용자명으로 관리자 검색 요청: {}", username);
        
        // 빈 값 검증
        if (username == null || username.trim().isEmpty()) {
            throw new IllegalArgumentException("검색어를 입력해주세요");
        }
        
        List<Admin> admins = adminRepository.findByUsernameContainingIgnoreCaseAndIsActiveTrue(username);
        return admins.stream()
                .map(AdminListResponseDTO::from)
                .collect(Collectors.toList());
    }
    
    // ==================== 삭제 기능 ====================
    
    /**
     * 관리자 논리적 삭제 (isActive를 false로 변경)
     */
    @Transactional
    public void deleteAdmin(Long adminId) {
        log.info("관리자 논리적 삭제 요청: adminId={}", adminId);
        
        Admin admin = adminRepository.findById(adminId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 관리자입니다."));
        
        // 이미 비활성화된 관리자인지 확인
        if (!admin.isActive()) {
            throw new IllegalArgumentException("이미 삭제된 관리자입니다.");
        }
        
        // 논리적 삭제 (isActive를 false로 변경)
        admin.setActive(false);
        adminRepository.save(admin);
        
        log.info("관리자 논리적 삭제 완료: adminId={}, username={}", adminId, admin.getUsername());
    }
    
}

