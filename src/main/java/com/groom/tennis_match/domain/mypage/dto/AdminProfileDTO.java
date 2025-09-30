package com.groom.tennis_match.domain.mypage.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminProfileDTO {
  private String username;

  private String name;

  private String phone;

  private String email;

  private String profileImageUrl;
}
