-- 관리자 계정 발급: 최고 권한(ADMIN) 부여 예시
INSERT INTO admins
(username, name, phone, email, is_active, password, password_miss, is_lock, profile_img_url, role,
 created_at, created_by, updated_at, updated_by)
VALUES
    ('admin001', '홍길동', '010-1234-5678', 'honggildong@example.com',
     1,
        -- 'Temp@1234'의 bcrypt 예시 해시 (샘플) → 실제 운영에선 애플리케이션에서 생성한 해시로 교체
     '$2a$10$yIY2n3Vn0g7HkQW8D4w3QOgYp8cA7djuE0N7cHj1H9zZCk1s8qk.e',
     0,
     0,
     NULL,
     'ADMIN',
     NOW(), 0, NOW(), 0);
