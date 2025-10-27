package com.groom.tennis_match.domain.stadium.service.impl;

import com.groom.tennis_match.common.constant.ErrorCode;
import com.groom.tennis_match.common.exception.BusinessException;
import com.groom.tennis_match.domain.stadium.dto.StadiumCreateDTO;
import com.groom.tennis_match.domain.stadium.dto.StadiumDTO;
import com.groom.tennis_match.domain.stadium.dto.StadiumSearchDTO;
import com.groom.tennis_match.domain.stadium.entity.Stadium;
import com.groom.tennis_match.domain.stadium.repository.StadiumRepository;
import com.groom.tennis_match.domain.stadium.repository.StadiumSpecification;
import com.groom.tennis_match.domain.stadium.service.StadiumService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional
public class StadiumServiceImpl implements StadiumService {

    private final StadiumRepository stadiumRepository;

    @Override
    public StadiumDTO create(StadiumCreateDTO dto) {
        validateCreateDto(dto);

        if (stadiumRepository.existsByNameAndRegion(dto.getName(), dto.getRegion())) {
            throw new BusinessException(ErrorCode.STADIUM_DUPLICATION);
        }

        Stadium stadium = dto.toEntity();
        stadium.setCreatedAt(LocalDateTime.now());
        Stadium saved = stadiumRepository.save(stadium);
        return StadiumDTO.from(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public StadiumDTO findById(Long id) {
        Stadium stadium = stadiumRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STADIUM_NOT_FOUND));
        return StadiumDTO.from(stadium);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StadiumDTO> findAll(Pageable pageable) {
        return stadiumRepository.findAll(pageable).map(StadiumDTO::from);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<StadiumDTO> findAll(StadiumSearchDTO searchDTO, Pageable pageable) {
        if (searchDTO == null || searchDTO.isEmpty()) {
            return findAll(pageable);
        }
        var spec = StadiumSpecification.toSpecification(searchDTO);
        if (spec == null) {
            return findAll(pageable);
        }
        return stadiumRepository.findAll(spec, pageable).map(StadiumDTO::from);
    }

    @Override
    public StadiumDTO update(Long id, StadiumCreateDTO dto) {
        validateCreateDto(dto);

        Stadium stadium = stadiumRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.STADIUM_NOT_FOUND));

        // 이름/지역이 변경되어 중복이 발생하는지 확인
        if (!Objects.equals(stadium.getName(), dto.getName()) || !Objects.equals(stadium.getRegion(), dto.getRegion())) {
            if (stadiumRepository.existsByNameAndRegion(dto.getName(), dto.getRegion())) {
                throw new BusinessException(ErrorCode.STADIUM_DUPLICATION);
            }
        }

        stadium.updateStadium(dto);

        Stadium saved = stadiumRepository.save(stadium);
        return StadiumDTO.from(saved);
    }

    @Override
    public void delete(Long id) {
        if (!stadiumRepository.existsById(id)) {
            throw new BusinessException(ErrorCode.STADIUM_NOT_FOUND);
        }
        stadiumRepository.deleteById(id);
    }

    private void validateCreateDto(StadiumCreateDTO dto) {
        if (dto == null) {
            throw new BusinessException(ErrorCode.VALIDATION_FAILED);
        }
        if (isBlank(dto.getName()) || dto.getName().length() > 20) {
            throw new BusinessException(ErrorCode.VALIDATION_FAILED);
        }
        if (isBlank(dto.getRegion()) || dto.getRegion().length() > 20) {
            throw new BusinessException(ErrorCode.VALIDATION_FAILED);
        }
        if (isBlank(dto.getAddress()) || dto.getAddress().length() > 30) {
            throw new BusinessException(ErrorCode.VALIDATION_FAILED);
        }
        if (dto.getDetailAddress() != null && dto.getDetailAddress().length() > 30) {
            throw new BusinessException(ErrorCode.VALIDATION_FAILED);
        }
        if (dto.getPlaceType() != null && dto.getPlaceType().length() > 30) {
            throw new BusinessException(ErrorCode.VALIDATION_FAILED);
        }
        if (dto.getManager() != null && dto.getManager().length() > 10) {
            throw new BusinessException(ErrorCode.VALIDATION_FAILED);
        }
        if (dto.getManagerPhoneNumber() != null && dto.getManagerPhoneNumber().length() > 15) {
            throw new BusinessException(ErrorCode.VALIDATION_FAILED);
        }
    }

    private boolean isBlank(String s) {
        return s == null || s.trim().isEmpty();
    }
}
