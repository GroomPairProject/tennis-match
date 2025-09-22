package com.groom.tennis_match.auth.handler;

import com.groom.tennis_match.common.dto.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import java.io.IOException;

@Slf4j
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  private final MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();

  @Override
  public void onAuthenticationFailure(HttpServletRequest request,
                                      HttpServletResponse response,
                                      AuthenticationException exception)
          throws IOException, ServletException {

    log.info("Exception Type : {}", exception.getClass().getName());
    log.info("Exception Message : {}", exception.getMessage());

    // ErrorResponse 생성
    // TODO : refactor message to i18n resources
    ErrorResponse errorResponse = ErrorResponse.of(
            "AUTH_FAILURE",
            exception.getMessage(),
            request.getRequestURI()
    );

    // HTTP 상태코드 설정
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");

    // JSON 변환 후 응답
    new MappingJackson2HttpMessageConverter()
            .write(errorResponse, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));

    log.info("Login Failed");
  }
}