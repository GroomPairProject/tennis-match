package com.groom.tennis_match.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import java.io.IOException;

@Slf4j
public class AuthFailureHandler extends SimpleUrlAuthenticationFailureHandler {

  private RequestCache requestCache = new HttpSessionRequestCache();

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
    logger.info("Exception Type : " + exception.getClass().getName());
    logger.info("Exception Message : " + exception.getMessage());

    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //401 인증 실패
    response.getWriter().write("fail");
    log.info("로그인 실패");
  }
}