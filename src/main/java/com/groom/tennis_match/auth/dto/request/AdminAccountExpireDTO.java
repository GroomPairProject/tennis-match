package com.groom.tennis_match.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminAccountExpireDTO {
  @NotBlank
  private String password;

  /**
   * 탈퇴 사유
   */
//  private String reason;

  /**
   * hard Delete 여부
   */
  @Builder.Default
  private Boolean hardDelete=false;

//  private String csrfToken;

}
