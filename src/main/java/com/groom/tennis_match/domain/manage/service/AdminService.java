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
     * 모든 관리자 목록 조회
     */
    public List<AdminListResponseDTO> getAllAdmins() {
        log.info("모든 관리자 목록 조회 요청");
        
        List<Admin> admins = adminRepository.findAll();
        return admins.stream()
                .map(AdminListResponseDTO::from)
                .collect(Collectors.toList());
    }

    /**
     * 활성화된 관리자 목록 조회
     */
    public List<AdminListResponseDTO> getActiveAdmins() {
        log.info("활성화된 관리자 목록 조회 요청");
        
        List<Admin> admins = adminRepository.findByIsActiveTrue();
        return admins.stream()
                .map(AdminListResponseDTO::from)
                .collect(Collectors.toList());
    }

    /**
     * 비활성화된 관리자 목록 조회
     */
    public List<AdminListResponseDTO> getInactiveAdmins() {
        log.info("비활성화된 관리자 목록 조회 요청");
        
        List<Admin> admins = adminRepository.findByIsActiveFalse();
        return admins.stream()
                .map(AdminListResponseDTO::from)
                .collect(Collectors.toList());
    }

    /**
     * 권한별 관리자 목록 조회
     */
    public List<AdminListResponseDTO> getAdminsByRole(com.groom.tennis_match.auth.AdminRole role) {
        log.info("권한별 관리자 목록 조회 요청: {}", role);
        
        List<Admin> admins = adminRepository.findByRole(role);
        return admins.stream()
                .map(AdminListResponseDTO::from)
                .collect(Collectors.toList());
    }
}

