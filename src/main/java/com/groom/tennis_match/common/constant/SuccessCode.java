package com.groom.tennis_match.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * API 성공 응답을 위한 코드와 메시지를 정의하는 enum
 * 비즈니스 로직 성공 시 사용되는 표준화된 성공 코드
 */
@Getter
@RequiredArgsConstructor
public enum SuccessCode {
    
    // ==================== 공통 성공 코드 ====================
    /** 일반적인 작업 성공 */
    OPERATION_SUCCESS("S000", "작업이 성공적으로 완료되었습니다"),
    
    // ==================== 인증/인가 관련 ====================
    /** 로그인 성공 */
    LOGIN_SUCCESS("S001", "로그인 성공했습니다"),
    /** 로그아웃 성공 */
    LOGOUT_SUCCESS("S002", "로그아웃 성공했습니다"),
    /** 회원가입 성공 */
    SIGNUP_SUCCESS("S003", "회원가입이 성공적으로 완료되었습니다"),
    /** 비밀번호 변경 성공 */
    PASSWORD_CHANGE_SUCCESS("S004", "비밀번호가 성공적으로 변경되었습니다"),
    
    // ==================== 사용자 관련 ====================
    /** 사용자 생성 성공 */
    USER_CREATE_SUCCESS("S101", "사용자가 성공적으로 생성되었습니다"),
    /** 사용자 조회 성공 */
    USER_READ_SUCCESS("S102", "사용자 정보를 성공적으로 조회했습니다"),
    /** 사용자 수정 성공 */
    USER_UPDATE_SUCCESS("S103", "사용자 정보가 성공적으로 수정되었습니다"),
    /** 사용자 삭제 성공 */
    USER_DELETE_SUCCESS("S104", "사용자가 성공적으로 삭제되었습니다"),
    
    // ==================== 카테고리 관련 ====================
    /** 카테고리 생성 성공 */
    CATEGORY_CREATE_SUCCESS("S201", "카테고리가 성공적으로 생성되었습니다"),
    /** 카테고리 조회 성공 */
    CATEGORY_READ_SUCCESS("S202", "카테고리 정보를 성공적으로 조회했습니다"),
    /** 카테고리 수정 성공 */
    CATEGORY_UPDATE_SUCCESS("S203", "카테고리가 성공적으로 수정되었습니다"),
    /** 카테고리 삭제 성공 */
    CATEGORY_DELETE_SUCCESS("S204", "카테고리가 성공적으로 삭제되었습니다"),
    
    // ==================== 매치 관련 ====================
    /** 매치 생성 성공 */
    MATCH_CREATE_SUCCESS("S301", "매치가 성공적으로 생성되었습니다"),
    /** 매치 조회 성공 */
    MATCH_READ_SUCCESS("S302", "매치 정보를 성공적으로 조회했습니다"),
    /** 매치 수정 성공 */
    MATCH_UPDATE_SUCCESS("S303", "매치 정보가 성공적으로 수정되었습니다"),
    /** 매치 삭제 성공 */
    MATCH_DELETE_SUCCESS("S304", "매치가 성공적으로 삭제되었습니다"),
    /** 매치 참가 성공 */
    MATCH_JOIN_SUCCESS("S305", "매치 참가가 성공적으로 완료되었습니다"),
    /** 매치 참가 취소 성공 */
    MATCH_LEAVE_SUCCESS("S306", "매치 참가 취소가 성공적으로 완료되었습니다");
    
    /** 성공 코드 */
    private final String code;
    /** 성공 메시지 */
    private final String message;
    
    /**
     * 성공 코드로 SuccessCode를 찾는 메서드
     * @param code 찾을 성공 코드
     * @return 해당하는 SuccessCode
     * @throws IllegalArgumentException 존재하지 않는 코드인 경우
     */
    public static SuccessCode fromCode(String code) {
        for (SuccessCode successCode : values()) {
            if (successCode.getCode().equals(code)) {
                return successCode;
            }
        }
        throw new IllegalArgumentException("Unknown success code: " + code);
    }
}
