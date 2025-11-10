package com.greenfund.greenfund_backend.controller;

import com.greenfund.greenfund_backend.model.dto.request.InvestRequest;
import com.greenfund.greenfund_backend.model.dto.response.InvestmentResponse;
import com.greenfund.greenfund_backend.service.InvestmentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/investments")
@CrossOrigin(origins = "*")
public class InvestmentController {
    @Autowired
    private InvestmentService investmentService;

    @PostMapping("/projects/{projectId}")
    @PreAuthorize("hasRole('INVESTOR')")
    public ResponseEntity<InvestmentResponse> investInProject(
            @PathVariable Long projectId,
            @Valid @RequestBody InvestRequest request) {
        InvestmentResponse response = investmentService.investInProject(projectId, request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/my-investments")
    @PreAuthorize("hasRole('INVESTOR')")
    public ResponseEntity<List<InvestmentResponse>> getMyInvestments() {
        List<InvestmentResponse> investments = investmentService.getInvestmentsByInvestor();
        return ResponseEntity.ok(investments);
    }

    @GetMapping("/projects/{projectId}")
    public ResponseEntity<List<InvestmentResponse>> getInvestmentsByProject(@PathVariable Long projectId) {
        List<InvestmentResponse> investments = investmentService.getInvestmentsByProject(projectId);
        return ResponseEntity.ok(investments);
    }
}

