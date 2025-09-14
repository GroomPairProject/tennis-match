package com.groom.tennis_match.auth.dto;

import com.groom.tennis_match.auth.AdminRole;
import lombok.*;

/**
 * 관리자 계정 발급 결과를 담는 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminAccountRegisterDTO {
    
    /**
     * 발급된 사용자명
     */
    private String username;
    
    /**
     * 임시 비밀번호 (발급 후 사용자에게 노출됨)
     */
    private String temporaryPassword;
    
    /**
     * 사용자 이름
     */
    private String name;
    
    /**
     * 이메일 주소
     */
    private String email;
    
    /**
     * 전화번호
     */
    private String phone;
    
    /**
     * 발급된 권한
     */
    private AdminRole role;
    
    /**
     * 계정 발급자
     */
    private String createdBy;
}
