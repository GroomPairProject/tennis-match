package com.groom.tennis_match.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "C001", "잘못된 입력값입니다."),
    METHOD_NOT_ALLOWED(HttpStatus.METHOD_NOT_ALLOWED, "C002", "허용되지 않은 HTTP 메서드입니다."),
    ENTITY_NOT_FOUND(HttpStatus.NOT_FOUND, "C003", "해당 엔티티를 찾을 수 없습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "C004", "서버 내부 오류가 발생했습니다."),
    INVALID_TYPE_VALUE(HttpStatus.BAD_REQUEST, "C005", "잘못된 타입입니다."),
    HANDLE_ACCESS_DENIED(HttpStatus.FORBIDDEN, "C006", "접근이 거부되었습니다."),

    // Authentication & Authorization
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "A001", "인증이 필요합니다."),
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "A002", "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "A003", "만료된 토큰입니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "A004", "권한이 없습니다."),
    LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "A005", "아이디 혹은 비밀번호가 잘못 입력되었습니다."),
    ACCOUNT_LOCKED(HttpStatus.LOCKED, "A006", "계정이 잠겨있습니다."),
    ACCOUNT_DISABLED(HttpStatus.FORBIDDEN, "A007", "비활성화된 계정입니다."),

    // User
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "사용자를 찾을 수 없습니다."),
    EMAIL_DUPLICATION(HttpStatus.CONFLICT, "U002", "이미 사용 중인 이메일입니다."),
    INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "U003", "잘못된 비밀번호입니다."),
    USER_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "U004", "이미 삭제된 사용자입니다."),
    USER_CREATE_FAILED(HttpStatus.BAD_REQUEST, "U005", "사용자 생성에 실패했습니다."),
    USER_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "U006", "사용자 정보 수정에 실패했습니다."),
    USER_DELETE_FAILED(HttpStatus.BAD_REQUEST, "U007", "사용자 삭제에 실패했습니다."),

    // Category
    CATEGORY_NOT_FOUND(HttpStatus.NOT_FOUND, "C101", "카테고리를 찾을 수 없습니다."),
    CATEGORY_NAME_DUPLICATION(HttpStatus.CONFLICT, "C102", "이미 존재하는 카테고리 이름입니다."),
    CATEGORY_CREATE_FAILED(HttpStatus.BAD_REQUEST, "C103", "카테고리 생성에 실패했습니다."),
    CATEGORY_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "C104", "카테고리 수정에 실패했습니다."),
    CATEGORY_DELETE_FAILED(HttpStatus.BAD_REQUEST, "C105", "카테고리 삭제에 실패했습니다."),

    // Match
    MATCH_NOT_FOUND(HttpStatus.NOT_FOUND, "M001", "매치를 찾을 수 없습니다."),
    MATCH_CREATE_FAILED(HttpStatus.BAD_REQUEST, "M002", "매치 생성에 실패했습니다."),
    MATCH_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "M003", "매치 정보 수정에 실패했습니다."),
    MATCH_DELETE_FAILED(HttpStatus.BAD_REQUEST, "M004", "매치 삭제에 실패했습니다."),
    MATCH_JOIN_FAILED(HttpStatus.BAD_REQUEST, "M005", "매치 참가에 실패했습니다."),
    MATCH_LEAVE_FAILED(HttpStatus.BAD_REQUEST, "M006", "매치 참가 취소에 실패했습니다."),
    MATCH_FULL(HttpStatus.BAD_REQUEST, "M007", "매치가 가득 찼습니다."),
    MATCH_ALREADY_JOINED(HttpStatus.CONFLICT, "M008", "이미 참가한 매치입니다."),
    MATCH_NOT_JOINED(HttpStatus.BAD_REQUEST, "M009", "참가하지 않은 매치입니다."),

    // Validation
    VALIDATION_FAILED(HttpStatus.BAD_REQUEST, "V001", "입력값 검증에 실패했습니다."),
    REQUIRED_FIELD_MISSING(HttpStatus.BAD_REQUEST, "V002", "필수 필드가 누락되었습니다."),
    INVALID_FORMAT(HttpStatus.BAD_REQUEST, "V003", "잘못된 형식입니다."),
    FIELD_TOO_LONG(HttpStatus.BAD_REQUEST, "V004", "필드 길이가 너무 깁니다."),
    FIELD_TOO_SHORT(HttpStatus.BAD_REQUEST, "V005", "필드 길이가 너무 짧습니다."),

    // External Service
    EXTERNAL_API_ERROR(HttpStatus.BAD_GATEWAY, "E001", "외부 API 호출에 실패했습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "E002", "데이터베이스 오류가 발생했습니다."),
    FILE_UPLOAD_FAILED(HttpStatus.BAD_REQUEST, "E003", "파일 업로드에 실패했습니다."),
    FILE_DOWNLOAD_FAILED(HttpStatus.BAD_REQUEST, "E004", "파일 다운로드에 실패했습니다.");

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;
    
    /**
     * 에러 코드로 ErrorCode를 찾는 메서드
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
