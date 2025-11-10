package com.greenfund.greenfund_backend.model.dto.response;

import com.greenfund.greenfund_backend.model.enums.EnergyType;
import com.greenfund.greenfund_backend.model.enums.ProjectStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProjectResponse {
    private Long id;
    private String title;
    private String city;
    private EnergyType energyType;
    private String description;
    private BigDecimal targetAmount;
    private BigDecimal raisedAmount;
    private double progress;
    private ProjectStatus status;
    private Long ownerId;
    private String ownerName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

