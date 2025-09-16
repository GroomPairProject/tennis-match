package com.groom.tennis_match.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import java.io.IOException;

@Slf4j
public class AuthLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {
  @Override
  public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
    super.onLogoutSuccess(request, response, authentication);

    String username = "Anonymous";

    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    if (authentication != null && authentication.getPrincipal() instanceof UserDetails u) {
      username = u.getUsername();
    }
    log.info("로그아웃 성공. 세션 삭제. username={}", username);

    response.setStatus(200);
    response.setContentType("application/json;charset=UTF-8");
    response.getWriter().write("{\"message\":\"logout success\"}");

  }
}