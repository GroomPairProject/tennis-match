package com.groom.tennis_match.domain.court.enums;

public enum SurfaceType {
    HARD("하드"),        // 아크릴/콘크리트 계열
    CLAY("클레이"),        // 클레이
    GRASS("잔디"),       // 잔디
    OTHER("기타");        // 기타

    private final String label;

    SurfaceType(String label) {
        this.label = label;
    }

    public String getLabelName() {
        return label;
    }
}
