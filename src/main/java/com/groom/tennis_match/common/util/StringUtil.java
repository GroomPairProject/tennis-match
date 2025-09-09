package com.groom.tennis_match.common.util;

import org.springframework.util.StringUtils;

public class StringUtil {
    public static boolean isBlank(String str) {
        return !StringUtils.hasText(str);
    }

    public static boolean isNotBlank(String str) {
        return StringUtils.hasText(str);
    }

    public static String mask(String str, int startIndex, int endIndex) {
        if (isBlank(str) || str.length() < endIndex) {
            return str;
        }

        StringBuilder sb = new StringBuilder(str);
        for (int i = startIndex; i < endIndex; i++) {
            sb.setCharAt(i, '*');
        }
        return sb.toString();
    }

    public static String maskEmail(String email) {
        if (isBlank(email) || !email.contains("@")) {
            return email;
        }

        String[] parts = email.split("@");
        String local = parts[0];
        String domain = parts[1];

        if (local.length() <= 2) {
            return email;
        }

        return local.substring(0, 2) + "***@" + domain;
    }
}
