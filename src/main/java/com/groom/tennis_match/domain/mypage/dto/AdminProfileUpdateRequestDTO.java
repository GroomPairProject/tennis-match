package com.groom.tennis_match.domain.mypage.dto;

import jakarta.validation.constraints.Email;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminProfileUpdateRequestDTO {
    private String username;

    private String name;

    private String phone;

    @Email
    private String email;

    private String password;

    private String profileImageUrl;
}
