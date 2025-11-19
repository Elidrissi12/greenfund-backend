# Script simple qui utilise Maven directement si disponible
# Usage: .\run-sonar-simple.ps1

$env:SONAR_TOKEN="sqp_41da5aa48bfc8d008d2c6a01596327a959e14f28"

Write-Host "Token SonarQube configuré" -ForegroundColor Green

# Vérifier si Maven est disponible
$mavenCmd = $null
try {
    $mavenCmd = (Get-Command mvn -ErrorAction Stop).Source
    Write-Host "Maven trouvé: $mavenCmd" -ForegroundColor Green
} catch {
    Write-Host "Maven n'est pas trouvé dans le PATH" -ForegroundColor Red
    Write-Host ""
    Write-Host "Solutions:" -ForegroundColor Yellow
    Write-Host "1. Installez Maven: https://maven.apache.org/download.cgi" -ForegroundColor White
    Write-Host "2. Ou utilisez le script d'installation: .\install-maven.ps1" -ForegroundColor White
    Write-Host "3. Ou utilisez Maven depuis un chemin spécifique:" -ForegroundColor White
    Write-Host "   & `"C:\Program Files\Apache\maven\bin\mvn.cmd`" clean verify sonar:sonar -Dsonar.login=$env:SONAR_TOKEN" -ForegroundColor Cyan
    exit 1
}

Write-Host ""
Write-Host "Lancement de l'analyse SonarQube..." -ForegroundColor Yellow
Write-Host ""

& $mavenCmd clean verify sonar:sonar

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "✅ Analyse terminée avec succès !" -ForegroundColor Green
    Write-Host "Consultez les résultats sur: http://localhost:9000" -ForegroundColor Cyan
} else {
    Write-Host ""
    Write-Host "❌ L'analyse a échoué" -ForegroundColor Red
}

