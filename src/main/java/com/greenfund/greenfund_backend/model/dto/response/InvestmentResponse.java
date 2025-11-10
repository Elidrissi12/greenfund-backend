package com.greenfund.greenfund_backend.model.dto.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class InvestmentResponse {
    private Long id;
    private BigDecimal amount;
    private Long projectId;
    private String projectTitle;
    private Long investorId;
    private String investorName;
    private LocalDateTime createdAt;
}

