package com.groom.tennis_match.domain.stadium.service;

import com.groom.tennis_match.domain.stadium.dto.StadiumCreateDTO;
import com.groom.tennis_match.domain.stadium.dto.StadiumDTO;
import com.groom.tennis_match.domain.stadium.dto.StadiumSearchDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StadiumService {
    StadiumDTO create(StadiumCreateDTO dto);
    StadiumDTO findById(Long id);
    Page<StadiumDTO> findAll(Pageable pageable);
    Page<StadiumDTO> findAll(StadiumSearchDTO searchDTO, Pageable pageable);
    StadiumDTO update(Long id, StadiumCreateDTO dto);
    void delete(Long id);
}
