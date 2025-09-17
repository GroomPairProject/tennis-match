package com.groom.tennis_match.auth.dto;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminLoginDTO {
    private String username;

    private String password;
}
