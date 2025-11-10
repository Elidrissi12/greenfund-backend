# 🔍 Problèmes Identifiés dans le Backend

## ✅ Problèmes déjà corrigés

1. ✅ **Package `enum` renommé en `enums`** - Tous les imports mis à jour
2. ✅ **Dépendances JWT** - `jjwt-impl` et `jjwt-jackson` sans scope runtime
3. ✅ **Configuration Lombok** - Ajoutée dans le plugin Spring Boot
4. ✅ **Import `SecretKey`** - Changé en `java.security.Key`

---

## ⚠️ Problèmes restants à vérifier

### 1. Dépendances Maven non téléchargées

**Symptômes :**
- `cannot find symbol: class Claims`
- `cannot find symbol: class Jwts`
- `cannot find symbol: class JwtException`
- `cannot find symbol: variable Keys`

**Solution :**
```powershell
cd "C:\Users\ABDO EL IDRISSI\Desktop\greenfund-backend"
.\mvnw.cmd clean install -DskipTests
```

---

### 2. Lombok non activé dans l'IDE

**Symptômes :**
- `cannot find symbol: method getId()`
- `cannot find symbol: method getEmail()`
- `cannot find symbol: method setEmail()`
- `cannot find symbol: method setPassword()`

**Solution pour IntelliJ IDEA :**
1. Installer le plugin Lombok : `File` → `Settings` → `Plugins` → Chercher "Lombok"
2. Activer l'annotation processing : `File` → `Settings` → `Build, Execution, Deployment` → `Compiler` → `Annotation Processors` → ✅ Enable annotation processing
3. Recharger le projet : `File` → `Invalidate Caches / Restart`

**Solution pour Eclipse :**
1. Télécharger `lombok.jar` depuis https://projectlombok.org/download
2. Double-cliquer sur `lombok.jar` et installer pour Eclipse
3. Redémarrer Eclipse
4. Activer l'annotation processing : `Project` → `Properties` → `Java Compiler` → `Annotation Processing` → ✅ Enable annotation processing

---

### 3. Dossier `enum` vide

**Problème :** Le dossier `model/enum/` existe encore mais est vide, ce qui peut causer de la confusion.

**Solution :** Supprimer le dossier vide `src/main/java/com/greenfund/greenfund_backend/model/enum/`

---

## 📋 Vérification de la structure

### ✅ Structure correcte actuelle :

```
model/
├── dto/
│   ├── request/
│   └── response/
├── entity/
│   ├── User.java
│   ├── Project.java
│   └── Investment.java
├── enums/          ← ✅ Correct (pas "enum")
│   ├── Role.java
│   ├── EnergyType.java
│   └── ProjectStatus.java
└── enum/           ← ❌ À supprimer (vide)
```

---

## 🔧 Actions à effectuer

### Étape 1 : Télécharger les dépendances

```powershell
cd "C:\Users\ABDO EL IDRISSI\Desktop\greenfund-backend"
.\mvnw.cmd clean install -DskipTests
```

### Étape 2 : Activer Lombok dans l'IDE

Voir les instructions ci-dessus selon votre IDE.

### Étape 3 : Supprimer le dossier `enum` vide

Supprimez manuellement le dossier :
`src/main/java/com/greenfund/greenfund_backend/model/enum/`

### Étape 4 : Recompiler dans l'IDE

- **IntelliJ IDEA :** `Build` → `Rebuild Project`
- **Eclipse :** `Project` → `Clean` → `Clean all projects`

---

## ✅ Vérification finale

Après avoir effectué ces actions, le projet devrait :
- ✅ Compiler sans erreurs
- ✅ Reconnaître les classes JWT (Claims, Jwts, etc.)
- ✅ Reconnaître les méthodes Lombok (getters/setters)

---

## 📝 Fichiers à vérifier

1. ✅ `pom.xml` - Dépendances correctes
2. ✅ `JwtTokenProvider.java` - Imports corrects
3. ✅ `User.java` - Annotations Lombok présentes
4. ✅ Tous les imports `model.enums.*` - Corrects
5. ⚠️ Dossier `enum/` vide - À supprimer

---

## 🆘 Si les problèmes persistent

1. Vérifiez que Java 17+ est installé : `java -version`
2. Vérifiez que Maven Wrapper fonctionne : `.\mvnw.cmd --version`
3. Vérifiez votre connexion internet (pour télécharger les dépendances)
4. Consultez les logs de compilation pour plus de détails

---

*Document créé après analyse complète du backend*

