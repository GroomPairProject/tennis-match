package com.groom.tennis_match.domain.court.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourtSearchDTO {
    private Long stadiumId;      // 경기장으로 검색
    private String state;        // 상태로 검색 (예: available, closed)
    private String surfaceType;  // 코트 표면타입으로 검색
    private Integer number;      // 코트 번호로 검색 (선택)

    /**
     * 모든 검색 조건이 비어있으면 true 반환.
     * 서비스에서 전체 조회 vs 필터 조회 분기용으로 사용.
     */
    public boolean isEmpty() {
        boolean stadiumEmpty = (stadiumId == null);
        boolean stateEmpty = (state == null) || state.isBlank();
        boolean surfaceTypeEmpty = (surfaceType == null) || surfaceType.isBlank();
        boolean numberEmpty = (number == null);
        return stadiumEmpty && stateEmpty && surfaceTypeEmpty && numberEmpty;
    }
}
