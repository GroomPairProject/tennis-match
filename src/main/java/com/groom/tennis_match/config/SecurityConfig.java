package com.groom.tennis_match.config;

import com.groom.tennis_match.auth.filter.JsonUsernamePasswordAuthFilter;
import com.groom.tennis_match.auth.handler.*;
import com.groom.tennis_match.auth.provider.CustomAuthenticationProvider;
import com.groom.tennis_match.auth.service.AdminDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.http.HttpMethod;

@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AdminDetailsService userDetailsService;

    // Password encoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    // Dao provider
//    @Bean
//    public DaoAuthenticationProvider daoAuthenticationProvider(PasswordEncoder encoder) {
//        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
//        provider.setUserDetailsService(userDetailsService);
//        provider.setPasswordEncoder(encoder);
//        return provider;
//    }

    // custom provider
    @Bean
    public AuthenticationProvider authenticationProvider(PasswordEncoder encoder) {
        CustomAuthenticationProvider provider = new CustomAuthenticationProvider(userDetailsService, encoder);
        return provider;
    }


    // Success/Failure/Logout handlers
    @Bean
    public AuthSuccessHandler authenticationSuccessHandler() {
        return new AuthSuccessHandler();
    }

    @Bean
    public AuthFailureHandler authenticationFailureHandler() {
        return new AuthFailureHandler();
    }

    @Bean
    public AuthLogoutSuccessHandler logoutSuccessHandler() {
        return new AuthLogoutSuccessHandler();
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() { return new AuthAccessDeniedHandler(); }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() { return new JsonAuthenticationEntryPoint(); }

    // Custom JSON login filter as a bean (AuthenticationManager 주입)
    @Bean
    public JsonUsernamePasswordAuthFilter jsonUsernamePasswordAuthFilter(
            AuthenticationConfiguration authConfig,
            AuthSuccessHandler successHandler,
            AuthFailureHandler failureHandler
    ) throws Exception {
        AuthenticationManager authManager = authConfig.getAuthenticationManager();
        JsonUsernamePasswordAuthFilter filter = new JsonUsernamePasswordAuthFilter();
        filter.setFilterProcessesUrl("/api/admin/auth/login");
        filter.setAuthenticationManager(authManager);
        filter.setUsernameParameter("username");
        filter.setPasswordParameter("password");
        filter.setAuthenticationSuccessHandler(successHandler);
        filter.setAuthenticationFailureHandler(failureHandler);
        return filter;
    }

    // Security filter chain
    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http,
            AuthenticationProvider authenticationProvider,
            AuthLogoutSuccessHandler logoutSuccessHandler,
            JsonUsernamePasswordAuthFilter jsonLoginFilter
    ) throws Exception {

        http
                .securityContext(sc -> sc.requireExplicitSave(false))   // 세션 발급
                // 세션 기반
                .sessionManagement(sm -> sm.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))

                // CSRF: 기본 활성화, 특정 경로만 예외 (H2, JSON 로그인)
                // 현재 비활성화, 구현 예정
                .csrf(csrf -> csrf.disable()
//                        .ignoringRequestMatchers(
//                                new AntPathRequestMatcher("/h2-console/**"),
//                                new AntPathRequestMatcher("/api/admin/**")
//                        )
                )

                // 접근 권한 부족 시 해당 핸들러 동작
                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(authenticationEntryPoint()) // unauthorized 반환
                        .accessDeniedHandler(accessDeniedHandler()) // 권한 부족, forbidden 반환
                )

                // H2 콘솔 프레임 허용(로컬 개발용)
                .headers(headers -> headers.frameOptions(frame -> frame.disable()))

                // 폼 로그인 비활성화(우리는 JSON 로그인 사용)
                .formLogin(form -> form.disable())
                // 로그아웃
                .logout(logout -> logout
                        .logoutUrl("/api/admin/auth/logout")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .logoutSuccessHandler(logoutSuccessHandler) // 로깅 핸들러
                        .permitAll()
                )

                // 인가 규칙: 꼭 필요한 곳만 열기
                .authorizeHttpRequests(authz -> authz
                        // preflight 허용
//                        .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers("/h2-console/**").permitAll()
                        .requestMatchers("/api/admin/auth/**").permitAll()  // 인증 관련 API는 허용
                        .requestMatchers(HttpMethod.DELETE, "/api/admin/accounts/**").hasAnyRole("ADMIN", "CHIEF_MANAGER")  // 관리자 삭제는 ADMIN, CHIEF_MANAGER 권한만
                        .requestMatchers("/api/admin/users/**").hasRole("ADMIN")  // 관리자 목록 조회는 ADMIN 권한만
                        .requestMatchers("/api/admin/**").authenticated()  // 나머지 관리자 API는 인증 필요
//                        .requestMatchers("/**").permitAll()
//                        .requestMatchers("/actuator/health").permitAll()
                        // 그 외는 프로젝트 규칙에 맞게 정의
                        .anyRequest().authenticated()
                )

                // 인증 Provider 등록
                .authenticationProvider(authenticationProvider)

                // 커스텀 JSON 로그인 필터를 UsernamePasswordAuthenticationFilter 위치에 삽입
                .addFilterAt(jsonLoginFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
