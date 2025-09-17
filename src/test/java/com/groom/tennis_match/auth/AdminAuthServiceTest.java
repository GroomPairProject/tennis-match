package com.groom.tennis_match.auth;

import com.groom.tennis_match.auth.dto.AdminAccountCreateDTO;
import com.groom.tennis_match.auth.dto.AdminAccountRegisterDTO;
import com.groom.tennis_match.auth.entity.Admin;
import com.groom.tennis_match.auth.repository.AdminRepository;
import com.groom.tennis_match.auth.service.AdminAuthService;
import com.groom.tennis_match.common.exception.BusinessException;
import com.groom.tennis_match.auth.util.PasswordUtil;
import com.groom.tennis_match.auth.util.SecurityContextUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("AdminAuthService 통합 테스트")
class AdminAuthServiceTest {

  @Autowired
  private AdminAuthService adminAuthService;

  @Autowired
  private AdminRepository adminRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @MockitoBean
  private SecurityContextUtil securityContextUtil;

  @MockitoBean
  private PasswordUtil passwordUtil;

  @BeforeEach
  void setUp() {
    // 테스트 전 DB 초기화
    adminRepository.deleteAll();
    
    // Mock 설정 - 기본적으로 ADMIN 권한을 가진 사용자로 설정
    when(securityContextUtil.getCurrentUserRole()).thenReturn(AdminRole.ADMIN);
    when(securityContextUtil.getCurrentUsername()).thenReturn("test-admin");
    when(passwordUtil.generateTemporaryPassword()).thenReturn("TempPass123!");
  }

  @Test
  @DisplayName("계정 발급 성공 - 관리자 계정이 실제 DB에 저장되고 반환된다")
  void registerAccount_Success() {
    // Given
    AdminAccountCreateDTO createDTO = AdminAccountCreateDTO.builder()
            .name("테스트 관리자")
            .email("test@example.com")
            .phone("010-1234-5678")
            .role(AdminRole.MANAGER)
            .build();

    // When
    AdminAccountRegisterDTO result = adminAuthService.registerAccount(createDTO);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getUsername()).isEqualTo("test"); // 이메일 @ 앞부분
    assertThat(result.getName()).isEqualTo("테스트 관리자");
    assertThat(result.getEmail()).isEqualTo("test@example.com");
    assertThat(result.getPhone()).isEqualTo("010-1234-5678");
    assertThat(result.getRole()).isEqualTo(AdminRole.MANAGER);
    assertThat(result.getTemporaryPassword()).isEqualTo("TempPass123!");
    assertThat(result.getCreatedBy()).isEqualTo("test-admin");

    // 실제 DB에서 저장된 데이터 확인
    List<Admin> savedAdmins = adminRepository.findAll();
    assertThat(savedAdmins).hasSize(1);

