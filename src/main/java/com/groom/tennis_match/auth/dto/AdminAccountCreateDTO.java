package com.groom.tennis_match.auth.dto;

import com.groom.tennis_match.auth.AdminRole;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
    @NotBlank(message = "관리자명을 입력해주십시오.")
    private String name;
    
    /**
     * 이메일 주소
     */
    @Email
    @NotBlank(message = "이메일을 입력해주십시오.")
    private String email;
    
    /**
     * 전화번호
     */
    @NotBlank(message = "전화번호를 입력해주십시오.")
    private String phone;
    
    /**
     * 발급할 권한
     */
    @NotNull(message = "권한을 설정해주십시오.")
    private AdminRole role;
}
