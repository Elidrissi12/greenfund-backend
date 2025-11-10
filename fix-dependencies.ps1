# Script PowerShell pour résoudre les problèmes de dépendances
Write-Host "🔧 Résolution des problèmes de dépendances Maven..." -ForegroundColor Cyan

# Vérifier que nous sommes dans le bon répertoire
$projectPath = "C:\Users\ABDO EL IDRISSI\Desktop\greenfund-backend"
if (Test-Path $projectPath) {
    Set-Location $projectPath
    Write-Host "✅ Répertoire trouvé : $projectPath" -ForegroundColor Green
} else {
    Write-Host "❌ Répertoire non trouvé : $projectPath" -ForegroundColor Red
    exit 1
}

# Vérifier que Maven Wrapper existe
if (Test-Path ".\mvnw.cmd") {
    Write-Host "✅ Maven Wrapper trouvé" -ForegroundColor Green
} else {
    Write-Host "❌ Maven Wrapper non trouvé" -ForegroundColor Red
    exit 1
}

# Étape 1 : Nettoyer le projet
Write-Host "`n🧹 Nettoyage du projet..." -ForegroundColor Yellow
& .\mvnw.cmd clean
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Erreur lors du nettoyage" -ForegroundColor Red
    exit 1
}

# Étape 2 : Télécharger les dépendances
Write-Host "`n📥 Téléchargement des dépendances..." -ForegroundColor Yellow
& .\mvnw.cmd dependency:resolve -U
if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Erreur lors du téléchargement des dépendances" -ForegroundColor Red
    exit 1
}

# Étape 3 : Compiler le projet
Write-Host "`n🔨 Compilation du projet..." -ForegroundColor Yellow
& .\mvnw.cmd compile -DskipTests
if ($LASTEXITCODE -eq 0) {
    Write-Host "`n✅ Compilation réussie !" -ForegroundColor Green
    Write-Host "`n📝 Prochaines étapes :" -ForegroundColor Cyan
    Write-Host "1. Ouvrez votre IDE (IntelliJ IDEA, Eclipse, etc.)" -ForegroundColor White
    Write-Host "2. Installez le plugin Lombok" -ForegroundColor White
    Write-Host "3. Activez l'annotation processing" -ForegroundColor White
    Write-Host "4. Rechargez le projet Maven" -ForegroundColor White
    Write-Host "5. Recompilez le projet dans l'IDE" -ForegroundColor White
} else {
    Write-Host "`n❌ Erreur lors de la compilation" -ForegroundColor Red
    Write-Host "Consultez les logs ci-dessus pour plus de détails" -ForegroundColor Yellow
    exit 1
}

Write-Host "`n✨ Script terminé !" -ForegroundColor Green