    Admin savedAdmin = savedAdmins.get(0);
    assertThat(savedAdmin.getUsername()).isEqualTo("test");
    assertThat(savedAdmin.getName()).isEqualTo("테스트 관리자");
    assertThat(savedAdmin.getEmail()).isEqualTo("test@example.com");
    assertThat(savedAdmin.getRole()).isEqualTo("MANAGER");
    assertThat(savedAdmin.isActive()).isTrue();
  }

  @Test
  @DisplayName("계정 발급 시 비밀번호 암호화 검증 - 임시 비밀번호가 실제로 암호화되어 저장된다")
  void registerAccount_PasswordEncoding() {
    // Given
    AdminAccountCreateDTO createDTO = AdminAccountCreateDTO.builder()
            .name("테스트 관리자")
            .email("test@example.com")
            .phone("010-1234-5678")
            .role(AdminRole.STAFF)
            .build();

    // When
    AdminAccountRegisterDTO result = adminAuthService.registerAccount(createDTO);

    // Then
    Admin savedAdmin = adminRepository.findAll().get(0);

    // 원본 임시 비밀번호와 암호화된 비밀번호가 다른지 확인
    assertThat(savedAdmin.getPassword()).isNotEqualTo("TempPass123!");

    // 암호화된 비밀번호가 올바른지 확인 (PasswordEncoder로 검증)
    assertThat(passwordEncoder.matches("TempPass123!", savedAdmin.getPassword())).isTrue();
    
    // 임시 비밀번호가 결과에 포함되어 있는지 확인
    assertThat(result.getTemporaryPassword()).isEqualTo("TempPass123!");
  }

  @Test
  @DisplayName("계정 발급 시 엔티티 기본값 검증 - 기본값들이 올바르게 설정된다")
  void registerAccount_DefaultValues() {
    // Given
    AdminAccountCreateDTO createDTO = AdminAccountCreateDTO.builder()
            .name("테스트 관리자")
            .email("test@example.com")
            .phone("010-1234-5678")
            .role(AdminRole.CHIEF_MANAGER)
            .build();

    // When
    adminAuthService.registerAccount(createDTO);

    // Then
    Admin savedAdmin = adminRepository.findAll().get(0);

    assertThat(savedAdmin.isActive()).isTrue();
    assertThat(savedAdmin.getPasswordMiss()).isEqualTo((short) 0);
    assertThat(savedAdmin.isLock()).isFalse();
    assertThat(savedAdmin.getRole()).isEqualTo("CHIEF_MANAGER");
  }

  @Test
  @DisplayName("계정 발급 결과 DTO 변환 검증 - 저장된 엔티티가 올바른 DTO로 변환된다")
  void registerAccount_DTOConversion() {
    // Given
    AdminAccountCreateDTO createDTO = AdminAccountCreateDTO.builder()
            .name("테스트 관리자")
            .email("test@example.com")
            .phone("010-1234-5678")
            .role(AdminRole.STAFF)
            .build();

    // When
    AdminAccountRegisterDTO result = adminAuthService.registerAccount(createDTO);

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getUsername()).isEqualTo("test");
    assertThat(result.getName()).isEqualTo("테스트 관리자");
    assertThat(result.getEmail()).isEqualTo("test@example.com");
    assertThat(result.getPhone()).isEqualTo("010-1234-5678");
    assertThat(result.getRole()).isEqualTo(AdminRole.STAFF);
    assertThat(result.getCreatedBy()).isEqualTo("test-admin");

    // 임시 비밀번호는 DTO에 포함됨
    assertThat(result.getTemporaryPassword()).isEqualTo("TempPass123!");
  }

  @Test
  @DisplayName("중복 계정 발급 테스트 - 여러 번 호출해도 각각 저장된다")
  void registerAccount_MultipleRegistrations() {
    // Given
    AdminAccountCreateDTO createDTO1 = AdminAccountCreateDTO.builder()
            .name("테스트 관리자1")
            .email("test1@example.com")
            .phone("010-1111-1111")
            .role(AdminRole.STAFF)
            .build();

    AdminAccountCreateDTO createDTO2 = AdminAccountCreateDTO.builder()
            .name("테스트 관리자2")
            .email("test2@example.com")
            .phone("010-2222-2222")
            .role(AdminRole.MANAGER)
            .build();

    // When
    AdminAccountRegisterDTO result1 = adminAuthService.registerAccount(createDTO1);
    AdminAccountRegisterDTO result2 = adminAuthService.registerAccount(createDTO2);

    // Then
    List<Admin> savedAdmins = adminRepository.findAll();
    assertThat(savedAdmins).hasSize(2);

    // 두 결과가 다른 값으로 생성되었는지 확인
    assertThat(result1.getUsername()).isNotEqualTo(result2.getUsername());
    assertThat(result1.getEmail()).isNotEqualTo(result2.getEmail());

    // 저장된 엔티티들의 ID가 다른지 확인 (실제로 다른 레코드)
    assertThat(savedAdmins.get(0).getAdminId()).isNotEqualTo(savedAdmins.get(1).getAdminId());
  }

  @Test
  @DisplayName("계정 발급 후 조회 테스트 - 저장된 계정을 username으로 조회할 수 있다")
  void registerAccount_FindByUsername() {
    // Given
    AdminAccountCreateDTO createDTO = AdminAccountCreateDTO.builder()
            .name("테스트 관리자")
            .email("test@example.com")
            .phone("010-1234-5678")
            .role(AdminRole.STAFF)
            .build();

    adminAuthService.registerAccount(createDTO);

    // When
    Optional<Admin> foundAdmin = adminRepository.findByUsername("test");

    // Then
    assertThat(foundAdmin).isPresent();
    assertThat(foundAdmin.get().getName()).isEqualTo("테스트 관리자");
    assertThat(foundAdmin.get().getEmail()).isEqualTo("test@example.com");
    assertThat(foundAdmin.get().getRole()).isEqualTo("STAFF");
  }

  @Test
  @DisplayName("권한 검증 실패 테스트 - 자신의 권한 이상으로 계정을 생성할 수 없다")
  void registerAccount_UnauthorizedRoleCreation() {
    // Given - MANAGER 권한을 가진 사용자가 CHIEF_MANAGER 권한으로 계정을 생성하려고 시도
    when(securityContextUtil.getCurrentUserRole()).thenReturn(AdminRole.MANAGER);
    
    AdminAccountCreateDTO createDTO = AdminAccountCreateDTO.builder()
            .name("테스트 관리자")
            .email("test@example.com")
            .phone("010-1234-5678")
            .role(AdminRole.CHIEF_MANAGER) // MANAGER보다 높은 권한
            .build();

    // When & Then
    assertThatThrownBy(() -> adminAuthService.registerAccount(createDTO))
            .isInstanceOf(BusinessException.class)
            .hasMessageContaining("권한이 부족합니다");
  }

  @Test
  @DisplayName("사용자명 중복 처리 테스트 - 중복된 이메일로 계정 생성 시 번호가 추가된다")
  void registerAccount_UsernameDuplicateHandling() {
    // Given
    AdminAccountCreateDTO createDTO1 = AdminAccountCreateDTO.builder()
            .name("테스트 관리자1")
            .email("test@example.com")
            .phone("010-1111-1111")
            .role(AdminRole.STAFF)
            .build();

    AdminAccountCreateDTO createDTO2 = AdminAccountCreateDTO.builder()
            .name("테스트 관리자2")
            .email("test@example.com")
            .phone("010-2222-2222")
            .role(AdminRole.STAFF)
            .build();

    // When
    AdminAccountRegisterDTO result1 = adminAuthService.registerAccount(createDTO1);
    AdminAccountRegisterDTO result2 = adminAuthService.registerAccount(createDTO2);

    // Then
    assertThat(result1.getUsername()).isEqualTo("test");
    assertThat(result2.getUsername()).isEqualTo("test1"); // 중복으로 인해 번호 추가

    // 두 계정 모두 DB에 저장되었는지 확인
    List<Admin> savedAdmins = adminRepository.findAll();
    assertThat(savedAdmins).hasSize(2);
  }

  @Test
  @DisplayName("임시 비밀번호 생성 호출 검증 - PasswordUtil이 올바르게 호출된다")
  void registerAccount_TemporaryPasswordGeneration() {
    // Given
    AdminAccountCreateDTO createDTO = AdminAccountCreateDTO.builder()
            .name("테스트 관리자")
            .email("test@example.com")
            .phone("010-1234-5678")
            .role(AdminRole.STAFF)
            .build();

    // When
    adminAuthService.registerAccount(createDTO);

    // Then
    verify(passwordUtil, times(1)).generateTemporaryPassword();
    verify(passwordUtil, times(1)).logPasswordGeneration("test");
  }
}