package com.groom.tennis_match.domain.manage.controller;

import com.groom.tennis_match.domain.manage.dto.AdminListResponseDTO;
import com.groom.tennis_match.domain.manage.service.AdminService;
import com.groom.tennis_match.common.constant.SuccessCode;
import com.groom.tennis_match.common.dto.ApiResponse;
import com.groom.tennis_match.auth.AdminRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 관리자 목록 조회 컨트롤러
 * ADMIN 권한만 접근 가능
 */
@Slf4j
@RestController
@RequestMapping("/api/admin/users")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * 모든 관리자 목록 조회
     * GET /api/admin/users
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<AdminListResponseDTO>>> getAllAdmins() {
        log.info("모든 관리자 목록 조회 API 호출");
        
        List<AdminListResponseDTO> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(ApiResponse.success(admins, SuccessCode.ADMIN_READ_SUCCESS));
    }

    /**
     * 활성화된 관리자 목록 조회
     * GET /api/admin/users/active
     */
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<AdminListResponseDTO>>> getActiveAdmins() {
        log.info("활성화된 관리자 목록 조회 API 호출");
        
        List<AdminListResponseDTO> admins = adminService.getActiveAdmins();
        return ResponseEntity.ok(ApiResponse.success(admins, SuccessCode.ADMIN_READ_SUCCESS));
    }

    /**
     * 비활성화된 관리자 목록 조회
     * GET /api/admin/users/inactive
     */
    @GetMapping("/inactive")
    public ResponseEntity<ApiResponse<List<AdminListResponseDTO>>> getInactiveAdmins() {
        log.info("비활성화된 관리자 목록 조회 API 호출");
        
        List<AdminListResponseDTO> admins = adminService.getInactiveAdmins();
        return ResponseEntity.ok(ApiResponse.success(admins, SuccessCode.ADMIN_READ_SUCCESS));
    }

    /**
     * 권한별 관리자 목록 조회
     * GET /api/admin/users/role/{role}
     */
    @GetMapping("/role/{role}")
    public ResponseEntity<ApiResponse<List<AdminListResponseDTO>>> getAdminsByRole(@PathVariable String role) {
        log.info("권한별 관리자 목록 조회 API 호출: role={}", role);
        
        try {
            AdminRole adminRole = AdminRole.valueOf(role.toUpperCase());
            List<AdminListResponseDTO> admins = adminService.getAdminsByRole(adminRole);
            return ResponseEntity.ok(ApiResponse.success(admins, SuccessCode.ADMIN_READ_SUCCESS));
        } catch (IllegalArgumentException e) {
            log.warn("잘못된 권한 값: {}", role);
            throw new IllegalArgumentException("잘못된 권한 값입니다. (ADMIN, CHIEF_MANAGER, MANAGER, STAFF)");
        }
    }
}

