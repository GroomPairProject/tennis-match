package com.groom.tennis_match.auth.handler;

import com.groom.tennis_match.common.dto.ApiResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
public class AuthLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler {

  private final MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();

  @Override
  public void onLogoutSuccess(HttpServletRequest request,
                              HttpServletResponse response,
                              Authentication authentication) throws IOException, ServletException {

    String username = "Anonymous";
    if (authentication != null && authentication.getPrincipal() instanceof UserDetails u) {
      username = u.getUsername();
    }

    // 응답 데이터
    // TODO : refactor message to i18n resources
    Map<String, Object> data = new HashMap<>();
    data.put("username", username);
    ApiResponse<Map<String, Object>> body = ApiResponse.success(data, "로그아웃 성공");

    // 응답 설정
    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");

    // JSON으로 변환하여 응답
    jsonConverter.write(body, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
    log.info("logout successful for user. username={}", username);
  }
}