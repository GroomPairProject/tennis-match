package com.groom.tennis_match.domain.stadium.repository;


import com.groom.tennis_match.domain.stadium.entity.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Long>, JpaSpecificationExecutor<Stadium> {

    boolean existsByNameAndRegion(String name, String region);
}
