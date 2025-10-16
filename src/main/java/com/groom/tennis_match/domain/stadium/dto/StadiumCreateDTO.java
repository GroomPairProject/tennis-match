package com.groom.tennis_match.domain.stadium.dto;

import com.groom.tennis_match.domain.stadium.entity.Stadium;
import jakarta.validation.constraints.NotBlank;
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
public class StadiumCreateDTO {
    @NotBlank
    @Size(max = 20)
    private String name;

    @NotBlank
    @Size(max = 20)
    private String region;

    @NotBlank
    @Size(max = 30)
    private String address;

    @Size(max = 30)
    private String detailAddress;

    @Size(max = 30)
    private String placeType;

    @NotNull
    private Boolean isIndoor;

    @NotNull
    private Boolean isOutdoor;

    @Size(max = 10)
    private String manager;

    @Size(max = 15)
    private String managerPhoneNumber;

    public Stadium toEntity() {
        Stadium stadium = new Stadium();
        stadium.setName(this.name);
        stadium.setRegion(this.region);
        stadium.setAddress(this.address);
        stadium.setDetailAddress(this.detailAddress);
        stadium.setPlaceType(this.placeType);
        stadium.setIsIndoor(this.isIndoor);
        stadium.setIsOutdoor(this.isOutdoor);
        stadium.setManager(this.manager);
        stadium.setManagerPhoneNumber(this.managerPhoneNumber);
        return stadium;
    }
}
