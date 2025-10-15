package com.groom.tennis_match.domain.manage.repository;

import com.groom.tennis_match.auth.entity.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 관리자 목록 조회용 Repository
 * bean 이름 충돌 방지를 위해 "manageAdminRepository"로 명명
 */
@Repository("manageAdminRepository")
public interface AdminRepository extends JpaRepository<Admin, Long> {
    
    /**
     * 활성화된 관리자 목록 조회
     */
    List<Admin> findByIsActiveTrue();
    
    /**
     * 비활성화된 관리자 목록 조회
     */
    List<Admin> findByIsActiveFalse();
    
    /**
     * 권한별 관리자 목록 조회
     */
    List<Admin> findByRole(com.groom.tennis_match.auth.AdminRole role);
    
    /**
     * 사용자명으로 관리자 조회
     */
    java.util.Optional<Admin> findByUsername(String username);
    
    /**
     * 이메일로 관리자 조회
     */
    java.util.Optional<Admin> findByEmail(String email);
}

