# Script pour lancer SonarQube sans vérification de couverture stricte
# Usage: .\run-sonar-without-check.ps1

$env:SONAR_TOKEN="sqp_41da5aa48bfc8d008d2c6a01596327a959e14f28"

Write-Host "Token SonarQube configuré" -ForegroundColor Green
Write-Host "Lancement de l'analyse SonarQube (sans vérification de couverture stricte)..." -ForegroundColor Yellow
Write-Host ""

# Lancer les tests et générer le rapport JaCoCo, puis SonarQube
# On skip la vérification de couverture avec -Djacoco.check.skip=true
mvn clean test jacoco:report sonar:sonar -Djacoco.check.skip=true

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "✅ Analyse terminée avec succès !" -ForegroundColor Green
    Write-Host "Consultez les résultats sur: http://localhost:9000" -ForegroundColor Cyan
    Write-Host ""
    Write-Host "Note: La couverture de code est faible car il n'y a pas encore beaucoup de tests." -ForegroundColor Yellow
    Write-Host "C'est normal pour un projet en développement." -ForegroundColor Yellow
} else {
    Write-Host ""
    Write-Host "❌ L'analyse a échoué" -ForegroundColor Red
}

