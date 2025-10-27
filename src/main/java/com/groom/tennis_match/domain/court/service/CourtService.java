package com.groom.tennis_match.domain.court.service;

import com.groom.tennis_match.domain.court.dto.CourtCreateDTO;
import com.groom.tennis_match.domain.court.dto.CourtDTO;
import com.groom.tennis_match.domain.court.dto.CourtSearchDTO;
import com.groom.tennis_match.domain.court.dto.CourtUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CourtService {
    CourtDTO create(CourtCreateDTO dto);
    CourtDTO findById(Long id);
    Page<CourtDTO> findAll(Pageable pageable);
    Page<CourtDTO> findAll(CourtSearchDTO searchDTO, Pageable pageable);
    CourtDTO update(Long id, CourtUpdateDTO dto);
    void delete(Long id);
}
