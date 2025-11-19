# 🚀 Lancer l'analyse SonarQube

## ⚡ Méthode rapide (Recommandée)

### Option 1 : Script PowerShell
```powershell
cd greenfund-backend
.\run-sonar.ps1
```

### Option 2 : Script Batch
```cmd
cd greenfund-backend
run-sonar.bat
```

## 📝 Méthode manuelle

### 1. Configurer le token (PowerShell)
```powershell
$env:SONAR_TOKEN="sqp_41da5aa48bfc8d008d2c6a01596327a959e14f28"
```

### 2. Lancer l'analyse
```powershell
cd greenfund-backend
.\mvnw.cmd clean verify sonar:sonar
```

### Ou directement avec le token dans la commande
```powershell
.\mvnw.cmd clean verify sonar:sonar -Dsonar.login=sqp_41da5aa48bfc8d008d2c6a01596327a959e14f28
```

**Note** : Si vous avez Maven installé globalement, vous pouvez utiliser `mvn` au lieu de `.\mvnw.cmd`

## ✅ Vérifier que SonarQube est démarré

Avant de lancer l'analyse, assurez-vous que SonarQube est démarré :

1. Ouvrez http://localhost:9000
2. Vous devriez voir l'interface SonarQube
3. Si ce n'est pas le cas, démarrez SonarQube :
   ```powershell
   docker start sonarqube
   ```
   Ou si vous l'avez installé localement, lancez-le manuellement.

## 📊 Voir les résultats

Une fois l'analyse terminée :

1. Allez sur http://localhost:9000
2. Cliquez sur le projet **"GreenFund Backend"**
3. Vous verrez :
   - ✅ **Code Coverage** (couverture de code)
   - ⚠️ **Code Smells** (problèmes de qualité)
   - 🐛 **Bugs** (erreurs potentielles)
   - 🔒 **Security Hotspots** (vulnérabilités)

## 🎯 Rapports JaCoCo locaux

Pour voir le rapport JaCoCo sans SonarQube :

```powershell
.\mvnw.cmd clean test jacoco:report
```

Puis ouvrez dans votre navigateur :
```
greenfund-backend/target/site/jacoco/index.html
```

## ⚠️ Note de sécurité

Le token est maintenant dans les scripts. Pour la sécurité :
- Ne commitez **PAS** ces scripts dans Git
- Ajoutez-les au `.gitignore` si nécessaire
- Le token est personnel et ne doit pas être partagé

---

**Prêt à analyser !** 🎉

