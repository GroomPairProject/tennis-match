package com.groom.tennis_match.domain.club.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 사용자-클럽 관계 엔티티
 * 사용자가 속한 클럽 정보를 관리
 * 
 * @author tennis-match
 */
@Entity
@Table(name = "club_users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class ClubUser {

    /** 클럽 사용자 ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_user_id")
    private Long clubUserId;

    /** 사용자 ID */
    @Column(name = "user_id")
    private Long userId;

    /** 관리자 ID */
    @Column(name = "admin_id")
    private Long adminId;

    /** 클럽 ID */
    @Column(name = "club_id")
    private Long clubId;

    /** 클럽 엔티티와의 관계 */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id", insertable = false, updatable = false)
    private Club club;

    /**
     * 사용자 ID로 클럽 사용자 생성
     */
    public static ClubUser createUserClub(Long userId, Long clubId, Long adminId) {
        return ClubUser.builder()
                .userId(userId)
                .adminId(adminId)
                .clubId(clubId)
                .build();
    }

    /**
     * 관리자 ID로 클럽 사용자 생성
     */
    public static ClubUser createAdminClub(Long adminId, Long clubId) {
        return ClubUser.builder()
                .adminId(adminId)
                .clubId(clubId)
                .build();
    }
}



