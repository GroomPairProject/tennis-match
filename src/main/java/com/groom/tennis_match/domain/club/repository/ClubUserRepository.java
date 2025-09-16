package com.groom.tennis_match.domain.club.repository;

import com.groom.tennis_match.domain.club.entity.ClubUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 클럽 사용자 관계 레포지토리
 * 
 * @author tennis-match
 */
@Repository
public interface ClubUserRepository extends JpaRepository<ClubUser, Long> {

    /**
     * 사용자 ID로 클럽 사용자 조회
     */
    Optional<ClubUser> findByUserId(Long userId);

    /**
     * 관리자 ID로 클럽 사용자 조회
     */
    Optional<ClubUser> findByAdminId(Long adminId);

    /**
     * 클럽 ID로 클럽 사용자 목록 조회
     */
    List<ClubUser> findByClubId(Long clubId);

    /**
     * 사용자 ID와 클럽 ID로 중복 확인
     */
    boolean existsByUserIdAndClubId(Long userId, Long clubId);

    /**
     * 관리자 ID와 클럽 ID로 중복 확인
     */
    boolean existsByAdminIdAndClubId(Long adminId, Long clubId);

    /**
     * 사용자 ID로 클럽 ID 조회
     */
    @Query("SELECT cu.clubId FROM ClubUser cu WHERE cu.userId = :userId")
    Optional<Long> findClubIdByUserId(@Param("userId") Long userId);

    /**
     * 관리자 ID로 클럽 ID 조회
     */
    @Query("SELECT cu.clubId FROM ClubUser cu WHERE cu.adminId = :adminId")
    Optional<Long> findClubIdByAdminId(@Param("adminId") Long adminId);
}



