package com.groom.tennis_match.domain.court.dto;

import com.groom.tennis_match.domain.court.entity.Court;
import com.groom.tennis_match.domain.court.enums.CourtState;
import com.groom.tennis_match.domain.court.enums.SurfaceType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourtDTO {
    private Long id;
    private Integer number;
    private CourtState state;
    private SurfaceType surfaceType;
    private Long stadiumId;
    private String stadiumName;

    public static CourtDTO from(Court court) {
        if (court == null) return null;
        Long sid = null;
        String sname = null;
        if (court.getStadium() != null) {
            sid = court.getStadium().getId();
            sname = court.getStadium().getName();
        }
        return CourtDTO.builder()
                .id(court.getId())
                .number(court.getNumber())
                .state(court.getState())
                .surfaceType(court.getSurfaceType())
                .stadiumId(sid)
                .stadiumName(sname)
                .build();
    }
}
