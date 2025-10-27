package com.groom.tennis_match.domain.court.controller;

import com.groom.tennis_match.common.constant.SuccessCode;
import com.groom.tennis_match.common.dto.ApiResponse;
import com.groom.tennis_match.domain.court.dto.CourtCreateDTO;
import com.groom.tennis_match.domain.court.dto.CourtDTO;
import com.groom.tennis_match.domain.court.dto.CourtSearchDTO;
import com.groom.tennis_match.domain.court.dto.CourtUpdateDTO;
import com.groom.tennis_match.domain.court.service.CourtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api/admin/courts")
@RequiredArgsConstructor
public class CourtController {

    private final CourtService courtService;

    @PostMapping
    public ResponseEntity<ApiResponse<CourtDTO>> create(@Valid @RequestBody CourtCreateDTO dto) {
        CourtDTO saved = courtService.create(dto);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saved.getId())
                .toUri();
        return ResponseEntity.created(location).body(ApiResponse.success(saved));
    }

    @GetMapping("/{id}")
    public ApiResponse<CourtDTO> findById(@PathVariable Long id) {
        return ApiResponse.success(courtService.findById(id));
    }

    @GetMapping
    public ApiResponse<Page<CourtDTO>> findAll(@ModelAttribute CourtSearchDTO searchDTO, Pageable pageable) {
        Page<CourtDTO> page = courtService.findAll(searchDTO, pageable);
        return ApiResponse.success(page);
    }

    @PutMapping("/{id}")
    public ApiResponse<CourtDTO> update(@PathVariable Long id, @Valid @RequestBody CourtUpdateDTO dto) {
        return ApiResponse.success(courtService.update(id, dto));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        courtService.delete(id);
        return ApiResponse.success(SuccessCode.COURT_DELETE_SUCCESS);
    }
}
