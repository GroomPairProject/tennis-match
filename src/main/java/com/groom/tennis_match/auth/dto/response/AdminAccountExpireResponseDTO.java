package com.groom.tennis_match.auth.dto.response;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminAccountExpireResponseDTO {
  private String username;

  private Boolean hardDeleted;

  private Boolean expired;
}
