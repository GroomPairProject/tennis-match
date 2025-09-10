package com.groom.tennis_match.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.groom.tennis_match.common.constant.SuccessCode;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * API 성공 응답을 위한 표준화된 DTO 클래스
 * 모든 에러는 예외 처리로 GlobalExceptionHandler에서 ErrorResponse로 처리
 * 
 * @param <T> 응답 데이터의 타입
 */
@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    
    /** 응답 상태 (success, error) */
    private String status;
    
    /** 응답 코드 (성공/실패 코드) */
    private String code;
    
    /** 응답 메시지 */
    private String message;
    
    /** 응답 데이터 */
    private T data;
    
    /** 메타데이터 (페이징, 추가 정보 등) */
    private Object meta;
    
    /** 응답 생성 시간 */
    private LocalDateTime timestamp;

    // ==================== 성공 응답 메서드들 ====================
    
    /**
     * 데이터와 함께 기본 성공 응답 생성
     * @param data 응답 데이터
     * @return 성공 응답
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status("success")
                .code("SUCCESS")
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * 데이터와 커스텀 메시지로 성공 응답 생성
     * @param data 응답 데이터
     * @param message 커스텀 메시지
     * @return 성공 응답
     */
    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .status("success")
                .code("SUCCESS")
                .data(data)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * SuccessCode enum을 사용한 성공 응답 생성 (데이터 포함)
     * @param data 응답 데이터
     * @param successCode 성공 코드 enum
     * @return 성공 응답
     */
    public static <T> ApiResponse<T> success(T data, SuccessCode successCode) {
        return ApiResponse.<T>builder()
                .status("success")
                .code(successCode.getCode())
                .data(data)
                .message(successCode.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    /**
     * SuccessCode enum을 사용한 성공 응답 생성 (데이터 없음)
     * @param successCode 성공 코드 enum
     * @return 성공 응답
     */
    public static <T> ApiResponse<T> success(SuccessCode successCode) {
        return ApiResponse.<T>builder()
                .status("success")
                .code(successCode.getCode())
                .message(successCode.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }



}
