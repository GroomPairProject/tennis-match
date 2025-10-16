package com.groom.tennis_match.domain.stadium.controller;

import com.groom.tennis_match.common.constant.SuccessCode;
import com.groom.tennis_match.common.dto.ApiResponse;
import com.groom.tennis_match.domain.stadium.dto.StadiumCreateDTO;
import com.groom.tennis_match.domain.stadium.dto.StadiumDTO;
import com.groom.tennis_match.domain.stadium.dto.StadiumSearchDTO;
import com.groom.tennis_match.domain.stadium.service.StadiumService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/admin/stadiums")
@RequiredArgsConstructor
public class StadiumController {
    private final StadiumService stadiumService;

    @PostMapping
    public ResponseEntity<ApiResponse<StadiumDTO>> createStadium(@Valid @RequestBody StadiumCreateDTO request) {
        log.info("스타디움 등록 API 호출: {}", request.getName());
        StadiumDTO response = stadiumService.create(request);
        ApiResponse<StadiumDTO> apiResponse = ApiResponse.success(response, SuccessCode.STADIUM_CREATE_SUCCESS);
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ApiResponse<StadiumDTO> getStadium(@PathVariable Long id) {
        log.info("스타디움 조회 API 호출: ID={}", id);
        StadiumDTO response = stadiumService.findById(id);
        return ApiResponse.success(response, SuccessCode.STADIUM_READ_SUCCESS);
    }

    @GetMapping
    public ApiResponse<Page<StadiumDTO>> listStadiums(@ModelAttribute StadiumSearchDTO searchDTO, Pageable pageable) {
        log.info("스타디움 목록/검색 API 호출");
        Page<StadiumDTO> page;
        if (searchDTO == null || searchDTO.isEmpty()) {
            page = stadiumService.findAll(pageable);
        } else {
            page = stadiumService.findAll(searchDTO, pageable);
        }
        return ApiResponse.success(page, SuccessCode.STADIUM_READ_SUCCESS);
    }

    @PutMapping("/{id}")
    public ApiResponse<StadiumDTO> updateStadium(@PathVariable Long id, @Valid @RequestBody StadiumCreateDTO request) {
        log.info("스타디움 수정 API 호출: ID={}", id);
        StadiumDTO response = stadiumService.update(id, request);
        return ApiResponse.success(response, SuccessCode.STADIUM_UPDATE_SUCCESS);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteStadium(@PathVariable Long id) {
        log.info("스타디움 삭제 API 호출: ID={}", id);
        stadiumService.delete(id);
        return ApiResponse.success(SuccessCode.STADIUM_DELETE_SUCCESS);
    }
}
