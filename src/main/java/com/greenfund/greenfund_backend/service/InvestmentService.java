package com.greenfund.greenfund_backend.service;

import com.greenfund.greenfund_backend.model.dto.request.InvestRequest;
import com.greenfund.greenfund_backend.model.dto.response.InvestmentResponse;
import com.greenfund.greenfund_backend.model.entity.Investment;
import com.greenfund.greenfund_backend.model.entity.Project;
import com.greenfund.greenfund_backend.model.entity.User;
import com.greenfund.greenfund_backend.model.enums.ProjectStatus;
import com.greenfund.greenfund_backend.repository.InvestmentRepository;
import com.greenfund.greenfund_backend.repository.ProjectRepository;
import com.greenfund.greenfund_backend.repository.UserRepository;
import com.greenfund.greenfund_backend.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InvestmentService {
    @Autowired
    private InvestmentRepository investmentRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public InvestmentResponse investInProject(Long projectId, InvestRequest request) {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        User investor = userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project not found"));

        if (project.getStatus() != ProjectStatus.APPROVED && project.getStatus() != ProjectStatus.ACTIVE) {
            throw new RuntimeException("Project is not available for investment");
        }

        // Mettre à jour le montant collecté
        project.setRaisedAmount(project.getRaisedAmount().add(request.getAmount()));
        
        // Mettre à jour le statut si nécessaire
        if (project.isFunded() && project.getStatus() == ProjectStatus.ACTIVE) {
            project.setStatus(ProjectStatus.COMPLETED);
        } else if (project.getStatus() == ProjectStatus.APPROVED) {
            // Si le projet est approuvé, le passer en ACTIVE lors du premier investissement
            project.setStatus(ProjectStatus.ACTIVE);
        }

        Investment investment = new Investment();
        investment.setAmount(request.getAmount());
        investment.setProject(project);
        investment.setInvestor(investor);

        investment = investmentRepository.save(investment);
        projectRepository.save(project);

        return convertToResponse(investment);
    }

    public List<InvestmentResponse> getInvestmentsByInvestor() {
        UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return investmentRepository.findByInvestorId(userPrincipal.getId()).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<InvestmentResponse> getInvestmentsByProject(Long projectId) {
        return investmentRepository.findByProjectId(projectId).stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    public List<InvestmentResponse> getAllInvestments() {
        return investmentRepository.findAll().stream()
                .map(this::convertToResponse)
                .collect(Collectors.toList());
    }

    private InvestmentResponse convertToResponse(Investment investment) {
        InvestmentResponse response = new InvestmentResponse();
        response.setId(investment.getId());
        response.setAmount(investment.getAmount());
        response.setProjectId(investment.getProject().getId());
        response.setProjectTitle(investment.getProject().getTitle());
        response.setInvestorId(investment.getInvestor().getId());
        response.setInvestorName(investment.getInvestor().getName());
        response.setCreatedAt(investment.getCreatedAt());
        return response;
    }
}

