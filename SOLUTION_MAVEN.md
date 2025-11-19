# 🔧 Solution : Problème avec mvnw.cmd

## ❌ Problème

L'erreur `"était inattendu"` avec `mvnw.cmd` est généralement causée par :
- Des espaces dans le chemin `JAVA_HOME`
- Des caractères spéciaux dans les variables d'environnement
- Un problème avec le wrapper Maven

## ✅ Solutions

### Solution 1 : Utiliser le script PowerShell corrigé

```powershell
.\run-sonar-fixed.ps1
```

### Solution 2 : Installer Maven globalement

1. **Télécharger Maven** :
   - Allez sur https://maven.apache.org/download.cgi
   - Téléchargez `apache-maven-3.9.x-bin.zip`

2. **Extraire et configurer** :
   - Extrayez dans `C:\Program Files\Apache\maven` (ou autre)
   - Ajoutez `C:\Program Files\Apache\maven\bin` au PATH système

3. **Vérifier l'installation** :
   ```powershell
   mvn --version
   ```

4. **Lancer l'analyse** :
   ```powershell
   $env:SONAR_TOKEN="sqp_41da5aa48bfc8d008d2c6a01596327a959e14f28"
   mvn clean verify sonar:sonar
   ```

### Solution 3 : Corriger JAVA_HOME

Si votre `JAVA_HOME` contient des espaces :

1. **Vérifier JAVA_HOME** :
   ```powershell
   echo $env:JAVA_HOME
   ```

2. **Si le chemin contient des espaces**, mettez-le entre guillemets dans les variables d'environnement système :
   - Ouvrez "Variables d'environnement" dans Windows
   - Modifiez `JAVA_HOME` et mettez le chemin entre guillemets si nécessaire

### Solution 4 : Utiliser le chemin complet de Java

Créez un script qui utilise directement Java :

```powershell
# Trouver Java
$javaPath = (Get-Command java).Source
$javaHome = Split-Path (Split-Path $javaPath)

# Configurer JAVA_HOME pour cette session
$env:JAVA_HOME = $javaHome

# Lancer l'analyse
$env:SONAR_TOKEN="sqp_41da5aa48bfc8d008d2c6a01596327a959e14f28"
.\mvnw.cmd clean verify sonar:sonar
```

## 🎯 Recommandation

**La solution la plus simple** : Installer Maven globalement (Solution 2) pour éviter les problèmes avec le wrapper.

Une fois Maven installé, vous pouvez utiliser directement :
```powershell
mvn clean verify sonar:sonar -Dsonar.login=sqp_41da5aa48bfc8d008d2c6a01596327a959e14f28
```

---

**Essayez d'abord** : `.\run-sonar-fixed.ps1`

