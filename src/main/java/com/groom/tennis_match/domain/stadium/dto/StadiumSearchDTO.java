package com.groom.tennis_match.domain.stadium.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StadiumSearchDTO {
    private String region;
    private String keyword;
    private Boolean isIndoor;
    private Boolean isOutdoor;

    /**
     * 모든 검색 조건이 비어있으면 true 반환.
     * 서비스에서 전체 조회 vs 필터 조회 분기용으로 사용.
     */
    public boolean isEmpty() {
        boolean regionEmpty = (region == null) || region.isBlank();
        boolean keywordEmpty = (keyword == null) || keyword.isBlank();
        boolean indoorEmpty = (isIndoor == null);
        boolean outdoorEmpty = (isOutdoor == null);
        return regionEmpty && keywordEmpty && indoorEmpty && outdoorEmpty;
    }
}
