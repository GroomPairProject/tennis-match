-- clubs 테이블에서 creator, editor 컬럼 제거
-- 자신이 속한 협회 정보를 등록하는 것이므로 등록자/수정자 정보 불필요

-- 1. created_by 컬럼 제거
ALTER TABLE clubs DROP COLUMN created_by;

-- 2. updated_by 컬럼 제거  
ALTER TABLE clubs DROP COLUMN updated_by;

-- 변경 후 clubs 테이블 구조 확인
DESCRIBE clubs;
