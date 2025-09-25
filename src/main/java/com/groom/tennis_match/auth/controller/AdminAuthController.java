package com.groom.tennis_match.auth.controller;

import com.groom.tennis_match.auth.dto.request.AdminAccountExpireDTO;
import com.groom.tennis_match.auth.dto.response.AdminAccountExpireResponseDTO;
import com.groom.tennis_match.auth.service.AdminAuthService;
import com.groom.tennis_match.auth.dto.response.AdminAccountCreateDTO;
import com.groom.tennis_match.auth.dto.request.AdminAccountRegisterDTO;
import com.groom.tennis_match.common.constant.SuccessCode;
import com.groom.tennis_match.common.dto.ApiResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/auth")
public class AdminAuthController {
    private final AdminAuthService adminAuthService;

    /**
     * 계정 발급 핸들러 메소드입니다.
     * 발급 과정에서 인증 권한 확인 필요합니다.
     * @return
     */
    @PostMapping("/accounts")
    public ApiResponse<AdminAccountRegisterDTO> registerAccount(@Valid @RequestBody AdminAccountCreateDTO adminAccountCreateDTO) {
        AdminAccountRegisterDTO admin = adminAuthService.registerAccount(adminAccountCreateDTO);
        return ApiResponse.success(admin, SuccessCode.USER_CREATE_SUCCESS);
    }

    /**
     * 계정 탈퇴 핸들러 메소드입니다.
     * @param adminAccountExpireDTO
     * @return
     */
    @DeleteMapping("/withdraw")
    public ApiResponse<AdminAccountExpireResponseDTO> withdrawAccount(
            @Valid @RequestBody AdminAccountExpireDTO adminAccountExpireDTO,
            HttpServletRequest request) {
        AdminAccountExpireResponseDTO admin = adminAuthService.withdrawAccount(adminAccountExpireDTO);

        // 2. 세션 만료 처리
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
            log.info("탈퇴한 사용자 세션 만료 처리 완료. sessionId={}", session.getId());
        }


        return ApiResponse.success(admin, SuccessCode.USER_DELETE_SUCCESS);
    }


    /**
     * 관리자용 초기화 핸들러 메서드
     * 테스트에서만 사용하시오
     * @return
     */
    @GetMapping("/admin-register-temp")
    public String initService() {
        adminAuthService.createTestAdmin("testAdmin",
                                "1234",
                                    "최고 권한",
                                    "admin@test.com",
                                    "010-12340-5678");

        return "noice";
    }

//    @PostMapping("/accounts")
//    public ApiResponse<AdminSignUpDTO> signUp(@Valid @RequestBody UserSignUpRequest request) {
//        AdminSignUpDTO admin = adminAuthService.registerAdmin(request);
//        return ApiResponse.success(admin, SuccessCode.USER_CREATE_SUCCESS);
//    }
}
