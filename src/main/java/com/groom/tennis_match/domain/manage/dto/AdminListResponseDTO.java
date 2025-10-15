package com.groom.tennis_match.domain.manage.dto;

import com.groom.tennis_match.auth.entity.Admin;
import com.groom.tennis_match.auth.AdminRole;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 관리자 목록 조회 응답 DTO
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminListResponseDTO {
    
    /** 관리자 ID */
    private Long adminId;
    
    /** 사용자명 */
    private String username;
    
    /** 이름 */
    private String name;
    
    /** 전화번호 */
    private String phone;
    
    /** 이메일 */
    private String email;
    
    /** 활성화 상태 */
    private Boolean isActive;
    
    /** 권한 */
    private AdminRole role;
    
    /** 권한 텍스트 */
    private String roleText;
    
    /** 권한 레벨 */
    private Integer roleLevel;
    
    /** 생성일 */
    private LocalDateTime createdAt;
    
    /** 수정일 */
    private LocalDateTime updatedAt;

    /**
     * Admin 엔티티를 AdminListResponseDTO로 변환
     */
    public static AdminListResponseDTO from(Admin admin) {
        return AdminListResponseDTO.builder()
                .adminId(admin.getAdminId())
                .username(admin.getUsername())
                .name(admin.getName())
                .phone(admin.getPhone())
                .email(admin.getEmail())
                .isActive(admin.isActive())
                .role(admin.getRole())
                .roleText(getRoleText(admin.getRole()))
                .roleLevel(admin.getRole() != null ? admin.getRole().getLevel() : null)
                .createdAt(admin.getCreatedAt())
                .updatedAt(admin.getUpdatedAt())
                .build();
    }

    /**
     * 권한을 한국어 텍스트로 변환
     */
    private static String getRoleText(AdminRole role) {
        if (role == null) return null;
        return switch (role) {
            case ADMIN -> "관리자";
            case CHIEF_MANAGER -> "총관리자";
            case MANAGER -> "매니저";
            case STAFF -> "스태프";
            default -> "미정";
        };
    }
}

