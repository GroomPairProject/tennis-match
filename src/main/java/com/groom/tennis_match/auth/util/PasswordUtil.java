package com.groom.tennis_match.auth.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

/**
 * 비밀번호 관련 유틸리티 클래스
 */
@Slf4j
@Component
public class PasswordUtil {

    private static final String UPPER_CASE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String LOWER_CASE = "abcdefghijklmnopqrstuvwxyz";
    private static final String NUMBERS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%^&*";
    private static final String ALL_CHARS = UPPER_CASE + LOWER_CASE + NUMBERS + SPECIAL_CHARS;
    
    private static final int DEFAULT_LENGTH = 12;
    private static final int MIN_UPPER = 2;
    private static final int MIN_LOWER = 2;
    private static final int MIN_NUMBERS = 2;
    private static final int MIN_SPECIAL = 1;

    private final Random random = new SecureRandom();

    /**
     * 기본 길이(12자리)의 임시 비밀번호를 생성합니다.
     * @return 생성된 임시 비밀번호
     */
    public String generateTemporaryPassword() {
        return generateTemporaryPassword(DEFAULT_LENGTH);
    }

    /**
     * 지정된 길이의 임시 비밀번호를 생성합니다.
     * @param length 비밀번호 길이 (최소 8자리)
     * @return 생성된 임시 비밀번호
     */
    public String generateTemporaryPassword(int length) {
        if (length < 8) {
            throw new IllegalArgumentException("비밀번호 길이는 최소 8자리 이상이어야 합니다.");
        }

        StringBuilder password = new StringBuilder();
        
        // 필수 문자들 추가
        password.append(getRandomChars(UPPER_CASE, MIN_UPPER));
        password.append(getRandomChars(LOWER_CASE, MIN_LOWER));
        password.append(getRandomChars(NUMBERS, MIN_NUMBERS));
        password.append(getRandomChars(SPECIAL_CHARS, MIN_SPECIAL));
        
        // 나머지 길이만큼 랜덤 문자 추가
        int remainingLength = length - (MIN_UPPER + MIN_LOWER + MIN_NUMBERS + MIN_SPECIAL);
        if (remainingLength > 0) {
            password.append(getRandomChars(ALL_CHARS, remainingLength));
        }
        
        // 비밀번호 섞기
        return shuffleString(password.toString());
    }

    /**
     * 지정된 문자 집합에서 랜덤하게 문자를 선택합니다.
     * @param chars 문자 집합
     * @param count 선택할 문자 개수
     * @return 선택된 문자들
     */
    private String getRandomChars(String chars, int count) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < count; i++) {
            result.append(chars.charAt(random.nextInt(chars.length())));
        }
        return result.toString();
    }

    /**
     * 문자열을 랜덤하게 섞습니다.
     * @param str 섞을 문자열
     * @return 섞인 문자열
     */
    private String shuffleString(String str) {
        char[] chars = str.toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char temp = chars[i];
            chars[i] = chars[j];
            chars[j] = temp;
        }
        return new String(chars);
    }

    /**
     * 임시 비밀번호 생성 로그를 남깁니다.
     * @param username 사용자명
     */
    public void logPasswordGeneration(String username) {
        log.info("임시 비밀번호가 생성되었습니다. 사용자: {}", username);
    }
}
