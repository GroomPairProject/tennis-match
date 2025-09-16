package com.groom.tennis_match.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * API 에러 응답을 위한 코드와 메시지를 정의하는 enum
 * HTTP 상태 코드와 함께 사용되는 표준화된 에러 코드
 */
@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // ==================== 공통 에러 코드 ====================
    /** 잘못된 입력값 */
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력값입니다."),
    /** 허용되지 않은 HTTP 메서드 */
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", "허용되지 않은 HTTP 메서드입니다."),
    /** 엔티티를 찾을 수 없음 */
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "C003", "해당 엔티티를 찾을 수 없습니다."),
    /** 서버 내부 오류 */
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C004", "서버 내부 오류가 발생했습니다."),
    /** 잘못된 타입 */
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C005", "잘못된 타입입니다."),
    /** 접근 거부 */
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "C006", "접근이 거부되었습니다."),

    // ==================== 인증/인가 관련 ====================
    /** 인증 필요 */
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A001", "인증이 필요합니다."),
    /** 유효하지 않은 토큰 */
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A002", "유효하지 않은 토큰입니다."),
    /** 만료된 토큰 */
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "A003", "만료된 토큰입니다."),
    /** 권한 없음 */
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "A004", "권한이 없습니다."),
    /** 로그인 실패 */
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "A005", "아이디 혹은 비밀번호가 잘못 입력되었습니다."),
    /** 계정 잠김 */
    ACCOUNT_LOCKED(HttpStatus.LOCKED, "A006", "계정이 잠겨있습니다."),
    /** 비활성화된 계정 */
    ACCOUNT_DISABLED(HttpStatus.FORBIDDEN, "A007", "비활성화된 계정입니다."),

    // ==================== 사용자 관련 ====================
    /** 사용자를 찾을 수 없음 */
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "사용자를 찾을 수 없습니다."),
    /** 이메일 중복 */
    EMAIL_DUPLICATION(HttpStatus.CONFLICT, "U002", "이미 사용 중인 이메일입니다."),
    /** 잘못된 비밀번호 */
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "U003", "잘못된 비밀번호입니다."),
    /** 이미 삭제된 사용자 */
    USER_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "U004", "이미 삭제된 사용자입니다."),
    /** 사용자 생성 실패 */
    USER_CREATE_FAILED(HttpStatus.BAD_REQUEST, "U005", "사용자 생성에 실패했습니다."),
    /** 사용자 수정 실패 */
    USER_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "U006", "사용자 정보 수정에 실패했습니다."),
    /** 사용자 삭제 실패 */
    USER_DELETE_FAILED(HttpStatus.BAD_REQUEST, "U007", "사용자 삭제에 실패했습니다."),

    // ==================== 카테고리 관련 ====================
    /** 카테고리를 찾을 수 없음 */
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "C101", "카테고리를 찾을 수 없습니다."),
    /** 카테고리 이름 중복 */
    CATEGORY_NAME_DUPLICATION(HttpStatus.CONFLICT, "C102", "이미 존재하는 카테고리 이름입니다."),
    /** 카테고리 생성 실패 */
    CATEGORY_CREATE_FAILED(HttpStatus.BAD_REQUEST, "C103", "카테고리 생성에 실패했습니다."),
    /** 카테고리 수정 실패 */
    CATEGORY_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "C104", "카테고리 수정에 실패했습니다."),
    /** 카테고리 삭제 실패 */
    CATEGORY_DELETE_FAILED(HttpStatus.BAD_REQUEST, "C105", "카테고리 삭제에 실패했습니다."),

    // ==================== 클럽(협회) 관련 ====================
    /** 클럽을 찾을 수 없음 */
    CLUB_NOT_FOUND(HttpStatus.NOT_FOUND, "CL001", "협회 정보 없음"),
    /** 클럽 생성 실패 */
    CLUB_CREATE_FAILED(HttpStatus.BAD_REQUEST, "CL002", "클럽 생성에 실패했습니다."),
    /** 클럽 수정 실패 */
    CLUB_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "CL003", "클럽 정보 수정에 실패했습니다."),
    /** 클럽 삭제 실패 */
    CLUB_DELETE_FAILED(HttpStatus.BAD_REQUEST, "CL004", "클럽 삭제에 실패했습니다."),
    /** 이미 클럽에 가입됨 */
    CLUB_ALREADY_JOINED(HttpStatus.CONFLICT, "CL005", "이미 클럽에 가입되어 있습니다."),
    /** 클럽에 가입되지 않음 */
    CLUB_NOT_JOINED(HttpStatus.BAD_REQUEST, "CL006", "클럽에 가입되어 있지 않습니다."),

    // ==================== 매치 관련 ====================
    /** 매치를 찾을 수 없음 */
    MATCH_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "매치를 찾을 수 없습니다."),
    /** 매치 생성 실패 */
    MATCH_CREATE_FAILED(HttpStatus.BAD_REQUEST, "M002", "매치 생성에 실패했습니다."),
    /** 매치 수정 실패 */
    MATCH_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "M003", "매치 정보 수정에 실패했습니다."),
    /** 매치 삭제 실패 */
    MATCH_DELETE_FAILED(HttpStatus.BAD_REQUEST, "M004", "매치 삭제에 실패했습니다."),
    /** 매치 참가 실패 */
    MATCH_JOIN_FAILED(HttpStatus.BAD_REQUEST, "M005", "매치 참가에 실패했습니다."),
    /** 매치 참가 취소 실패 */
    MATCH_LEAVE_FAILED(HttpStatus.BAD_REQUEST, "M006", "매치 참가 취소에 실패했습니다."),
    /** 매치가 가득 참 */
    MATCH_FULL(HttpStatus.BAD_REQUEST, "M007", "매치가 가득 찼습니다."),
    /** 이미 참가한 매치 */
    MATCH_ALREADY_JOINED(HttpStatus.CONFLICT, "M008", "이미 참가한 매치입니다."),
    /** 참가하지 않은 매치 */
    MATCH_NOT_JOINED(HttpStatus.BAD_REQUEST, "M009", "참가하지 않은 매치입니다."),

    // ==================== 검증 관련 ====================
    /** 입력값 검증 실패 */
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "V001", "입력값 검증에 실패했습니다."),
    /** 필수 필드 누락 */
    REQUIRED_FIELD_MISSING(HttpStatus.BAD_REQUEST, "V002", "필수 필드가 누락되었습니다."),
    /** 잘못된 형식 */
    INVALID_FORMAT(HttpStatus.BAD_REQUEST, "V003", "잘못된 형식입니다."),
    /** 필드 길이 초과 */
    FIELD_TOO_LONG(HttpStatus.BAD_REQUEST, "V004", "필드 길이가 너무 깁니다."),
    /** 필드 길이 부족 */
    FIELD_TOO_SHORT(HttpStatus.BAD_REQUEST, "V005", "필드 길이가 너무 짧습니다."),

    // ==================== 외부 서비스 관련 ====================
    /** 외부 API 호출 실패 */
    EXTERNAL_API_ERROR(HttpStatus.BAD_GATEWAY, "E001", "외부 API 호출에 실패했습니다."),
    /** 데이터베이스 오류 */
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E002", "데이터베이스 오류가 발생했습니다."),
    /** 파일 업로드 실패 */
    FILE_UPLOAD_FAILED(HttpStatus.BAD_REQUEST, "E003", "파일 업로드에 실패했습니다."),
    /** 파일 다운로드 실패 */
    FILE_DOWNLOAD_FAILED(HttpStatus.BAD_REQUEST, "E004", "파일 다운로드에 실패했습니다.");

    /** HTTP 상태 코드 */
    private final HttpStatus httpStatus;
    /** 에러 코드 */
    private final String code;
    /** 에러 메시지 */
    private final String message;
    
    /**
     * 에러 코드로 ErrorCode를 찾는 메서드
     * @param code 찾을 에러 코드
     * @return 해당하는 ErrorCode
     * @throws IllegalArgumentException 존재하지 않는 코드인 경우
     */
    public static ErrorCode fromCode(String code) {
        for (ErrorCode errorCode : values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }
        throw new IllegalArgumentException("Unknown error code: " + code);
    }
}
