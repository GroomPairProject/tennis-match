package com.groom.tennis_match.domain.court.service.impl;

import com.groom.tennis_match.common.constant.ErrorCode;
import com.groom.tennis_match.common.exception.BusinessException;
import com.groom.tennis_match.domain.court.dto.CourtCreateDTO;
import com.groom.tennis_match.domain.court.dto.CourtDTO;
import com.groom.tennis_match.domain.court.dto.CourtSearchDTO;
import com.groom.tennis_match.domain.court.dto.CourtUpdateDTO;
import com.groom.tennis_match.domain.court.entity.Court;
import com.groom.tennis_match.domain.court.repository.CourtRepository;
import com.groom.tennis_match.domain.court.repository.CourtSpecification;
import com.groom.tennis_match.domain.court.service.CourtService;
import com.groom.tennis_match.domain.stadium.entity.Stadium;
import com.groom.tennis_match.domain.stadium.repository.StadiumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CourtServiceImpl implements CourtService {

    private final CourtRepository courtRepository;
    private final StadiumRepository stadiumRepository;

    @Override
    public CourtDTO create(CourtCreateDTO dto) {
        validateCreateDto(dto);

        if (courtRepository.existsByStadiumIdAndNumber(dto.getStadiumId(), dto.getNumber())) {
            throw new BusinessException(ErrorCode.COURT_DUPLICATION);
        }

        Stadium stadium = stadiumRepository.findById(dto.getStadiumId())
                .orElseThrow(() -> new BusinessException(ErrorCode.STADIUM_NOT_FOUND));

        Court court = dto.toEntity(stadium);
        Court saved = courtRepository.save(court);
        return CourtDTO.from(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CourtDTO findById(Long id) {
        Court court = courtRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.COURT_NOT_FOUND));
        return CourtDTO.from(court);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourtDTO> findAll(Pageable pageable) {
        return courtRepository.findAll(pageable).map(CourtDTO::from);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CourtDTO> findAll(CourtSearchDTO searchDTO, Pageable pageable) {
        if (searchDTO == null || searchDTO.isEmpty()) {
            return findAll(pageable);
        }
        var spec = CourtSpecification.toSpecification(searchDTO);
        if (spec == null) {
            return findAll(pageable);
        }
        return courtRepository.findAll(spec, pageable).map(CourtDTO::from);
    }

    @Override
    public CourtDTO update(Long id, CourtUpdateDTO dto) {
        validateUpdateDto(dto);

        // 기존 코트 여부 있는지 확인
        Court court = courtRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.COURT_NOT_FOUND));

        court.updateCourt(dto);

        Court saved = courtRepository.save(court);
        return CourtDTO.from(saved);
    }

    @Override
    public void delete(Long id) {
        if (!courtRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.COURT_NOT_FOUND);
        }
        courtRepository.deleteById(id);
    }

    private void validateCreateDto(CourtCreateDTO dto) {
        if (dto == null) {
            throw new BusinessException(ErrorCode.VALIDATION_FAILED);
        }
        if (dto.getStadiumId() == null) {
            throw new BusinessException(ErrorCode.VALIDATION_FAILED);
        }
        if (dto.getNumber() == null || dto.getNumber() <= 0) {
            throw new BusinessException(ErrorCode.VALIDATION_FAILED);
        }
        if (dto.getState() == null) {
            throw new BusinessException(ErrorCode.VALIDATION_FAILED);
        }
        if (dto.getSurfaceType() == null) {
            throw new BusinessException(ErrorCode.VALIDATION_FAILED);
        }
    }

    private void validateUpdateDto(CourtUpdateDTO dto) {
        if (dto == null) {
            throw new BusinessException(ErrorCode.VALIDATION_FAILED);
        }
        if (dto.getState()  == null) {
            throw new BusinessException(ErrorCode.VALIDATION_FAILED);
        }
        if (dto.getSurfaceType()  == null) {
            throw new BusinessException(ErrorCode.VALIDATION_FAILED);
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
