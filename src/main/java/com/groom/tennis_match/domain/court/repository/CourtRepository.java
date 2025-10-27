package com.groom.tennis_match.domain.court.repository;

import com.groom.tennis_match.domain.court.entity.Court;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface CourtRepository extends JpaRepository<Court, Long>, JpaSpecificationExecutor<Court> {
    Optional<Court> findByStadiumIdAndNumber(Long stadiumId, Integer number);
    List<Court> findAllByStadiumId(Long stadiumId);
    boolean existsByStadiumIdAndNumber(Long stadiumId, Integer number);
}
