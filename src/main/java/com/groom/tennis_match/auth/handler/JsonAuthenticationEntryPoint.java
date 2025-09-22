package com.groom.tennis_match.auth.handler;

import com.groom.tennis_match.common.dto.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class JsonAuthenticationEntryPoint implements AuthenticationEntryPoint {

  private final MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
    ErrorResponse body = ErrorResponse.of("UNAUTHORIZED", "미인증 시 접근할 수 없습니다.", request.getRequestURI());
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");
    jsonConverter.write(body, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));
  }
}
