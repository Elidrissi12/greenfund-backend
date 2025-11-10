# ⚙️ Configuration MySQL

## 📝 Étape 1 : Configurer le mot de passe MySQL

1. Ouvrez le fichier : `src/main/resources/application.properties`

2. Modifiez la ligne suivante avec votre mot de passe MySQL :

```properties
spring.datasource.password=VOTRE_MOT_DE_PASSE_MYSQL
```

**Exemple :**
```properties
spring.datasource.password=monpassword123
```

Si vous n'avez pas de mot de passe MySQL, laissez la ligne vide :
```properties
spring.datasource.password=
```

---

## 📝 Étape 2 : Vérifier que MySQL est démarré

### Windows
```bash
net start MySQL80
```

### Mac/Linux
```bash
sudo systemctl start mysql
```

---

## 📝 Étape 3 : Créer la base de données

Connectez-vous à MySQL et exécutez :

```sql
CREATE DATABASE IF NOT EXISTS greenfund_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

---

## 📝 Étape 4 : Lancer l'application

```bash
./mvnw spring-boot:run
```

L'application créera automatiquement les tables dans la base de données.

---

## ✅ Vérification

Si tout fonctionne correctement, vous devriez voir dans les logs :

```
Hibernate: create table projects ...
Hibernate: create table users ...
Hibernate: create table investments ...
```

Et à la fin :
```
Admin user created: admin@greenfund.com / admin123
```

---

*Configuration MySQL - GreenFund Backend*

