package com.groom.tennis_match.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * API 에러 응답을 위한 표준화된 DTO 클래스
 * GlobalExceptionHandler에서 사용되는 에러 응답 구조
 * 
 * @see com.groom.tennis_match.common.exception.GlobalExceptionHandler
 */
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {
    
    /** 에러 코드 */
    private String errorCode;
    
    /** 에러 메시지 */
    private String message;
    
    /** 상세 에러 정보 */
    private String detail;
    
    /** 에러 발생 시간 */
    private LocalDateTime timestamp;
    
    /** 에러 발생 경로 */
    private String path;
    
    /** 필드별 검증 에러 목록 */
    private List<FieldError> fieldErrors;

    /**
     * 필드별 검증 에러를 나타내는 내부 클래스
     */
    @Getter
    @Builder
    public static class FieldError {
        /** 에러가 발생한 필드명 */
        private String field;
        /** 거부된 값 */
        private Object rejectedValue;
        /** 필드별 에러 메시지 */
        private String message;
    }

    // ==================== 정적 팩토리 메서드들 ====================
    
    /**
     * 기본 에러 응답 생성
     * @param errorCode 에러 코드
     * @param message 에러 메시지
     * @return ErrorResponse
     */
    public static ErrorResponse of(String errorCode, String message) {
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * 경로 정보가 포함된 에러 응답 생성
     * @param errorCode 에러 코드
     * @param message 에러 메시지
     * @param path 에러 발생 경로
     * @return ErrorResponse
     */
    public static ErrorResponse of(String errorCode, String message, String path) {
        return ErrorResponse.builder()
                .errorCode(errorCode)
                .message(message)
                .path(path)
                .timestamp(LocalDateTime.now())
                .build();
    }
}
