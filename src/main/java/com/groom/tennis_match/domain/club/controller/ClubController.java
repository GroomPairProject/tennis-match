package com.groom.tennis_match.domain.club.controller;

import com.groom.tennis_match.domain.club.dto.ClubRequestDTO;
import com.groom.tennis_match.domain.club.dto.ClubResponseDTO;
import com.groom.tennis_match.domain.club.dto.ClubUpdateRequestDTO;
import com.groom.tennis_match.domain.club.service.ClubService;
import com.groom.tennis_match.common.constant.SuccessCode;
import com.groom.tennis_match.common.dto.ApiResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * 클럽(협회) 마이페이지 컨트롤러
 * 사용자가 자신이 속한 클럽을 관리하는 API
 * 
 * @author tennis-match
 */
@Slf4j
@RestController
@RequestMapping("/admin/club")
@RequiredArgsConstructor
public class ClubController {

    private final ClubService clubService;

    /**
     * 클럽 등록
     * POST /admin/club
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ClubResponseDTO>> createClub(@Valid @RequestBody ClubRequestDTO request) {
        log.info("클럽 등록 API 호출: {}", request.getClubName());
        
        ClubResponseDTO response = clubService.createMyClub(request);
        ApiResponse<ClubResponseDTO> apiResponse = ApiResponse.success(response, SuccessCode.CLUB_CREATE_SUCCESS);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(apiResponse);
    }

    /**
     * 클럽 조회
     * GET /admin/club/{clubId}
     */
    @GetMapping("/{clubId}")
    public ApiResponse<ClubResponseDTO> getClub(@PathVariable Long clubId) {
        log.info("클럽 조회 API 호출: ID={}", clubId);
        
        ClubResponseDTO response = clubService.getMyClub();
        return ApiResponse.success(response, SuccessCode.CLUB_READ_SUCCESS);
    }

    /**
     * 클럽 수정
     * PUT /admin/club/{clubId}
     */
    @PutMapping("/{clubId}")
    public ApiResponse<ClubResponseDTO> updateClub(@PathVariable Long clubId, @Valid @RequestBody ClubUpdateRequestDTO request) {
        log.info("클럽 수정 API 호출: ID={}, Name={}", clubId, request.getClubName());
        
        ClubResponseDTO response = clubService.updateMyClub(request);
        return ApiResponse.success(response, SuccessCode.CLUB_UPDATE_SUCCESS);
    }

    /**
     * 클럽 삭제
     * DELETE /admin/club/{clubId}
     */
    @DeleteMapping("/{clubId}")
    public ApiResponse<Void> deleteClub(@PathVariable Long clubId) {
        log.info("클럽 삭제 API 호출: ID={}", clubId);
        
        clubService.deleteMyClub();
        return ApiResponse.success(SuccessCode.CLUB_DELETE_SUCCESS);
    }
}
