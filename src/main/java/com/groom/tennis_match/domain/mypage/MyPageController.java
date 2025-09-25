package com.groom.tennis_match.domain.mypage;

import com.groom.tennis_match.common.constant.SuccessCode;
import com.groom.tennis_match.common.dto.ApiResponse;
import com.groom.tennis_match.domain.mypage.dto.AdminProfileDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class MyPageController {
  private final MyPageService myPageService;

  @GetMapping("/admin/mypage")
  public ApiResponse<AdminProfileDTO> getAdminProfile(HttpServletRequest request) {

    HttpSession session = request.getSession(false);
    String username = (String) session.getAttribute("username");

    return ApiResponse.success(myPageService.getAdminProfile(username), SuccessCode.USER_UPDATE_SUCCESS);

  }
}
