# Script PowerShell pour lancer l'analyse SonarQube (version avec correction JAVA_HOME)
# Usage: .\run-sonar-fixed2.ps1

$env:SONAR_TOKEN="sqp_41da5aa48bfc8d008d2c6a01596327a959e14f28"

Write-Host "Token SonarQube configuré" -ForegroundColor Green
Write-Host "Correction de JAVA_HOME..." -ForegroundColor Yellow

# Trouver Java et corriger JAVA_HOME
try {
    $javaPath = (Get-Command java -ErrorAction Stop).Source
    $javaHome = Split-Path (Split-Path $javaPath)
    
    # Mettre JAVA_HOME entre guillemets si nécessaire
    if ($javaHome -match '\s') {
        $env:JAVA_HOME = "`"$javaHome`""
    } else {
        $env:JAVA_HOME = $javaHome
    }
    
    Write-Host "JAVA_HOME configuré: $env:JAVA_HOME" -ForegroundColor Green
} catch {
    Write-Host "Erreur: Java n'est pas trouvé dans le PATH" -ForegroundColor Red
    Write-Host "Veuillez installer Java ou configurer JAVA_HOME manuellement" -ForegroundColor Yellow
    exit 1
}

Write-Host ""
Write-Host "Lancement de l'analyse SonarQube..." -ForegroundColor Yellow
Write-Host ""

# Essayer d'abord avec le wrapper, sinon utiliser Maven directement
$useMaven = $false

# Vérifier si Maven est disponible
try {
    $mvnVersion = mvn --version 2>&1
    if ($LASTEXITCODE -eq 0) {
        Write-Host "Maven trouvé, utilisation de Maven directement" -ForegroundColor Green
        $useMaven = $true
    }
} catch {
    Write-Host "Maven non trouvé, utilisation du wrapper Maven" -ForegroundColor Yellow
}

if ($useMaven) {
    # Utiliser Maven directement
    mvn clean verify sonar:sonar
} else {
    # Utiliser le wrapper avec JAVA_HOME corrigé
    # Créer un script batch temporaire qui corrige JAVA_HOME
    $batchScript = @"
@echo off
set "JAVA_HOME=$javaHome"
set "SONAR_TOKEN=$env:SONAR_TOKEN"
call mvnw.cmd clean verify sonar:sonar
"@
    
    $batchFile = Join-Path $PSScriptRoot "temp-run-sonar.bat"
    $batchScript | Out-File -FilePath $batchFile -Encoding ASCII
    
    try {
        & cmd /c $batchFile
        Remove-Item $batchFile -ErrorAction SilentlyContinue
    } catch {
        Write-Host "Erreur lors de l'exécution: $_" -ForegroundColor Red
        Remove-Item $batchFile -ErrorAction SilentlyContinue
    }
}

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "✅ Analyse terminée avec succès !" -ForegroundColor Green
    Write-Host "Consultez les résultats sur: http://localhost:9000" -ForegroundColor Cyan
} else {
    Write-Host ""
    Write-Host "❌ L'analyse a échoué" -ForegroundColor Red
    Write-Host ""
    Write-Host "Solutions:" -ForegroundColor Yellow
    Write-Host "1. Installez Maven globalement: https://maven.apache.org/download.cgi" -ForegroundColor White
    Write-Host "2. Ajoutez Maven au PATH système" -ForegroundColor White
    Write-Host "3. Puis utilisez: mvn clean verify sonar:sonar -Dsonar.login=$env:SONAR_TOKEN" -ForegroundColor White
}

