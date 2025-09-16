package com.groom.tennis_match.domain.club.service;

import com.groom.tennis_match.domain.club.dto.ClubRequestDTO;
import com.groom.tennis_match.domain.club.dto.ClubResponseDTO;
import com.groom.tennis_match.domain.club.dto.ClubUpdateRequestDTO;
import com.groom.tennis_match.domain.club.entity.Club;
import com.groom.tennis_match.domain.club.entity.ClubUser;
import com.groom.tennis_match.domain.club.repository.ClubRepository;
import com.groom.tennis_match.domain.club.repository.ClubUserRepository;
import com.groom.tennis_match.common.constant.ErrorCode;
import com.groom.tennis_match.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * 클럽(협회) 마이페이지 서비스
 * 사용자가 자신이 속한 클럽을 관리하는 서비스
 * 
 * @author tennis-match
 */
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubService {

    private final ClubRepository clubRepository;
    private final ClubUserRepository clubUserRepository;
    
    // TODO: 실제 구현 시 SecurityContext에서 현재 사용자 ID를 가져와야 함
    private static final Long CURRENT_USER_ID = 1L; // 임시 하드코딩
    private static final Long CURRENT_ADMIN_ID = 1L; // 임시 하드코딩

    /**
     * 내 클럽 등록
     */
    @Transactional
    public ClubResponseDTO createMyClub(ClubRequestDTO request) {
        log.info("내 클럽 등록 요청: {}", request.getClubName());

        // 이미 클럽에 속해있는지 확인
        if (clubUserRepository.findByUserId(CURRENT_USER_ID).isPresent()) {
            throw new BusinessException(ErrorCode.CLUB_ALREADY_JOINED);
        }


        // Club 엔티티 생성
        LocalDateTime now = LocalDateTime.now();
        Club club = Club.builder()
                .clubName(request.getClubName())
                .city(request.getCity())
                .district(request.getDistrict())
                .addressDetail(request.getAddressDetail())
                .clubGender(request.getClubGender())
                .category(request.getCategory())
                .clubDiv(request.getClubDiv())
                .representativeName(request.getRepresentativeName())
                .isActive(request.getIsActive())
                .createDate(request.getCreateDate() != null ? request.getCreateDate() : LocalDate.now())
                .createAt(now)
                .creator(CURRENT_ADMIN_ID)
                .build();

        Club savedClub = clubRepository.save(club);

        // 사용자-클럽 관계 생성
        ClubUser clubUser = ClubUser.createUserClub(CURRENT_USER_ID, savedClub.getClubId(), CURRENT_ADMIN_ID);
        clubUserRepository.save(clubUser);

        log.info("내 클럽 등록 완료: ID={}, Name={}", savedClub.getClubId(), savedClub.getClubName());

        return ClubResponseDTO.from(savedClub);
    }

    /**
     * 내 클럽 조회
     */
    public ClubResponseDTO getMyClub() {
        log.info("내 클럽 조회 요청");

        // 사용자의 클럽 ID 조회
        Long clubId = clubUserRepository.findClubIdByUserId(CURRENT_USER_ID)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));

        return ClubResponseDTO.from(club);
    }


    /**
     * 내 클럽 수정
     */
    @Transactional
    public ClubResponseDTO updateMyClub(ClubUpdateRequestDTO request) {
        log.info("내 클럽 수정 요청: Name={}", request.getClubName());

        // 사용자의 클럽 ID 조회
        Long clubId = clubUserRepository.findClubIdByUserId(CURRENT_USER_ID)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));


        // 클럽 정보 수정
        club.updateClub(
                request.getClubName(),
                request.getCity(),
                request.getDistrict(),
                request.getAddressDetail(),
                request.getClubGender(),
                request.getCategory(),
                request.getClubDiv(),
                request.getRepresentativeName(),
                request.getIsActive(),
                CURRENT_ADMIN_ID
        );

        log.info("내 클럽 수정 완료: ID={}, Name={}", club.getClubId(), club.getClubName());
        return ClubResponseDTO.from(club);
    }

    /**
     * 내 클럽 삭제 (탈퇴)
     */
    @Transactional
    public void deleteMyClub() {
        log.info("내 클럽 삭제 요청");

        // 사용자의 클럽 ID 조회
        Long clubId = clubUserRepository.findClubIdByUserId(CURRENT_USER_ID)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));

        // 클럽 비활성화
        club.changeActiveStatus(false, CURRENT_ADMIN_ID);

        // 사용자-클럽 관계 삭제
        ClubUser clubUser = clubUserRepository.findByUserId(CURRENT_USER_ID)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));
        clubUserRepository.delete(clubUser);

        log.info("내 클럽 삭제 완료: ID={}, Name={}", club.getClubId(), club.getClubName());
    }

}
