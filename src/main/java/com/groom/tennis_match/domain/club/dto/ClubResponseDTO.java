package com.groom.tennis_match.domain.club.dto;

import com.groom.tennis_match.domain.club.entity.Club;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 클럽 응답 DTO
 * 
 * @author tennis-match
 */
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClubResponseDTO {

    /** 클럽 ID */
    private Long clubId;

    /** 클럽명 */
    private String clubName;

    /** 도시 */
    private String city;

    /** 구/군 */
    private String district;

    /** 상세 주소 */
    private String addressDetail;

    /** 클럽 성별 (0: 남성, 1: 여성, 2: 혼성) */
    private Integer clubGender;

    /** 클럽 성별 텍스트 */
    private String clubGenderText;

    /** 카테고리 */
    private String category;

    /** 클럽 구분 */
    private String clubDiv;

    /** 대표자명 */
    private String representativeName;

    /** 활성화 여부 */
    private Boolean isActive;

    /** 생성일 */
    private LocalDate createDate;

    /** 생성 시간 */
    private LocalDateTime createAt;

    /** 생성자 */
    private Long creator;

    /** 수정 시간 */
    private LocalDateTime updatedAt;

    /** 수정자 */
    private Long editor;

    /**
     * Club 엔티티를 ClubResponseDTO로 변환
     */
    public static ClubResponseDTO from(Club club) {
        return ClubResponseDTO.builder()
                .clubId(club.getClubId())
                .clubName(club.getClubName())
                .city(club.getCity())
                .district(club.getDistrict())
                .addressDetail(club.getAddressDetail())
                .clubGender(club.getClubGender())
                .clubGenderText(getGenderText(club.getClubGender()))
                .category(club.getCategory())
                .clubDiv(club.getClubDiv())
                .representativeName(club.getRepresentativeName())
                .isActive(club.getIsActive())
                .createDate(club.getCreateDate())
                .createAt(club.getCreateAt())
                .creator(club.getCreator())
                .updatedAt(club.getUpdatedAt())
                .editor(club.getEditor())
                .build();
    }

    /**
     * 성별 코드를 텍스트로 변환
     */
    private static String getGenderText(Integer clubGender) {
        if (clubGender == null) return null;
        
        return switch (clubGender) {
            case 0 -> "남성";
            case 1 -> "여성";
            case 2 -> "혼성";
            default -> "미정";
        };
    }
}
