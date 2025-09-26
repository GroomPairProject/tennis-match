package com.groom.tennis_match.domain.mypage;

import com.groom.tennis_match.auth.entity.Admin;
import com.groom.tennis_match.common.constant.SuccessCode;
import com.groom.tennis_match.common.dto.ApiResponse;
import com.groom.tennis_match.domain.mypage.dto.AdminProfileDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class AdminMyPageController {
  private final AdminMyPageService adminMyPageService;

  @GetMapping("/admin/mypage")
  public ApiResponse<AdminProfileDTO> getAdminProfile(@AuthenticationPrincipal Admin admin) {
    return ApiResponse.success(adminMyPageService.getAdminProfile(admin.getUsername()), SuccessCode.USER_UPDATE_SUCCESS);

  }
}
