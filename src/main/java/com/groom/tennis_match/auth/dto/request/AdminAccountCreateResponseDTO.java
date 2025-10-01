package com.groom.tennis_match.auth.dto.request;

import com.groom.tennis_match.auth.AdminRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

/**
 * 관리자 계정 생성을 위한 요청 DTO
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminAccountCreateResponseDTO {
    
    /**
     * 사용자 이름
     */
    @NotBlank
    private String name;
    
    /**
     * 이메일 주소
     */
    @Email
    @NotBlank
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
