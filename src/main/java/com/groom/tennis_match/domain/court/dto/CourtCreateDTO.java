package com.groom.tennis_match.domain.court.dto;

import com.groom.tennis_match.domain.court.entity.Court;
import com.groom.tennis_match.domain.court.enums.CourtState;
import com.groom.tennis_match.domain.court.enums.SurfaceType;
import com.groom.tennis_match.domain.stadium.entity.Stadium;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourtCreateDTO {
    @NotNull
    private Integer number;

    @NotNull
    private CourtState state;

    @NotNull
    private SurfaceType surfaceType;

    @NotNull
    private Long stadiumId;

    // Stadium 엔티티를 확보한 후 엔티티 생성에 사용
    public Court toEntity(Stadium stadium) {
        Court court = new Court();
        court.setNumber(this.number);
        court.setState(this.state);
        court.setSurfaceType(this.surfaceType);
        court.setStadium(stadium);
        return court;
    }
}
