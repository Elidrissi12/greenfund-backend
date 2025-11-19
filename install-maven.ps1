# Script pour installer Maven automatiquement (optionnel)
# Usage: .\install-maven.ps1

Write-Host "Installation de Maven..." -ForegroundColor Yellow

$mavenVersion = "3.9.6"
$mavenUrl = "https://dlcdn.apache.org/maven/maven-3/$mavenVersion/binaries/apache-maven-$mavenVersion-bin.zip"
$installDir = "C:\Program Files\Apache"
$mavenDir = "$installDir\apache-maven-$mavenVersion"

# Vérifier si Maven est déjà installé
if (Test-Path "$mavenDir\bin\mvn.cmd") {
    Write-Host "Maven est déjà installé dans: $mavenDir" -ForegroundColor Green
    Write-Host "Ajoutez $mavenDir\bin au PATH système" -ForegroundColor Yellow
    exit 0
}

Write-Host "Téléchargement de Maven $mavenVersion..." -ForegroundColor Yellow

# Créer le dossier d'installation
if (-not (Test-Path $installDir)) {
    New-Item -ItemType Directory -Path $installDir -Force | Out-Null
}

# Télécharger Maven
$zipFile = "$env:TEMP\apache-maven-$mavenVersion-bin.zip"
try {
    Invoke-WebRequest -Uri $mavenUrl -OutFile $zipFile -UseBasicParsing
    Write-Host "Téléchargement terminé" -ForegroundColor Green
} catch {
    Write-Host "Erreur lors du téléchargement: $_" -ForegroundColor Red
    exit 1
}

# Extraire Maven
Write-Host "Extraction de Maven..." -ForegroundColor Yellow
try {
    Expand-Archive -Path $zipFile -DestinationPath $installDir -Force
    Write-Host "Extraction terminée" -ForegroundColor Green
} catch {
    Write-Host "Erreur lors de l'extraction: $_" -ForegroundColor Red
    exit 1
}

# Nettoyer
Remove-Item $zipFile -ErrorAction SilentlyContinue

Write-Host ""
Write-Host "✅ Maven installé dans: $mavenDir" -ForegroundColor Green
Write-Host ""
Write-Host "Étapes suivantes:" -ForegroundColor Yellow
Write-Host "1. Ajoutez au PATH système: $mavenDir\bin" -ForegroundColor White
Write-Host "2. Redémarrez PowerShell" -ForegroundColor White
Write-Host "3. Vérifiez avec: mvn --version" -ForegroundColor White
Write-Host ""
Write-Host "Ou utilisez directement:" -ForegroundColor Yellow
Write-Host "& `"$mavenDir\bin\mvn.cmd`" clean verify sonar:sonar -Dsonar.login=sqp_41da5aa48bfc8d008d2c6a01596327a959e14f28" -ForegroundColor Cyan

