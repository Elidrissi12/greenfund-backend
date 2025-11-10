package com.greenfund.greenfund_backend.repository;

import com.greenfund.greenfund_backend.model.entity.Project;
import com.greenfund.greenfund_backend.model.enums.EnergyType;
import com.greenfund.greenfund_backend.model.enums.ProjectStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findByStatus(ProjectStatus status);
    List<Project> findByOwnerId(Long ownerId);
    List<Project> findByEnergyType(EnergyType energyType);
    List<Project> findByCity(String city);
    
    @Query("SELECT SUM(p.raisedAmount) FROM Project p WHERE p.status = :status")
    Double getTotalRaisedAmountByStatus(@Param("status") ProjectStatus status);
    
    @Query("SELECT COUNT(p) FROM Project p WHERE p.status = :status")
    Long countByStatus(@Param("status") ProjectStatus status);
}

