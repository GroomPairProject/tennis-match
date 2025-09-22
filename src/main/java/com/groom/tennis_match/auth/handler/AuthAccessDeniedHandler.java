package com.groom.tennis_match.auth.handler;


import com.groom.tennis_match.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;

@Slf4j
public class AuthAccessDeniedHandler implements AccessDeniedHandler {

  private final MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();

  @Override
  public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException ex)
          throws IOException {
    ErrorResponse body = ErrorResponse.of("FORBIDDEN", "접근 권한이 부족합니다.", request.getRequestURI());

    response.setStatus(HttpServletResponse.SC_FORBIDDEN);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");

    jsonConverter.write(body, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
  }
}