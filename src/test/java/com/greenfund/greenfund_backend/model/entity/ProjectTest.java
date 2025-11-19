package com.greenfund.greenfund_backend.model.entity;

import com.greenfund.greenfund_backend.model.enums.EnergyType;
import com.greenfund.greenfund_backend.model.enums.ProjectStatus;
import com.greenfund.greenfund_backend.model.enums.Role;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

class ProjectTest {

    @Test
    void getProgress_ShouldReturnPercentageBasedOnRaisedAmount() {
        Project project = createProject(new BigDecimal("1000"), new BigDecimal("250"));

        assertThat(project.getProgress()).isEqualTo(25.0);
    }

    @Test
    void getProgress_ShouldClampToHundredWhenRaisedExceedsTarget() {
        Project project = createProject(new BigDecimal("500"), new BigDecimal("800"));

        assertThat(project.getProgress()).isEqualTo(100.0);
    }

    @Test
    void getProgress_ShouldReturnZeroWhenTargetIsZero() {
        Project project = createProject(BigDecimal.ZERO, BigDecimal.ZERO);

        assertThat(project.getProgress()).isEqualTo(0.0);
    }

    @Test
    void isFunded_ShouldReturnTrueWhenRaisedAmountGreaterOrEqualTarget() {
        Project project = createProject(new BigDecimal("1000"), new BigDecimal("1000"));

        assertThat(project.isFunded()).isTrue();
    }

    @Test
    void isFunded_ShouldReturnFalseOtherwise() {
        Project project = createProject(new BigDecimal("2000"), new BigDecimal("1500"));

        assertThat(project.isFunded()).isFalse();
    }

    private Project createProject(BigDecimal target, BigDecimal raised) {
        User owner = new User();
        owner.setId(1L);
        owner.setName("Owner");
        owner.setEmail("owner@example.com");
        owner.setPassword("password123");
        owner.setRole(Role.OWNER);
        owner.setActive(true);

        Project project = new Project();
        project.setId(1L);
        project.setTitle("Test Project");
        project.setCity("Casablanca");
        project.setEnergyType(EnergyType.SOLAIRE);
        project.setDescription("Description de test");
        project.setTargetAmount(target);
        project.setRaisedAmount(raised);
        project.setStatus(ProjectStatus.PENDING);
        project.setOwner(owner);
        return project;
    }
}
