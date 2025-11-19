package com.greenfund.greenfund_backend.repository.specification;

import com.greenfund.greenfund_backend.model.entity.Project;
import com.greenfund.greenfund_backend.model.enums.EnergyType;
import com.greenfund.greenfund_backend.model.enums.ProjectStatus;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class ProjectSpecifications {

    private ProjectSpecifications() {
    }

    public static Specification<Project> withKeyword(String keyword) {
        return (root, query, cb) -> {
            if (keyword == null || keyword.isBlank()) {
                return cb.conjunction();
            }
            String pattern = "%" + keyword.trim().toLowerCase() + "%";
            return cb.or(
                    cb.like(cb.lower(root.get("title")), pattern),
                    cb.like(cb.lower(root.get("description")), pattern),
                    cb.like(cb.lower(root.get("city")), pattern)
            );
        };
    }

    public static Specification<Project> withCity(String city) {
        return (root, query, cb) -> {
            if (city == null || city.isBlank()) {
                return cb.conjunction();
            }
            return cb.like(cb.lower(root.get("city")), "%" + city.trim().toLowerCase() + "%");
        };
    }

    public static Specification<Project> withEnergyType(EnergyType energyType) {
        return (root, query, cb) -> energyType == null ? cb.conjunction() : cb.equal(root.get("energyType"), energyType);
    }

    public static Specification<Project> withStatus(ProjectStatus status) {
        return (root, query, cb) -> status == null ? cb.conjunction() : cb.equal(root.get("status"), status);
    }

    public static Specification<Project> withMinTargetAmount(BigDecimal minTargetAmount) {
        return (root, query, cb) -> minTargetAmount == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("targetAmount"), minTargetAmount);
    }

    public static Specification<Project> withMaxTargetAmount(BigDecimal maxTargetAmount) {
        return (root, query, cb) -> maxTargetAmount == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("targetAmount"), maxTargetAmount);
    }

    public static Specification<Project> withMinRaisedAmount(BigDecimal minRaisedAmount) {
        return (root, query, cb) -> minRaisedAmount == null ? cb.conjunction() : cb.greaterThanOrEqualTo(root.get("raisedAmount"), minRaisedAmount);
    }

    public static Specification<Project> withMaxRaisedAmount(BigDecimal maxRaisedAmount) {
        return (root, query, cb) -> maxRaisedAmount == null ? cb.conjunction() : cb.lessThanOrEqualTo(root.get("raisedAmount"), maxRaisedAmount);
    }

    public static Specification<Project> build(String keyword,
                                               String city,
                                               EnergyType energyType,
                                               ProjectStatus status,
                                               BigDecimal minTargetAmount,
                                               BigDecimal maxTargetAmount,
                                               BigDecimal minRaisedAmount,
                                               BigDecimal maxRaisedAmount) {
        return Specification.where(withKeyword(keyword))
                .and(withCity(city))
                .and(withEnergyType(energyType))
                .and(withStatus(status))
                .and(withMinTargetAmount(minTargetAmount))
                .and(withMaxTargetAmount(maxTargetAmount))
                .and(withMinRaisedAmount(minRaisedAmount))
                .and(withMaxRaisedAmount(maxRaisedAmount));
    }
}
