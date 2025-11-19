# 🧪 Guide Rapide : Tester avec SonarQube et JaCoCo

## ⚡ Démarrage rapide

### 1. Démarrer SonarQube

**Option A : Docker (Recommandé)**
```powershell
docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest
```

**Option B : Installation locale**
```powershell
# Télécharger depuis https://www.sonarqube.org/downloads/
# Extraire et lancer
cd C:\sonarqube\bin\windows-x86-64
.\StartSonar.bat
```

Attendez 1-2 minutes, puis ouvrez http://localhost:9000
- Login : `admin`
- Password : `admin` (changez-le au premier démarrage)

### 2. Créer un projet dans SonarQube

1. Connectez-vous à http://localhost:9000
2. Cliquez sur **"Create Project"** → **"Manually"**
3. Remplissez :
   - **Project key** : `greenfund-backend`
   - **Display name** : `GreenFund Backend`
4. Cliquez sur **"Set Up"** → **"With Maven"**
5. **Copiez le token** généré (ex: `sqp_xxxxxxxxxxxxx`)

### 3. Configurer le token

**Windows PowerShell** :
```powershell
$env:SONAR_TOKEN="sqp_xxxxxxxxxxxxx"
```

**Windows CMD** :
```cmd
set SONAR_TOKEN=sqp_xxxxxxxxxxxxx
```

### 4. Lancer les tests et l'analyse

```powershell
cd greenfund-backend
mvn clean verify sonar:sonar
```

Ou avec le token directement :
```powershell
mvn clean verify sonar:sonar -Dsonar.login=sqp_xxxxxxxxxxxxx
```

### 5. Voir les résultats

1. Allez sur http://localhost:9000
2. Cliquez sur **"GreenFund Backend"**
3. Vous verrez :
   - ✅ **Code Coverage** (couverture de code)
   - ⚠️ **Code Smells** (problèmes de qualité)
   - 🐛 **Bugs** (erreurs potentielles)
   - 🔒 **Security Hotspots** (vulnérabilités)

## 📊 Rapports JaCoCo locaux

Pour voir le rapport sans SonarQube :

```powershell
mvn clean test jacoco:report
```

Ouvrez dans votre navigateur :
```
greenfund-backend/target/site/jacoco/index.html
```

## 🎯 Commandes utiles

### Générer uniquement le rapport JaCoCo
```powershell
mvn jacoco:report
```

### Vérifier la couverture (échoue si < 50%)
```powershell
mvn jacoco:check
```

### Analyser avec SonarQube (sans tests)
```powershell
mvn sonar:sonar
```

### Tout en une commande
```powershell
mvn clean verify sonar:sonar
```

## 🐛 Problèmes courants

### SonarQube ne démarre pas
- Vérifiez que le port 9000 n'est pas utilisé
- Vérifiez les logs dans `sonarqube/logs/sonar.log`

### Erreur "Authentication failed"
- Vérifiez que le token SONAR_TOKEN est correct
- Régénérez un nouveau token dans SonarQube

### Pas de couverture de code
- Assurez-vous d'avoir des tests : `mvn test`
- Vérifiez que les tests passent

## 📝 Configuration

Les fichiers de configuration sont déjà créés :
- ✅ `pom.xml` : Plugins JaCoCo et SonarQube configurés
- ✅ `sonar-project.properties` : Configuration SonarQube

## 🎓 Objectifs recommandés

- **Couverture de code** : ≥ 70%
- **Code Smells** : 0 critiques
- **Bugs** : 0 critiques, 0 majeurs
- **Security Hotspots** : 0 critiques

---

**Guide complet** : Voir `SONARQUBE_SETUP.md` pour plus de détails.

