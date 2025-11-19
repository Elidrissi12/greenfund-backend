# Guide : Configuration SonarQube + JaCoCo pour le Backend

## 📋 Prérequis

1. **Java 17+** installé
2. **Maven** installé
3. **SonarQube** installé et démarré (voir section Installation)

## 🔧 Installation de SonarQube

### Option 1 : Installation locale (Recommandé pour développement)

1. **Télécharger SonarQube** :
   - Allez sur https://www.sonarqube.org/downloads/
   - Téléchargez la version Community Edition

2. **Extraire et démarrer** :
   ```powershell
   # Extraire dans un dossier (ex: C:\sonarqube)
   # Démarrer SonarQube
   cd C:\sonarqube\bin\windows-x86-64
   .\StartSonar.bat
   ```

3. **Accéder à l'interface** :
   - Ouvrez http://localhost:9000
   - Identifiants par défaut : `admin` / `admin`
   - Changez le mot de passe au premier démarrage

### Option 2 : Docker (Plus simple)

```powershell
docker run -d --name sonarqube -e SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true -p 9000:9000 sonarqube:latest
```

Attendez quelques minutes, puis accédez à http://localhost:9000

## ⚙️ Configuration Maven (pom.xml)

Le fichier `pom.xml` a été mis à jour avec :
- **JaCoCo Maven Plugin** : pour générer les rapports de couverture de code
- **SonarQube Maven Plugin** : pour envoyer les résultats à SonarQube

## 🚀 Utilisation

### 1. Générer le rapport JaCoCo

```powershell
cd greenfund-backend
mvn clean test jacoco:report
```

Le rapport sera généré dans : `target/site/jacoco/index.html`

### 2. Analyser avec SonarQube

#### Étape 1 : Créer un projet dans SonarQube

1. Connectez-vous à http://localhost:9000
2. Cliquez sur **"Create Project"**
3. Choisissez **"Manually"**
4. Remplissez :
   - **Project key** : `greenfund-backend`
   - **Display name** : `GreenFund Backend`
5. Cliquez sur **"Set Up"**
6. Choisissez **"With Maven"**
7. Copiez le token généré (ex: `sqp_xxxxxxxxxxxxx`)

#### Étape 2 : Configurer le token

**Windows PowerShell** :
```powershell
$env:SONAR_TOKEN="sqp_xxxxxxxxxxxxx"
```

**Windows CMD** :
```cmd
set SONAR_TOKEN=sqp_xxxxxxxxxxxxx
```

**Linux/Mac** :
```bash
export SONAR_TOKEN=sqp_xxxxxxxxxxxxx
```

#### Étape 3 : Lancer l'analyse

```powershell
mvn clean verify sonar:sonar
```

Ou avec le token directement :
```powershell
mvn clean verify sonar:sonar -Dsonar.login=sqp_xxxxxxxxxxxxx
```

### 3. Voir les résultats

1. Allez sur http://localhost:9000
2. Cliquez sur votre projet **"GreenFund Backend"**
3. Vous verrez :
   - **Code Coverage** (couverture de code)
   - **Code Smells** (problèmes de qualité)
   - **Bugs** (erreurs potentielles)
   - **Security Hotspots** (vulnérabilités)
   - **Duplications** (code dupliqué)

## 📊 Rapports JaCoCo locaux

Pour voir le rapport JaCoCo sans SonarQube :

```powershell
mvn clean test jacoco:report
```

Puis ouvrez dans votre navigateur :
```
greenfund-backend/target/site/jacoco/index.html
```

## 🎯 Objectifs de qualité recommandés

- **Couverture de code** : ≥ 70%
- **Code Smells** : 0 critiques, < 10 majeurs
- **Bugs** : 0 critiques, 0 majeurs
- **Security Hotspots** : 0 critiques
- **Duplications** : < 3%

## 🔍 Commandes utiles

### Générer uniquement le rapport JaCoCo
```powershell
mvn jacoco:report
```

### Vérifier la couverture (échoue si < seuil)
```powershell
mvn jacoco:check
```

### Analyser avec SonarQube (sans tests)
```powershell
mvn sonar:sonar
```

### Tout en une commande (tests + JaCoCo + SonarQube)
```powershell
mvn clean verify sonar:sonar
```

## 🐛 Résolution de problèmes

### Erreur : "Unable to execute SonarQube"
**Solution** : Vérifiez que SonarQube est démarré sur le port 9000

### Erreur : "Authentication failed"
**Solution** : Vérifiez que le token SONAR_TOKEN est correctement configuré

### Erreur : "Project already exists"
**Solution** : Supprimez le projet dans SonarQube ou changez le projectKey dans `sonar-project.properties`

### Pas de rapport JaCoCo généré
**Solution** : Assurez-vous d'avoir des tests et qu'ils s'exécutent correctement :
```powershell
mvn test
```

## 📝 Configuration avancée

### Exclure des fichiers de l'analyse

Créez/modifiez `sonar-project.properties` :
```properties
sonar.exclusions=**/model/dto/**,**/config/**,**/exception/**
```

### Configurer les seuils de couverture

Dans `pom.xml`, section `jacoco-maven-plugin`, vous pouvez ajuster :
```xml
<rules>
  <rule>
    <limits>
      <limit>
        <counter>LINE</counter>
        <value>COVEREDRATIO</value>
        <minimum>0.70</minimum>
      </limit>
    </limits>
  </rule>
</rules>
```

## 🔗 Ressources

- **Documentation SonarQube** : https://docs.sonarqube.org/
- **Documentation JaCoCo** : https://www.jacoco.org/jacoco/trunk/doc/
- **Maven SonarQube Plugin** : https://docs.sonarqube.org/latest/analysis/scan/sonarscanner-for-maven/

---

**Note** : Pour une utilisation en CI/CD, configurez SonarQube dans votre pipeline (GitHub Actions, GitLab CI, Jenkins, etc.)

