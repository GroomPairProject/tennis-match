package com.groom.tennis_match.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.server.ServletServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import java.io.IOException;

@Slf4j
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private RequestCache requestCache = new HttpSessionRequestCache();


  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//    SavedRequest savedRequest = requestCache.getRequest(request, response);
//
//    if (savedRequest != null) {
//      requestCache.removeRequest(request, response);
//      clearAuthenticationAttributes(request);
//    }
//
//    String accept = request.getHeader("accept");
//
//    PrincipalDetails principalDetails = null;
//    if (SecurityContextHolder.getContext().getAuthentication() != null) {
//      Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//      if (principal != null && principal instanceof UserDetails) {
//        principalDetails = (PrincipalDetails) principal;
//      }
//    }
//
//    // 로그인 시, JSON 으로 반환
//    MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
//    MediaType jsonMimeType = MediaType.APPLICATION_JSON;
//
//    String message = "로그인 성공";
//    JsonDto jsonDto = JsonDto.success(principalDetails, message);
//    if (jsonConverter.canWrite(jsonDto.getClass(), jsonMimeType)) {
//      jsonConverter.write(jsonDto, jsonMimeType, new ServletServerHttpResponse(response));
//    }
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
    log.info( "로그인 성공. 세션 발급. username: {}" ,userDetails.getUsername());


    response.getWriter().write("success");
  }

  public void setRequestCache(RequestCache requestCache) {
    this.requestCache = requestCache;
  }
}