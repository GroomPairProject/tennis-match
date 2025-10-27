package com.groom.tennis_match.domain.court.entity;

import com.groom.tennis_match.domain.court.dto.CourtUpdateDTO;
import com.groom.tennis_match.domain.court.enums.CourtState;
import com.groom.tennis_match.domain.court.enums.SurfaceType;
import com.groom.tennis_match.domain.stadium.entity.Stadium;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "courts")
public class Court {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "court_id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "number", nullable = false)
    private Integer number;

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(name = "state", nullable = false, length = 20)
    private CourtState state;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "surface_type", nullable = false, length = 20)
    private SurfaceType surfaceType;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "stadium_id", nullable = false)
    private Stadium stadium;

    /**
     * DTO로부터 엔티티 필드를 갱신하고 updatedAt을 설정한다.
     * stadium이 제공되면 연관관계도 교체한다.
     */
    public void updateCourt(CourtUpdateDTO dto) {
        if (dto == null) {
            return;
        }
        this.state = dto.getState();
        this.surfaceType = dto.getSurfaceType();
        //this.updatedAt = LocalDateTime.now();
    }

}