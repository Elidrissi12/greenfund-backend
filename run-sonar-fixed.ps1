# Script PowerShell pour lancer l'analyse SonarQube (version corrigée)
# Usage: .\run-sonar-fixed.ps1

$env:SONAR_TOKEN="sqp_41da5aa48bfc8d008d2c6a01596327a959e14f28"

Write-Host "Token SonarQube configuré" -ForegroundColor Green
Write-Host "Vérification de Java..." -ForegroundColor Yellow

# Vérifier si Java est disponible
try {
    $javaVersion = java -version 2>&1 | Select-Object -First 1
    Write-Host "Java trouvé: $javaVersion" -ForegroundColor Green
} catch {
    Write-Host "Erreur: Java n'est pas trouvé dans le PATH" -ForegroundColor Red
    Write-Host "Veuillez installer Java ou configurer JAVA_HOME" -ForegroundColor Yellow
    exit 1
}

Write-Host "Lancement de l'analyse SonarQube..." -ForegroundColor Yellow
Write-Host ""

# Utiliser le wrapper Maven avec gestion d'erreur
try {
    & .\mvnw.cmd clean verify sonar:sonar
    if ($LASTEXITCODE -eq 0) {
        Write-Host ""
        Write-Host "✅ Analyse terminée avec succès !" -ForegroundColor Green
        Write-Host "Consultez les résultats sur: http://localhost:9000" -ForegroundColor Cyan
    } else {
        Write-Host ""
        Write-Host "❌ L'analyse a échoué avec le code: $LASTEXITCODE" -ForegroundColor Red
    }
} catch {
    Write-Host ""
    Write-Host "❌ Erreur lors de l'exécution: $_" -ForegroundColor Red
    Write-Host ""
    Write-Host "Solutions possibles:" -ForegroundColor Yellow
    Write-Host "1. Vérifiez que SonarQube est démarré (http://localhost:9000)" -ForegroundColor White
    Write-Host "2. Vérifiez que le token est correct" -ForegroundColor White
    Write-Host "3. Essayez d'installer Maven globalement et utilisez 'mvn' au lieu de 'mvnw.cmd'" -ForegroundColor White
}

