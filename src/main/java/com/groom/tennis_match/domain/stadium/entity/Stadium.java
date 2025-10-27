package com.groom.tennis_match.domain.stadium.entity;

import com.groom.tennis_match.domain.stadium.dto.StadiumCreateDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.NumberFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "stadium")
public class Stadium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stadium_id", nullable = false)
    private Long id;

    @Size(max = 20)
    @NotNull
    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Size(max = 20)
    @NotNull
    @Column(name = "region", nullable = false, length = 20)
    private String region;

    @Size(max = 30)
    @NotNull
    @Column(name = "address", nullable = false, length = 30)
    private String address;

    @Size(max = 30)
    @Column(name = "detail_address", length = 30)
    private String detailAddress;

    @Size(max = 30)
    @Column(name = "place_type", length = 30)
    private String placeType;

    @Column(name = "is_indoor")
    private Boolean isIndoor;

    @Column(name = "is_outdoor")
    private Boolean isOutdoor;

    @Size(max = 10)
    @Column(name = "manager", length = 10)
    private String manager;

    @Size(max = 15)
    @NumberFormat(pattern = "###########")
    @Column(name = "manager_phone_number", length = 15)
    private String managerPhoneNumber;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "updated_by")
    private Long updatedBy;

    /**
     * DTO로부터 엔티티 필드를 갱신하고 updatedAt, updatedBy를 설정한다.
     * DTO는 null 체크 후 사용한다.
     */
    public void updateStadium(StadiumCreateDTO dto) {
        if (dto == null) {
            return;
        }
        this.name = dto.getName();
        this.region = dto.getRegion();
        this.address = dto.getAddress();
        this.detailAddress = dto.getDetailAddress();
        this.placeType = dto.getPlaceType();
        this.isIndoor = dto.getIsIndoor();
        this.isOutdoor = dto.getIsOutdoor();
        this.manager = dto.getManager();
        this.managerPhoneNumber = dto.getManagerPhoneNumber();
        this.updatedAt = LocalDateTime.now();
    }
}