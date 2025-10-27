package com.groom.tennis_match.domain.court.repository;

import com.groom.tennis_match.domain.court.dto.CourtSearchDTO;
import com.groom.tennis_match.domain.court.entity.Court;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class CourtSpecification {
    public static Specification<Court> toSpecification(CourtSearchDTO dto) {
        return (root, query, cb) -> {
            if (dto == null) {
                return cb.conjunction();
            }

            List<Predicate> predicates = new ArrayList<>();

            if (dto.getStadiumId() != null) {
                predicates.add(cb.equal(root.get("stadium").get("id"), dto.getStadiumId()));
            }
            if (dto.getState() != null && !dto.getState().isBlank()) {
                predicates.add(cb.equal(root.get("state"), dto.getState()));
            }
            if (dto.getSurfaceType() != null && !dto.getSurfaceType().isBlank()) {
                predicates.add(cb.equal(root.get("surfaceType"), dto.getSurfaceType()));
            }
            if (dto.getNumber() != null) {
                predicates.add(cb.equal(root.get("number"), dto.getNumber()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
