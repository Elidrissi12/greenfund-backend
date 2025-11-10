package com.greenfund.greenfund_backend.repository;

import com.greenfund.greenfund_backend.model.entity.Investment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByInvestorId(Long investorId);
    List<Investment> findByProjectId(Long projectId);
    
    @Query("SELECT SUM(i.amount) FROM Investment i WHERE i.project.id = :projectId")
    Double getTotalInvestmentsByProjectId(@Param("projectId") Long projectId);
}

