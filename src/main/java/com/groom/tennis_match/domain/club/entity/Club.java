package com.groom.tennis_match.domain.club.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 클럽(협회) 엔티티
 * 
 * @author tennis-match
 */
@Entity
@Table(name = "clubs")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Club {

    /** 클럽 ID */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "club_id")
    private Long clubId;

    /** 클럽명 */
    @Column(name = "club_name", length = 20)
    private String clubName;

    /** 도시 */
    @Column(name = "city")
    private String city;

    /** 구/군 */
    @Column(name = "district")
    private String district;

    /** 상세 주소 */
    @Column(name = "address_detail")
    private String addressDetail;

    /** 클럽 성별 (0: 남성, 1: 여성, 2: 혼성) */
    @Column(name = "club_gender")
    private Integer clubGender;

    /** 카테고리 */
    @Column(name = "category", length = 20)
    private String category;

    /** 클럽 구분 */
    @Column(name = "club_div", length = 10)
    private String clubDiv;

    /** 대표자명 */
    @Column(name = "representative_name", length = 20)
    private String representativeName;

    /** 활성화 여부 */
    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    /** 생성일 */
    @Column(name = "create_date")
    private LocalDate createDate;

    /** 생성 시간 */
    @Column(name = "create_at")
    private LocalDateTime createAt;

    /** 생성자 */
    @Column(name = "creator")
    private Long creator;

    /** 수정 시간 */
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    /** 수정자 */
    @Column(name = "editor")
    private Long editor;

    /**
     * 클럽 정보 수정
     */
    public void updateClub(String clubName, String city, String district, String addressDetail,
                          Integer clubGender, String category, String clubDiv,
                          String representativeName, Boolean isActive, Long editor) {
        this.clubName = clubName;
        this.city = city;
        this.district = district;
        this.addressDetail = addressDetail;
        this.clubGender = clubGender;
        this.category = category;
        this.clubDiv = clubDiv;
        this.representativeName = representativeName;
        this.isActive = isActive;
        this.updatedAt = LocalDateTime.now();
        this.editor = editor;
    }

    /**
     * 클럽 활성화 상태 변경
     */
    public void changeActiveStatus(Boolean isActive, Long editor) {
        this.isActive = isActive;
        this.updatedAt = LocalDateTime.now();
        this.editor = editor;
    }
}
