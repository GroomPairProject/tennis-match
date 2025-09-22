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
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

  private final MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request,
                                      HttpServletResponse response,
                                      Authentication authentication) throws IOException, ServletException {

    // Get user details from the authentication object
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    // Prepare data for the JSON response
    List<String> authorities = authentication.getAuthorities().stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.toList());

    String sessionId = request.getSession().getId();

    Map<String, Object> data = new HashMap<>();
    data.put("username", userDetails.getUsername());
    data.put("authorities", authorities);
    data.put("sessionId", sessionId);

    // Create the success response body
    // TODO : refactor message to i18n resources
    ApiResponse<Map<String, Object>> body = ApiResponse.success(data, "로그인 성공");

    // Write the JSON response to the HttpServletResponse
    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setCharacterEncoding("UTF-8");

    jsonConverter.write(body, MediaType.APPLICATION_JSON, new ServletServerHttpResponse(response));

    log.info("Authentication successful for user: {}. Session ID: {}", userDetails.getUsername(), sessionId);

    // Clear authentication attributes to prevent redirect issues
    clearAuthenticationAttributes(request);
  }
}