package com.groom.tennis_match.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SuccessCode {
    
    // 인증 관련
    LOGIN_SUCCESS("S001", "로그인 성공했습니다"),
    LOGOUT_SUCCESS("S002", "로그아웃 성공했습니다"),
    SIGNUP_SUCCESS("S003", "회원가입이 성공적으로 완료되었습니다"),
    PASSWORD_CHANGE_SUCCESS("S004", "비밀번호가 성공적으로 변경되었습니다"),
    
    // 사용자 관련
    USER_CREATE_SUCCESS("S101", "사용자가 성공적으로 생성되었습니다"),
    USER_UPDATE_SUCCESS("S102", "사용자 정보가 성공적으로 수정되었습니다"),
    USER_DELETE_SUCCESS("S103", "사용자가 성공적으로 삭제되었습니다"),
    USER_READ_SUCCESS("S104", "사용자 정보를 성공적으로 조회했습니다"),
    
    // 카테고리 관련
    CATEGORY_CREATE_SUCCESS("S201", "카테고리가 성공적으로 생성되었습니다"),
    CATEGORY_UPDATE_SUCCESS("S202", "카테고리가 성공적으로 수정되었습니다"),
    CATEGORY_DELETE_SUCCESS("S203", "카테고리가 성공적으로 삭제되었습니다"),
    CATEGORY_READ_SUCCESS("S204", "카테고리 정보를 성공적으로 조회했습니다"),
    
    // 매치 관련
    MATCH_CREATE_SUCCESS("S301", "매치가 성공적으로 생성되었습니다"),
    MATCH_UPDATE_SUCCESS("S302", "매치 정보가 성공적으로 수정되었습니다"),
    MATCH_DELETE_SUCCESS("S303", "매치가 성공적으로 삭제되었습니다"),
    MATCH_READ_SUCCESS("S304", "매치 정보를 성공적으로 조회했습니다"),
    MATCH_JOIN_SUCCESS("S305", "매치 참가가 성공적으로 완료되었습니다"),
    MATCH_LEAVE_SUCCESS("S306", "매치 참가 취소가 성공적으로 완료되었습니다"),
    
    // 일반적인 성공
    OPERATION_SUCCESS("S000", "작업이 성공적으로 완료되었습니다"),
    DATA_SAVE_SUCCESS("S001", "데이터가 성공적으로 저장되었습니다"),
    DATA_UPDATE_SUCCESS("S002", "데이터가 성공적으로 수정되었습니다"),
    DATA_DELETE_SUCCESS("S003", "데이터가 성공적으로 삭제되었습니다"),
    DATA_READ_SUCCESS("S004", "데이터를 성공적으로 조회했습니다");
    
    private final String code;
    private final String message;
    
    /**
     * 성공 코드로 SuccessCode를 찾는 메서드
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
