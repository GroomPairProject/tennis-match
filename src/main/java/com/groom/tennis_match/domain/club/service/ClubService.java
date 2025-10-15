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
import com.groom.tennis_match.auth.util.SecurityContextUtil;
import com.groom.tennis_match.auth.entity.Admin;
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
    private final SecurityContextUtil securityContextUtil;

    /**
     * 내 클럽 등록
     */
    @Transactional
    public ClubResponseDTO createMyClub(ClubRequestDTO request) {
        log.info("내 클럽 등록 요청: {}", request.getClubName());

        // 현재 인증된 관리자 정보 가져오기
        Admin currentAdmin = securityContextUtil.getCurrentAdmin();
        Long currentAdminId = currentAdmin.getAdminId();
        
        // 관리자가 이미 클럽에 속해있는지 확인
        if (clubUserRepository.findByAdminId(currentAdminId).isPresent()) {
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
                .build();

        Club savedClub = clubRepository.save(club);

        // 관리자-클럽 관계 생성
        ClubUser clubUser = ClubUser.createAdminClub(currentAdminId, savedClub.getClubId());
        clubUserRepository.save(clubUser);

        log.info("내 클럽 등록 완료: ID={}, Name={}", savedClub.getClubId(), savedClub.getClubName());

        return ClubResponseDTO.from(savedClub);
    }

    /**
     * 내 클럽 조회
     */
    public ClubResponseDTO getMyClub() {
        log.info("내 클럽 조회 요청");

        // 현재 인증된 관리자 정보 가져오기
        Admin currentAdmin = securityContextUtil.getCurrentAdmin();
        Long currentAdminId = currentAdmin.getAdminId();

        // 관리자의 클럽 ID 조회
        Long clubId = clubUserRepository.findClubIdByAdminId(currentAdminId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));

        return ClubResponseDTO.from(club);
    }

    /**
     * 특정 클럽 ID로 내 클럽 조회 (권한 검증 포함)
     */
    public ClubResponseDTO getMyClubById(Long clubId) {
        log.info("내 클럽 조회 요청: ID={}", clubId);

        // 현재 인증된 관리자 정보 가져오기
        Admin currentAdmin = securityContextUtil.getCurrentAdmin();
        Long currentAdminId = currentAdmin.getAdminId();

        // 관리자의 클럽 ID 조회
        Long myClubId = clubUserRepository.findClubIdByAdminId(currentAdminId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));

        // 요청한 클럽 ID가 내 클럽 ID와 일치하는지 검증
        if (!myClubId.equals(clubId)) {
            throw new BusinessException(ErrorCode.CLUB_NOT_FOUND);
        }

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

        // 현재 인증된 관리자 정보 가져오기
        Admin currentAdmin = securityContextUtil.getCurrentAdmin();
        Long currentAdminId = currentAdmin.getAdminId();

        // 관리자의 클럽 ID 조회
        Long clubId = clubUserRepository.findClubIdByAdminId(currentAdminId)
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
                request.getIsActive()
        );

        log.info("내 클럽 수정 완료: ID={}, Name={}", club.getClubId(), club.getClubName());
        return ClubResponseDTO.from(club);
    }

    /**
     * 특정 클럽 ID로 내 클럽 수정 (권한 검증 포함)
     */
    @Transactional
    public ClubResponseDTO updateMyClubById(Long clubId, ClubUpdateRequestDTO request) {
        log.info("내 클럽 수정 요청: ID={}, Name={}", clubId, request.getClubName());

        // 현재 인증된 관리자 정보 가져오기
        Admin currentAdmin = securityContextUtil.getCurrentAdmin();
        Long currentAdminId = currentAdmin.getAdminId();

        // 관리자의 클럽 ID 조회
        Long myClubId = clubUserRepository.findClubIdByAdminId(currentAdminId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));

        // 요청한 클럽 ID가 내 클럽 ID와 일치하는지 검증
        if (!myClubId.equals(clubId)) {
            throw new BusinessException(ErrorCode.CLUB_NOT_FOUND);
        }

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
                request.getIsActive()
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

        // 현재 인증된 관리자 정보 가져오기
        Admin currentAdmin = securityContextUtil.getCurrentAdmin();
        Long currentAdminId = currentAdmin.getAdminId();

        // 관리자의 클럽 ID 조회
        Long clubId = clubUserRepository.findClubIdByAdminId(currentAdminId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));

        // 클럽 비활성화
        club.changeActiveStatus(false);

        // 관리자-클럽 관계 삭제
        ClubUser clubUser = clubUserRepository.findByAdminId(currentAdminId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));
        clubUserRepository.delete(clubUser);

        log.info("내 클럽 삭제 완료: ID={}, Name={}", club.getClubId(), club.getClubName());
    }

    /**
     * 특정 클럽 ID로 내 클럽 삭제 (권한 검증 포함)
     */
    @Transactional
    public void deleteMyClubById(Long clubId) {
        log.info("내 클럽 삭제 요청: ID={}", clubId);

        // 현재 인증된 관리자 정보 가져오기
        Admin currentAdmin = securityContextUtil.getCurrentAdmin();
        Long currentAdminId = currentAdmin.getAdminId();

        // 관리자의 클럽 ID 조회
        Long myClubId = clubUserRepository.findClubIdByAdminId(currentAdminId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));

        // 요청한 클럽 ID가 내 클럽 ID와 일치하는지 검증
        if (!myClubId.equals(clubId)) {
            throw new BusinessException(ErrorCode.CLUB_NOT_FOUND);
        }

        Club club = clubRepository.findById(clubId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));

        // 클럽 비활성화
        club.changeActiveStatus(false);

        // 관리자-클럽 관계 삭제
        ClubUser clubUser = clubUserRepository.findByAdminId(currentAdminId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CLUB_NOT_FOUND));
        clubUserRepository.delete(clubUser);

        log.info("내 클럽 삭제 완료: ID={}, Name={}", club.getClubId(), club.getClubName());
    }

}
