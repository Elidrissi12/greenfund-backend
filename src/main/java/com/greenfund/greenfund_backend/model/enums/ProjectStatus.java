package com.greenfund.greenfund_backend.model.enums;

public enum ProjectStatus {
    PENDING,    // En attente de validation
    APPROVED,   // Approuvé
    REJECTED,   // Rejeté
    ACTIVE,     // Actif (collecte en cours)
    COMPLETED,  // Complété (objectif atteint)
    CANCELLED   // Annulé
}

