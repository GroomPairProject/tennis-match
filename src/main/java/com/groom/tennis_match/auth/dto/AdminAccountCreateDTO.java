package com.groom.tennis_match.auth.dto;

import com.groom.tennis_match.auth.AdminRole;
import lombok.*;

/**
 * 관리자 계정 생성을 위한 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminAccountCreateDTO {
    
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
     * 발급할 권한
     */
    private AdminRole role;
}
