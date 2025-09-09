package com.groom.tennis_match.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.groom.tennis_match.common.constant.SuccessCode;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String status;
    private String code;
    private String message;
    private T data;
    private Object meta;
    private LocalDateTime timestamp;

    // 기본 성공 응답 메서드들
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .status("success")
                .code("SUCCESS")
                .data(data)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String message) {
        return ApiResponse.<T>builder()
                .status("success")
                .code("SUCCESS")
                .data(data)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> success(String code, String message) {
        return ApiResponse.<T>builder()
                .status("success")
                .code(code)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> success(T data, String code, String message) {
        return ApiResponse.<T>builder()
                .status("success")
                .code(code)
                .data(data)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }

    // SuccessCode를 사용하는 메서드들
    public static <T> ApiResponse<T> success(T data, SuccessCode successCode) {
        return ApiResponse.<T>builder()
                .status("success")
                .code(successCode.getCode())
                .data(data)
                .message(successCode.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

    public static <T> ApiResponse<T> success(SuccessCode successCode) {
        return ApiResponse.<T>builder()
                .status("success")
                .code(successCode.getCode())
                .message(successCode.getMessage())
                .timestamp(LocalDateTime.now())
                .build();
    }

}
