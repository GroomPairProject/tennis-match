package com.groom.tennis_match.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class AdminLoginDTO {
    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
