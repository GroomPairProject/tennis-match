package com.groom.tennis_match.domain.court.dto;

import com.groom.tennis_match.domain.court.enums.CourtState;
import com.groom.tennis_match.domain.court.enums.SurfaceType;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourtUpdateDTO {
    @NotNull
    private CourtState state;

    @NotNull
    private SurfaceType surfaceType;

}
