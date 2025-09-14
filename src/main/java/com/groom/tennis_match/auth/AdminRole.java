package com.groom.tennis_match.auth;

public enum AdminRole {
    ADMIN(4), CHIEF_MANAGER(3), MANAGER(2), STAFF(1);

    private final int level;

    AdminRole(int level) {
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    /**
     * 현재 권한이 대상 권한보다 높거나 같은지 확인
     * @param targetRole 비교 대상 권한
     * @return true: 현재 권한이 더 높거나 같음, false: 더 낮음
     */
    public boolean canManage(AdminRole targetRole) {
        return this.level >= targetRole.level;
    }

    /**
     * 권한 레벨로 AdminRole 찾기
     * @param level 권한 레벨
     * @return 해당하는 AdminRole
     */
    public static AdminRole fromLevel(int level) {
        for (AdminRole role : values()) {
            if (role.level == level) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid role level: " + level);
    }
}
