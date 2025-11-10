# 🔧 Solution Complète aux Problèmes de Compilation

## 📋 Problèmes identifiés

1. ❌ **JWT : cannot find symbol** (Claims, Jwts, JwtException, Keys)
2. ❌ **Lombok : cannot find symbol** (getId(), getEmail(), setEmail(), etc.)

## ✅ Solutions par IDE

### Solution 1 : IntelliJ IDEA

#### Étape 1 : Télécharger les dépendances avec Maven Wrapper

```powershell
# Dans PowerShell
cd "C:\Users\ABDO EL IDRISSI\Desktop\greenfund-backend"
.\mvnw.cmd clean install -DskipTests
```

#### Étape 2 : Installer et activer Lombok

1. **Installer le plugin Lombok :**
   - `File` → `Settings` (ou `Ctrl+Alt+S`)
   - `Plugins` → Chercher "Lombok"
   - Installer le plugin "Lombok" (par JetBrains)
   - Redémarrer IntelliJ

2. **Activer l'annotation processing :**
   - `File` → `Settings` → `Build, Execution, Deployment` → `Compiler` → `Annotation Processors`
   - ✅ Cochez **"Enable annotation processing"**
   - Cliquez sur **"Apply"** puis **"OK"**

#### Étape 3 : Recharger le projet Maven

1. Ouvrez la vue Maven : `View` → `Tool Windows` → `Maven`
2. Cliquez sur l'icône **"Reload All Maven Projects"** (🔄)
3. Ou : `File` → `Invalidate Caches / Restart` → `Invalidate and Restart`

#### Étape 4 : Vérifier la configuration du projet

1. `File` → `Project Structure` (`Ctrl+Alt+Shift+S`)
2. `Project` → Vérifiez que **SDK** est Java 17 ou 21
3. `Modules` → Vérifiez que le module est configuré correctement

#### Étape 5 : Recompiler

1. `Build` → `Rebuild Project`
2. Attendez la fin de la compilation

---

### Solution 2 : Eclipse

#### Étape 1 : Télécharger les dépendances

```powershell
cd "C:\Users\ABDO EL IDRISSI\Desktop\greenfund-backend"
.\mvnw.cmd clean install -DskipTests
```

#### Étape 2 : Importer le projet

1. `File` → `Import` → `Maven` → `Existing Maven Projects`
2. Sélectionnez le dossier `greenfund-backend`
3. Cliquez sur `Finish`

#### Étape 3 : Installer Lombok

1. Téléchargez `lombok.jar` depuis : https://projectlombok.org/download
2. Double-cliquez sur `lombok.jar`
3. Sélectionnez votre installation Eclipse
4. Cliquez sur `Install / Update`
5. Redémarrez Eclipse

#### Étape 4 : Activer l'annotation processing

1. Clic droit sur le projet → `Properties`
2. `Java Compiler` → `Annotation Processing`
3. ✅ Cochez **"Enable annotation processing"**
4. `Apply and Close`

#### Étape 5 : Mettre à jour le projet

1. Clic droit sur le projet → `Maven` → `Update Project`
2. ✅ Cochez **"Force Update of Snapshots/Releases"**
3. Cliquez sur `OK`

---

### Solution 3 : VSCode

#### Étape 1 : Installer les extensions

1. Installez **"Extension Pack for Java"** (Microsoft)
2. Installez **"Lombok Annotations Support for VS Code"**

#### Étape 2 : Télécharger les dépendances

```powershell
cd "C:\Users\ABDO EL IDRISSI\Desktop\greenfund-backend"
.\mvnw.cmd clean install -DskipTests
```

#### Étape 3 : Recharger la fenêtre

1. `Ctrl+Shift+P`
2. Tapez "Java: Clean Java Language Server Workspace"
3. Sélectionnez et confirmez

---

## 🔍 Vérification manuelle

### Vérifier que les dépendances sont téléchargées

Les dépendances JWT et Lombok doivent être dans :
```
C:\Users\ABDO EL IDRISSI\.m2\repository\io\jsonwebtoken\
C:\Users\ABDO EL IDRISSI\.m2\repository\org\projectlombok\
```

### Vérifier le pom.xml

Assurez-vous que votre `pom.xml` contient bien :

```xml
<!-- JWT -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.12.3</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.12.3</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.12.3</version>
</dependency>

<!-- Lombok -->
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

---

## 🚨 Si les problèmes persistent

### Solution alternative : Utiliser Maven Wrapper

1. **Ouvrez PowerShell dans le dossier du projet :**
   ```powershell
   cd "C:\Users\ABDO EL IDRISSI\Desktop\greenfund-backend"
   ```

2. **Téléchargez les dépendances :**
   ```powershell
   .\mvnw.cmd dependency:resolve
   ```

3. **Compilez le projet :**
   ```powershell
   .\mvnw.cmd clean compile
   ```

4. **Si ça ne fonctionne pas, forcez le téléchargement :**
   ```powershell
   .\mvnw.cmd clean install -U -DskipTests
   ```

### Vérifier la version de Java

```powershell
java -version
```

Doit afficher Java 17 ou 21.

### Vérifier Maven Wrapper

```powershell
.\mvnw.cmd --version
```

---

## 📝 Résumé des actions

1. ✅ Télécharger les dépendances avec `mvnw.cmd`
2. ✅ Installer le plugin Lombok dans l'IDE
3. ✅ Activer l'annotation processing
4. ✅ Recharger le projet Maven
5. ✅ Recompiler le projet

---

## ✅ Vérification finale

Après avoir suivi ces étapes, vous devriez pouvoir :
- ✅ Voir les imports JWT résolus (pas d'erreur rouge)
- ✅ Voir les getters/setters Lombok reconnus
- ✅ Compiler le projet sans erreurs

---

## 🆘 Support supplémentaire

Si rien ne fonctionne :
1. Vérifiez votre connexion internet
2. Vérifiez les logs de l'IDE pour plus de détails
3. Essayez de créer un nouveau projet Spring Boot pour tester
4. Vérifiez que Java 17+ est installé

---

*Solution complète - GreenFund Backend*

