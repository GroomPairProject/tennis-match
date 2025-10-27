package com.groom.tennis_match.domain.stadium.repository;

import com.groom.tennis_match.domain.stadium.dto.StadiumSearchDTO;
import com.groom.tennis_match.domain.stadium.entity.Stadium;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;

public class StadiumSpecification {
    /**
     * SearchDTO가 null이거나 isEmpty()면 null 반환(전체조회용).
     * 그렇지 않으면 동적 조건으로 Specification 생성.
     */
    public static Specification<Stadium> toSpecification(StadiumSearchDTO dto) {
        if (dto == null || dto.isEmpty()) {
            return null;
        }

        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (dto.getRegion() != null && !dto.getRegion().isBlank()) {
                predicates.add(cb.equal(root.get("region"), dto.getRegion()));
            }

            if (dto.getKeyword() != null && !dto.getKeyword().isBlank()) {
                String like = "%" + dto.getKeyword().toLowerCase() + "%";
                predicates.add(cb.or(
                        cb.like(cb.lower(root.get("name")), like),
                        cb.like(cb.lower(root.get("address")), like)
                ));
            }

            if (dto.getIsIndoor() != null) {
                predicates.add(dto.getIsIndoor() ? cb.isTrue(root.get("isIndoor")) : cb.isFalse(root.get("isIndoor")));
            }

            if (dto.getIsOutdoor() != null) {
                predicates.add(dto.getIsOutdoor() ? cb.isTrue(root.get("isOutdoor")) : cb.isFalse(root.get("isOutdoor")));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
