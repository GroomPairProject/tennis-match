package com.groom.tennis_match.domain.stadium.dto;

import com.groom.tennis_match.domain.stadium.entity.Stadium;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StadiumDTO {
    private Long id;
    private String name;
    private String region;
    private String address;
    private String detailAddress;
    private String placeType;
    private Boolean isIndoor;
    private Boolean isOutdoor;
    private String manager;
    private String managerPhoneNumber;
    private LocalDateTime createdAt;
    private Long createdBy;
    private LocalDateTime updatedAt;
    private Long updatedBy;

    public static StadiumDTO from(Stadium entity) {
        StadiumDTO dto = new StadiumDTO();
        dto.setId(entity.getId());
        dto.setName(entity.getName());
        dto.setRegion(entity.getRegion());
        dto.setAddress(entity.getAddress());
        dto.setDetailAddress(entity.getDetailAddress());
        dto.setPlaceType(entity.getPlaceType());
        dto.setIsIndoor(entity.getIsIndoor());
        dto.setIsOutdoor(entity.getIsOutdoor());
        dto.setManager(entity.getManager());
        dto.setManagerPhoneNumber(entity.getManagerPhoneNumber());
        dto.setCreatedAt(entity.getCreatedAt());
        dto.setCreatedBy(entity.getCreatedBy());
        dto.setUpdatedAt(entity.getUpdatedAt());
        dto.setUpdatedBy(entity.getUpdatedBy());
        return dto;
    }
}
