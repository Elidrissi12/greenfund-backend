# 🔧 Résolution des Problèmes de Compilation

## ❌ Problèmes rencontrés

1. **JWT : cannot find symbol (Claims, Jwts, JwtException, Keys)**
2. **Lombok : cannot find symbol (getId(), getEmail(), setEmail(), etc.)**

## ✅ Solutions

### 1. Télécharger les dépendances Maven

**Dans le terminal :**

```bash
cd "C:\Users\ABDO EL IDRISSI\Desktop\greenfund-backend"
mvn clean install -DskipTests
```

Ou simplement :

```bash
mvn clean compile
```

Cela va télécharger toutes les dépendances nécessaires, y compris JWT et Lombok.

---

### 2. Activer Lombok dans IntelliJ IDEA

1. **Installer le plugin Lombok :**
   - File → Settings → Plugins
   - Cherchez "Lombok"
   - Installez et activez le plugin

2. **Activer l'annotation processing :**
   - File → Settings → Build, Execution, Deployment → Compiler → Annotation Processors
   - Cochez "Enable annotation processing"

3. **Recharger le projet :**
   - File → Invalidate Caches / Restart
   - Sélectionnez "Invalidate and Restart"

---

### 3. Activer Lombok dans Eclipse

1. **Installer Lombok :**
   - Téléchargez `lombok.jar` depuis https://projectlombok.org/download
   - Double-cliquez sur le fichier `lombok.jar`
   - Sélectionnez votre installation Eclipse
   - Redémarrez Eclipse

2. **Activer l'annotation processing :**
   - Project → Properties → Java Compiler → Annotation Processing
   - Cochez "Enable annotation processing"

---

### 4. Vérifier la configuration Maven

Assurez-vous que votre `pom.xml` contient bien :

```xml
<dependency>
    <groupId>org.projectlombok</groupId>
    <artifactId>lombok</artifactId>
    <optional>true</optional>
</dependency>
```

Et dans le plugin Spring Boot :

```xml
<plugin>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-maven-plugin</artifactId>
    <configuration>
        <excludes>
            <exclude>
                <groupId>org.projectlombok</groupId>
                <artifactId>lombok</artifactId>
            </exclude>
        </excludes>
    </configuration>
</plugin>
```

---

### 5. Vérifier les dépendances JWT

Assurez-vous que votre `pom.xml` contient bien :

```xml
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
```

**Note :** Les dépendances `jjwt-impl` et `jjwt-jackson` ne doivent PAS avoir `<scope>runtime</scope>` pour que la compilation fonctionne.

---

### 6. Recompiler le projet

**Dans IntelliJ IDEA :**
1. Build → Rebuild Project

**Dans Eclipse :**
1. Project → Clean → Clean all projects

**Dans le terminal :**
```bash
mvn clean compile
```

---

### 7. Si les problèmes persistent

1. **Supprimez le dossier `.m2` local (si nécessaire) :**
   ```bash
   # Windows
   rmdir /s "%USERPROFILE%\.m2\repository\io\jsonwebtoken"
   rmdir /s "%USERPROFILE%\.m2\repository\org\projectlombok"
   ```

2. **Téléchargez à nouveau les dépendances :**
   ```bash
   mvn clean install -U
   ```

3. **Vérifiez la version de Java :**
   ```bash
   java -version
   ```
   Doit être Java 17 ou supérieur.

---

## ✅ Vérification

Après avoir suivi ces étapes, le projet devrait compiler sans erreurs :

```bash
mvn clean compile
```

Vous devriez voir :
```
[INFO] BUILD SUCCESS
```

---

## 📝 Notes importantes

1. **Lombok nécessite un plugin dans l'IDE** pour que les getters/setters soient reconnus
2. **Les dépendances JWT doivent être compilées** (pas seulement runtime)
3. **Java 17+ est requis** pour Spring Boot 3.2.0
4. **Maven doit télécharger les dépendances** avant la compilation

---

## 🆘 Si rien ne fonctionne

1. Vérifiez que vous utilisez bien Java 17 ou 21
2. Vérifiez que Maven est installé : `mvn --version`
3. Vérifiez la connexion internet (pour télécharger les dépendances)
4. Vérifiez les logs Maven pour voir les erreurs exactes

---

*Guide de résolution des problèmes - GreenFund Backend*

