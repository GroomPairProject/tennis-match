package com.groom.tennis_match.auth.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.groom.tennis_match.auth.entity.Admin;
import com.groom.tennis_match.auth.service.AdminDetailsService;
import com.groom.tennis_match.common.constant.ErrorCode;
import com.groom.tennis_match.common.exception.BusinessException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.util.MimeTypeUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
public class JsonUsernamePasswordAuthFilter extends UsernamePasswordAuthenticationFilter {
  private final ObjectMapper objectMapper = new ObjectMapper();
  private final AdminDetailsService adminDetailsService;

  @Override
  public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    UsernamePasswordAuthenticationToken authenticationToken = null;

    String userId = null;
    String userPassword = null;

    // JSON 요청일 경우
    if (request.getContentType().equals(MimeTypeUtils.APPLICATION_JSON_VALUE)) {
      try{
        // ObjectMapper를 이용해서 JSON 데이터를 dto에 저장 후 dto의 데이터를 이용

        LoginDto loginDto = objectMapper.readValue(
                request.getReader().lines().collect(Collectors.joining()), LoginDto.class);

        userId = loginDto.getUsername();
        userPassword = loginDto.getPassword();

        log.debug("user accessed - USERID : {}", userId);
      } catch(IOException e){
        e.printStackTrace();
      }

      // POST 요청일 경우 기존과 같은 방식 이용
    } else if(request.getMethod().equals("POST")){
      userId = obtainUsername(request);
      userPassword = obtainPassword(request);

      log.debug("user accessed - USERID : {}", userId);
    }
    else {
      log.error("POST / JSON 요청만 가능합니다.");
      throw new AuthenticationServiceException("Authentication Method Not Supported : " + request.getMethod());
    }

    if(userId.equals("") || userPassword.equals("")){
      log.warn("ID 혹은 PW를 입력하지 않았습니다.");
      throw new AuthenticationServiceException("ID 혹은 PW를 입력하지 않았습니다.");
    }

    // isLock, isActive 검증
    Admin admin = adminDetailsService.loadUserByUsername(userId);

    if(admin.isLock()) {
      log.debug("user locked");
      throw new BusinessException(ErrorCode.ACCOUNT_LOCKED);
    }

    if(!admin.isActive()) {
      log.debug("deleted user");
      throw new BusinessException(ErrorCode.ACCOUNT_DISABLED);
    }


    authenticationToken = new UsernamePasswordAuthenticationToken(userId, userPassword);
    this.setDetails(request, authenticationToken);
    return this.getAuthenticationManager().authenticate(authenticationToken);

  }

  @Getter
  @Setter
  @ToString
  private static class LoginDto {
    private String username;
    private String password;
  }
}