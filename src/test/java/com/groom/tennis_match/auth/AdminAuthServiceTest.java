package com.groom.tennis_match.auth;

import com.groom.tennis_match.auth.dto.AdminSignUpDTO;
import com.groom.tennis_match.auth.entity.Admin;
import com.groom.tennis_match.auth.repository.AdminRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
@DisplayName("AdminAuthService 통합 테스트")
class AdminAuthServiceTest {

  private final AdminAuthService adminAuthService;

  private final AdminRepository adminRepository;

  private final PasswordEncoder passwordEncoder;


  @Autowired
  AdminAuthServiceTest(AdminAuthService adminAuthService,
                       AdminRepository adminRepository,
                       PasswordEncoder passwordEncoder) {
    this.adminAuthService = adminAuthService;
    this.adminRepository = adminRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @BeforeEach
  void setUp() {
    // 테스트 전 DB 초기화
    adminRepository.deleteAll();
  }

  @Test
  @DisplayName("계정 발급 성공 - 관리자 계정이 실제 DB에 저장되고 반환된다")
  void registerAdmin_Success() {
    // When
    AdminSignUpDTO result = adminAuthService.registerAdmin();

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getUsername()).isEqualTo("admin");
    assertThat(result.getName()).isEqualTo("최고 권한");
    assertThat(result.getEmail()).isEqualTo("admin@admin.com");

    // 실제 DB에서 저장된 데이터 확인
    List<Admin> savedAdmins = adminRepository.findAll();
    assertThat(savedAdmins).hasSize(1);

    Admin savedAdmin = savedAdmins.get(0);
    assertThat(savedAdmin.getUsername()).isEqualTo("admin");
    assertThat(savedAdmin.getName()).isEqualTo("최고 권한");
    assertThat(savedAdmin.getEmail()).isEqualTo("admin@admin.com");
    assertThat(savedAdmin.getRole()).isEqualTo("ADMIN");
    assertThat(savedAdmin.isActive()).isTrue();
  }

  @Test
  @DisplayName("계정 발급 시 비밀번호 암호화 검증 - 비밀번호가 실제로 암호화되어 저장된다")
  void registerAdmin_PasswordEncoding() {
    // When
    AdminSignUpDTO result = adminAuthService.registerAdmin();

    // Then
    Admin savedAdmin = adminRepository.findAll().get(0);

    // 원본 비밀번호와 암호화된 비밀번호가 다른지 확인
    assertThat(savedAdmin.getPassword()).isNotEqualTo("admin");

    // 암호화된 비밀번호가 올바른지 확인 (PasswordEncoder로 검증)
    assertThat(passwordEncoder.matches("admin", savedAdmin.getPassword())).isTrue();
  }

  @Test
  @DisplayName("계정 발급 시 엔티티 기본값 검증 - 기본값들이 올바르게 설정된다")
  void registerAdmin_DefaultValues() {
    // When
    adminAuthService.registerAdmin();

    // Then
    Admin savedAdmin = adminRepository.findAll().get(0);

    assertThat(savedAdmin.isActive()).isTrue();
    assertThat(savedAdmin.getPasswordMiss()).isEqualTo((short) 0);
    assertThat(savedAdmin.isLock()).isFalse();
    assertThat(savedAdmin.getRole()).isEqualTo("ADMIN");
  }

  @Test
  @DisplayName("계정 발급 결과 DTO 변환 검증 - 저장된 엔티티가 올바른 DTO로 변환된다")
  void registerAdmin_DTOConversion() {
    // When
    AdminSignUpDTO result = adminAuthService.registerAdmin();

    // Then
    assertThat(result).isNotNull();
    assertThat(result.getUsername()).isEqualTo("admin");
    assertThat(result.getName()).isEqualTo("최고 권한");
    assertThat(result.getEmail()).isEqualTo("admin@admin.com");

    // password는 DTO에 포함되지 않음
    assertThat(result.getPassword()).isNull();
  }

  @Test
  @DisplayName("중복 계정 발급 테스트 - 여러 번 호출해도 각각 저장된다")
  void registerAdmin_MultipleRegistrations() {
    // When
    AdminSignUpDTO result1 = adminAuthService.registerAdmin();
    AdminSignUpDTO result2 = adminAuthService.registerAdmin();

    // Then
    List<Admin> savedAdmins = adminRepository.findAll();
    assertThat(savedAdmins).hasSize(2);

    // 두 결과 모두 동일한 값으로 생성되었는지 확인
    assertThat(result1.getUsername()).isEqualTo(result2.getUsername());
    assertThat(result1.getEmail()).isEqualTo(result2.getEmail());

    // 저장된 엔티티들의 ID가 다른지 확인 (실제로 다른 레코드)
    assertThat(savedAdmins.get(0).getAdminId()).isNotEqualTo(savedAdmins.get(1).getAdminId());
  }

  @Test
  @DisplayName("계정 발급 후 조회 테스트 - 저장된 계정을 username으로 조회할 수 있다")
  void registerAdmin_FindByUsername() {
    // Given
    adminAuthService.registerAdmin();

    // When
    Optional<Admin> foundAdmin = adminRepository.findByUsername("admin");

    // Then
    assertThat(foundAdmin).isPresent();
    assertThat(foundAdmin.get().getName()).isEqualTo("최고 권한");
    assertThat(foundAdmin.get().getEmail()).isEqualTo("admin@admin.com");
  }
}