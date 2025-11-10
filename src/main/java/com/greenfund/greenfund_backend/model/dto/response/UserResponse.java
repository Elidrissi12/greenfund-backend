package com.greenfund.greenfund_backend.model.dto.response;

import com.greenfund.greenfund_backend.model.enums.Role;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private Role role;
    private Boolean active;
    private LocalDateTime createdAt;
}

