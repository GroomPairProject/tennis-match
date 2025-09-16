package com.groom.tennis_match.domain.club.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDate;

/**
 * 클럽 생성 요청 DTO
 * 
 * @author tennis-match
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClubRequestDTO {

    /** 클럽명 */
    @NotBlank(message = "클럽명은 필수입니다")
    @Size(max = 20, message = "클럽명은 20자를 초과할 수 없습니다")
    private String clubName;

    /** 도시 */
    @NotBlank(message = "도시는 필수입니다")
    private String city;

    /** 구/군 */
    @NotBlank(message = "구/군은 필수입니다")
    private String district;

    /** 상세 주소 */
    private String addressDetail;

    /** 클럽 성별 (0: 남성, 1: 여성, 2: 혼성) */
    @NotNull(message = "클럽 성별은 필수입니다")
    private Integer clubGender;

    /** 카테고리 */
    @NotBlank(message = "카테고리는 필수입니다")
    @Size(max = 20, message = "카테고리는 20자를 초과할 수 없습니다")
    private String category;

    /** 클럽 구분 */
    @NotBlank(message = "클럽 구분은 필수입니다")
    @Size(max = 10, message = "클럽 구분은 10자를 초과할 수 없습니다")
    private String clubDiv;

    /** 대표자명 */
    @NotBlank(message = "대표자명은 필수입니다")
    @Size(max = 20, message = "대표자명은 20자를 초과할 수 없습니다")
    private String representativeName;

    /** 활성화 여부 */
    @Builder.Default
    private Boolean isActive = true;

    /** 생성일 */
    private LocalDate createDate;
}
