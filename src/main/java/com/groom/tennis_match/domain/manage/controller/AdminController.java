package com.groom.tennis_match.domain.manage.controller;

import com.groom.tennis_match.domain.manage.dto.AdminListResponseDTO;
import com.groom.tennis_match.domain.manage.service.AdminService;
import com.groom.tennis_match.common.constant.SuccessCode;
import com.groom.tennis_match.common.dto.ApiResponse;
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
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    /**
     * 모든 관리자 목록 조회
     * GET /api/admin/users
     */
    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<AdminListResponseDTO>>> getAllAdmins() {
        log.info("모든 관리자 목록 조회 API 호출");
        
        List<AdminListResponseDTO> admins = adminService.getAllAdmins();
        return ResponseEntity.ok(ApiResponse.success(admins, SuccessCode.ADMIN_READ_SUCCESS));
    }


    
    // ==================== 검색 기능 ====================
    
    /**
     * 사용자명으로 관리자 검색
     * GET /api/admin/users/search?username=admin
     */
    @GetMapping("/users/search")
    public ResponseEntity<ApiResponse<List<AdminListResponseDTO>>> searchAdminsByUsername(
            @RequestParam String username) {
        log.info("사용자명으로 관리자 검색 API 호출: username={}", username);
        
        List<AdminListResponseDTO> admins = adminService.searchAdminsByUsername(username);
        return ResponseEntity.ok(ApiResponse.success(admins, SuccessCode.ADMIN_READ_SUCCESS));
    }
    
    // ==================== 삭제 기능 ====================
    
    /**
     * 관리자 논리적 삭제
     * DELETE /api/admin/accounts/{accountId}
     */
    @DeleteMapping("/accounts/{accountId}")
    public ResponseEntity<ApiResponse<Void>> deleteAdmin(@PathVariable Long accountId) {
        log.info("관리자 논리적 삭제 API 호출: accountId={}", accountId);
        
        adminService.deleteAdmin(accountId);
        return ResponseEntity.ok(ApiResponse.success(SuccessCode.ADMIN_DELETE_SUCCESS));
    }
    
}

