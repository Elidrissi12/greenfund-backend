# Script PowerShell pour lancer l'analyse SonarQube
# Usage: .\run-sonar.ps1

$env:SONAR_TOKEN="sqp_41da5aa48bfc8d008d2c6a01596327a959e14f28"

Write-Host "Token SonarQube configuré" -ForegroundColor Green
Write-Host "Lancement de l'analyse..." -ForegroundColor Yellow

# Utiliser le wrapper Maven (mvnw.cmd)
.\mvnw.cmd clean verify sonar:sonar

Write-Host "Analyse terminée !" -ForegroundColor Green
Write-Host "Consultez les résultats sur: http://localhost:9000" -ForegroundColor Cyan

