package com.groom.tennis_match.domain.court.enums;

public enum CourtState {
    AVAILABLE("사용 가능"),    // 사용 가능
    IN_USE("사용 중"),       // 사용 중
    MAINTENANCE("정비/점검 중"),  // 정비/점검 중
    CLOSED("폐쇄/사용 불가");        // 폐쇄/사용 불가

    private String label;

    CourtState(String label) {
        this.label = label;
    }

    public String getLabelName() {
        return label;
    }
}
