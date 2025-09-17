package com.groom.tennis_match.auth.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminProfileUpdateDTO {
    private String username;

    private String name;

    private String phone;

    private String email;

    private String password;

    private String profileImageUrl;
}
