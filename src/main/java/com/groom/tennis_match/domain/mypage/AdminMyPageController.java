package com.groom.tennis_match.domain.mypage;

import com.groom.tennis_match.auth.entity.Admin;
import com.groom.tennis_match.common.constant.SuccessCode;
import com.groom.tennis_match.common.dto.ApiResponse;
import com.groom.tennis_match.domain.mypage.dto.AdminProfileDTO;
import com.groom.tennis_match.domain.mypage.dto.AdminProfileUpdateRequestDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/admin/mypage")
@RestController
@RequiredArgsConstructor
public class AdminMyPageController {
  private final AdminMyPageService adminMyPageService;

  @GetMapping
  public ApiResponse<AdminProfileDTO> getAdminProfile(@AuthenticationPrincipal Admin admin) {
    return ApiResponse.success(
            adminMyPageService.getAdminProfile(admin.getUsername()),
            SuccessCode.USER_READ_SUCCESS);

  }

  @PutMapping
  public ApiResponse<AdminProfileDTO> updateAdminProfile(
          @AuthenticationPrincipal Admin admin,
          @RequestBody AdminProfileUpdateRequestDTO requestDTO) {
    adminMyPageService.updateAdminProfile(requestDTO, admin.getAdminId());

    return ApiResponse.success(SuccessCode.USER_UPDATE_SUCCESS);
  }
}
