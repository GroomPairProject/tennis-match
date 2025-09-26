package com.groom.tennis_match.domain.mypage;

import com.groom.tennis_match.common.constant.SuccessCode;
import com.groom.tennis_match.common.dto.ApiResponse;
import com.groom.tennis_match.domain.mypage.dto.AdminProfileDTO;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Enumeration;

@Slf4j
@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class AdminMyPageController {
  private final AdminMyPageService adminMyPageService;

  @GetMapping("/admin/mypage")
  public ApiResponse<AdminProfileDTO> getAdminProfile(HttpServletRequest request) {

    HttpSession session = request.getSession(false);
    String username = (String) session.getAttribute("username");
    log.info("log of username = {}", username);

    Enumeration<String> names = session.getAttributeNames();
    while (names.hasMoreElements()) {
      String name = names.nextElement();
      Object value = session.getAttribute(name);
      log.info("session attribute: {} = {}", name, value);
    }
    return ApiResponse.success(adminMyPageService.getAdminProfile(username), SuccessCode.USER_UPDATE_SUCCESS);

  }
}
