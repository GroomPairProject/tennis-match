package com.groom.tennis_match.common.constant;

// ================================
// 4. 공통 상수
// ================================
public class CommonConstant {
    // API 버전
    public static final String API_V1 = "/api/v1";

    // 페이징 기본값
    public static final int DEFAULT_PAGE_SIZE = 20;
    public static final int MAX_PAGE_SIZE = 100;

    // 날짜 포맷
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String DATETIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    // 인코딩
    public static final String UTF_8 = "UTF-8";

    // 헤더
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String BEARER_PREFIX = "Bearer ";

    // 캐시 시간
    public static final long CACHE_EXPIRE_SECONDS = 300; // 5분
}
