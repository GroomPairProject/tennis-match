package com.groom.tennis_match.common.exception;

import com.groom.tennis_match.common.constant.ErrorCode;
import com.groom.tennis_match.common.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 비즈니스 로직 예외
     */
    @ExceptionHandler(BusinessException.class)
    protected ResponseEntity<ErrorResponse> handleBusinessException(
            BusinessException e, HttpServletRequest request) {
        log.error("BusinessException: {}", e.getMessage(), e);

        ErrorCode errorCode = e.getErrorCode();
        ErrorResponse response = ErrorResponse.builder()
                .errorCode(errorCode.getCode())
                .message(e.getMessage())
                .path(request.getRequestURI())
                .timestamp(java.time.LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(response);
    }

    /**
     * @Valid 유효성 검사 실패
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(
            MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("MethodArgumentNotValidException: {}", e.getMessage());

        List<ErrorResponse.FieldError> fieldErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> ErrorResponse.FieldError.builder()
                        .field(error.getField())
                        .rejectedValue(error.getRejectedValue())
                        .message(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        ErrorResponse response = ErrorResponse.builder()
                .errorCode(ErrorCode.INVALID_INPUT_VALUE.getCode())
                .message(ErrorCode.INVALID_INPUT_VALUE.getMessage())
                .fieldErrors(fieldErrors)
                .path(request.getRequestURI())
                .timestamp(java.time.LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(ErrorCode.INVALID_INPUT_VALUE.getHttpStatus())
                .body(response);
    }

    /**
     * @ModelAttribute 바인딩 예외
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ErrorResponse> handleBindException(
            BindException e, HttpServletRequest request) {
        log.error("BindException: {}", e.getMessage());

        List<ErrorResponse.FieldError> fieldErrors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> ErrorResponse.FieldError.builder()
                        .field(error.getField())
                        .rejectedValue(error.getRejectedValue())
                        .message(error.getDefaultMessage())
                        .build())
                .collect(Collectors.toList());

        ErrorResponse response = ErrorResponse.builder()
                .errorCode(ErrorCode.INVALID_INPUT_VALUE.getCode())
                .message(ErrorCode.INVALID_INPUT_VALUE.getMessage())
                .fieldErrors(fieldErrors)
                .path(request.getRequestURI())
                .timestamp(java.time.LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    /**
     * 타입 미스매치 예외
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<ErrorResponse> handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException e, HttpServletRequest request) {
        log.error("MethodArgumentTypeMismatchException: {}", e.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .errorCode(ErrorCode.INVALID_TYPE_VALUE.getCode())
                .message(ErrorCode.INVALID_TYPE_VALUE.getMessage())
                .detail(String.format("'%s' 파라미터의 값 '%s'이(가) 올바르지 않습니다.",
                        e.getName(), e.getValue()))
                .path(request.getRequestURI())
                .timestamp(java.time.LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

    /**
     * 지원하지 않는 HTTP 메서드 예외
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ErrorResponse> handleHttpRequestMethodNotSupportedException(
            HttpRequestMethodNotSupportedException e, HttpServletRequest request) {
        log.error("HttpRequestMethodNotSupportedException: {}", e.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .errorCode(ErrorCode.METHOD_NOT_ALLOWED.getCode())
                .message(ErrorCode.METHOD_NOT_ALLOWED.getMessage())
                .detail(String.format("지원하지 않는 HTTP 메서드입니다. 지원하는 메서드: %s",
                        String.join(", ", e.getSupportedMethods())))
                .path(request.getRequestURI())
                .timestamp(java.time.LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(ErrorCode.METHOD_NOT_ALLOWED.getHttpStatus())
                .body(response);
    }

    /**
     * 핸들러를 찾을 수 없는 예외 (404)
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    protected ResponseEntity<ErrorResponse> handleNoHandlerFoundException(
            NoHandlerFoundException e, HttpServletRequest request) {
        log.error("NoHandlerFoundException: {}", e.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .errorCode(ErrorCode.ENTITY_NOT_FOUND.getCode())
                .message("요청한 리소스를 찾을 수 없습니다.")
                .path(request.getRequestURI())
                .timestamp(java.time.LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(ErrorCode.ENTITY_NOT_FOUND.getHttpStatus())
                .body(response);
    }

    /**
     * 접근 거부 예외
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<ErrorResponse> handleAccessDeniedException(
            AccessDeniedException e, HttpServletRequest request) {
        log.error("AccessDeniedException: {}", e.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .errorCode(ErrorCode.HANDLE_ACCESS_DENIED.getCode())
                .message(ErrorCode.HANDLE_ACCESS_DENIED.getMessage())
                .path(request.getRequestURI())
                .timestamp(java.time.LocalDateTime.now())
                .build();

        return ResponseEntity
                .status(ErrorCode.HANDLE_ACCESS_DENIED.getHttpStatus())
                .body(response);
    }

    /**
     * JSON 파싱 오류
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    protected ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(
            HttpMessageNotReadableException e, HttpServletRequest request) {
        log.error("HttpMessageNotReadableException: {}", e.getMessage());

        ErrorResponse response = ErrorResponse.builder()
                .errorCode(ErrorCode.INVALID_INPUT_VALUE.getCode())
                .message("올바르지 않은 JSON 형식입니다.")
                .path(request.getRequestURI())
                .timestamp(java.time.LocalDateTime.now())
                .build();

        return ResponseEntity.badRequest().body(response);
    }

        /**
         * 기타 모든 예외
         */
        @ExceptionHandler(Exception.class)
        protected ResponseEntity<ErrorResponse> handleException(
                Exception e, HttpServletRequest request) {
            log.error("Exception: {}", e.getMessage(), e);

            ErrorResponse response = ErrorResponse.builder()
                    .errorCode(ErrorCode.INTERNAL_SERVER_ERROR.getCode())
                    .message(ErrorCode.INTERNAL_SERVER_ERROR.getMessage())
                    .path(request.getRequestURI())
                    .timestamp(java.time.LocalDateTime.now())
                    .build();

            return ResponseEntity
                    .status(ErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                    .body(response);
        }
}
