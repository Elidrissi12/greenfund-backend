# 🧪 Tests unitaires JUnit & JaCoCo

## 1. Lancer les tests

```powershell
cd greenfund-backend
mvn test
```

## 2. Générer le rapport de couverture JaCoCo

```powershell
cd greenfund-backend
mvn clean test jacoco:report
```

Le rapport HTML est généré dans :
```
C:\Users\ABDO EL IDRISSI\Desktop\greenfund-backend\target\site\jacoco\index.html
```

Ouvrez ce fichier dans votre navigateur pour voir la couverture par classe.

## 3. Analyse complète avec SonarQube

```powershell
cd greenfund-backend
mvn clean verify sonar:sonar -Dsonar.login=<VOTRE_TOKEN>
```

(Le script `run-sonar.ps1` configure déjà le token et exécute cette commande.)

## 4. Tests ajoutés

### `JwtTokenProviderTest`
- Vérifie la génération de token JWT
- Vérifie la validation du token
- Vérifie l'extraction de l'ID utilisateur

### `ProjectTest`
- Vérifie le calcul `getProgress()`
- Vérifie la méthode `isFunded()`

## 5. Ajouter un nouveau test

1. Créez une classe dans `src/test/java/...`
2. Annoter avec `@Test` (JUnit 5)
3. Utilisez AssertJ pour des assertions lisibles (`assertThat`)
4. Exécutez `mvn test`

Exemple :
```java
@Test
void shouldReturnTrueWhenFunded() {
    assertThat(project.isFunded()).isTrue();
}
```

## 6. Conseils pour augmenter la couverture

- Tester les services avec Mockito (`@ExtendWith(MockitoExtension.class)`)
- Tester les contrôleurs avec `@WebMvcTest` et `MockMvc`
- Ajouter des tests d'intégration avec `@SpringBootTest`
- Couvrir les branches (conditions `if` / `else`)

## 7. Nettoyer les rapports

```powershell
mvn clean
```

Supprime le dossier `target` (rapports, classes compilées).

---

✅ Les tests unitaires améliorent la qualité du code et la confiance dans vos livraisons.
