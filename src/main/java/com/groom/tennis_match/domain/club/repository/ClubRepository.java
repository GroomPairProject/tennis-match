package com.groom.tennis_match.domain.club.repository;

import com.groom.tennis_match.domain.club.entity.Club;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * 클럽(협회) 레포지토리
 * 
 * @author tennis-match
 */
@Repository
public interface ClubRepository extends JpaRepository<Club, Long> {

    /**
     * 클럽명으로 클럽 조회
     */
    Optional<Club> findByClubName(String clubName);

}



