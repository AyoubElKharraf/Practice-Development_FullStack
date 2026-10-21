# Travaux pratiques - Développement Full Stack

Collection de travaux pratiques réalisés dans le cadre du Master M2I - Développement Full Stack. Le dépôt suit une progression volontaire : partir d'une page web rendue côté serveur, exposer ensuite une API REST consommée par une interface React, puis construire une application métier complète avec Angular, Spring Boot, JPA et MySQL.

> **État du dépôt**  
> Les trois TPs sont des applications autonomes. Ils ne constituent pas une seule application à lancer simultanément : chaque dossier possède son propre build, son propre périmètre fonctionnel et, selon le cas, son propre front-end.

## Sommaire

- [Objectifs pédagogiques](#objectifs-pédagogiques)
- [Vue d'ensemble](#vue-densemble)
- [Architecture du dépôt](#architecture-du-dépôt)
- [Prérequis](#prérequis)
- [Démarrage rapide](#démarrage-rapide)
- [TP1 - Formulaire Thymeleaf](#tp1---formulaire-thymeleaf)
- [TP2 - Gestion des étudiants](#tp2---gestion-des-étudiants)
- [TP3 - RestoPilot](#tp3---restopilot)
- [Contrats HTTP](#contrats-http)
- [Méthode de travail recommandée](#méthode-de-travail-recommandée)
- [Tests et qualité](#tests-et-qualité)
- [Dépannage](#dépannage)
- [Limites et évolutions](#limites-et-évolutions)

## Objectifs pédagogiques

Ce dépôt met en pratique les compétences suivantes :

1. Créer une application web avec Spring Boot et Gradle.
2. Recevoir, traiter et afficher des données de formulaire avec un contrôleur MVC.
3. Construire une API REST CRUD avec Spring Boot.
4. Séparer les responsabilités entre contrôleur, service et modèle.
5. Consommer une API HTTP depuis React et Angular.
6. Gérer les formulaires, les états de chargement et les erreurs côté client.
7. Persister des données relationnelles avec Spring Data JPA et MySQL.
8. Modéliser un domaine métier comprenant des relations entre entités.
9. Valider les entrées HTTP et retourner des statuts REST cohérents.

## Vue d'ensemble

| TP | Sujet | Back-end | Front-end | Données | Port par défaut |
| --- | --- | --- | --- | --- | --- |
| [TP1](./TP1) | Formulaire d'informations | Spring Boot 4.1.1, Gradle | Thymeleaf, HTML, CSS | Mémoire de requête | `8081` |
| [TP2](./TP2) | Gestion des étudiants | Spring Boot 3.2.0, Maven | React 19, Vite 8 | Mémoire de l'application | `8081` / `5173` |
| [TP3](./TP3) | Gestion de restaurant | Spring Boot 3.5.6, Maven, JPA | Angular 20 | MySQL | `8081` / `4200` |

Les ports des trois back-ends sont configurés sur `8081`. Il faut donc arrêter un TP avant d'en démarrer un autre, ou modifier `server.port` ainsi que l'URL utilisée par le front-end concerné.

## Architecture du dépôt

```text
TPs/
├── README.md
├── TP1/
│   ├── build.gradle
│   ├── settings.gradle
│   └── src/
│       ├── main/java/com/example/tp1/
│       │   └── controller/HomeController.java
│       ├── main/resources/templates/
│       │   ├── formulaire.html
│       │   └── affichage.html
│       └── test/
├── TP2/
│   ├── students-api/
│   │   ├── pom.xml
│   │   └── src/main/java/com/tp/students/
│   │       ├── controller/
│   │       ├── model/
│   │       └── service/
│   └── students-frontend/
│       ├── package.json
│       └── src/
│           ├── App.jsx
│           └── components/
└── TP3/
    ├── backend/
    │   ├── pom.xml
    │   └── src/main/java/com/restaurant/
    │       ├── dto/
    │       ├── model/
    │       ├── repository/
    │       ├── service/
    │       └── web/
    └── frontend/
        ├── package.json
        └── src/app/
```

Les dossiers `build/`, `target/`, `node_modules/`, `dist/` et les caches d'IDE sont des artefacts locaux générés par les outils. Ils ne font pas partie de l'architecture fonctionnelle du dépôt.

## Prérequis

- **Java 17** ou version compatible avec les toolchains configurées.
- **Git**.
- **Node.js et npm** :
  - TP2 : Vite/React.
  - TP3 : Angular CLI 20 et TypeScript 5.9.
- **Maven 3.9+** pour TP2 et TP3, ou les wrappers Maven/Gradle disponibles dans l'environnement.
- **MySQL 8+** uniquement pour TP3.
- Un navigateur récent et un éditeur de code, idéalement VS Code ou IntelliJ IDEA.

Vérifier l'environnement :

```bash
java -version
mvn -version
node --version
npm --version
```

## Démarrage rapide

Les commandes ci-dessous sont données depuis la racine du dépôt. Sous Windows, utiliser PowerShell et remplacer `./` par `.\` si nécessaire.

### TP1

```bash
cd TP1
.\gradlew.bat bootRun
```

Ouvrir [http://localhost:8081](http://localhost:8081). Le formulaire est disponible à la racine `/`.

### TP2

Terminal 1 - API :

```bash
cd TP2/students-api
mvn spring-boot:run
```

Terminal 2 - interface React :

```bash
cd TP2/students-frontend
npm install
npm run dev
```

Ouvrir [http://localhost:5173](http://localhost:5173).

### TP3

1. Créer la base MySQL :

```sql
CREATE DATABASE restaurant_db;
```

2. Depuis `TP3/backend`, définir au minimum `DB_USERNAME` et `DB_PASSWORD`, ou créer un fichier local non versionné nommé `application-local.properties` :

```properties
spring.datasource.username=root
spring.datasource.password=votre_mot_de_passe
```

3. Démarrer l'API :

```bash
cd TP3/backend
mvn spring-boot:run
```

4. Dans un second terminal, démarrer Angular :

```bash
cd TP3/frontend
npm install
npm start
```

Ouvrir [http://localhost:4200](http://localhost:4200). Hibernate crée ou met à jour les tables via `spring.jpa.hibernate.ddl-auto=update`.

## TP1 - Formulaire Thymeleaf

### Concept

Le premier TP présente le cycle MVC classique côté serveur :

1. `GET /` affiche `formulaire.html`.
2. L'utilisateur saisit son nom, prénom, adresse e-mail et filière.
3. `POST /afficher` reçoit les paramètres du formulaire dans `HomeController`.
4. Le contrôleur place les valeurs dans le `Model`.
5. Thymeleaf rend `affichage.html` avec les données reçues.

Il s'agit d'une application pédagogique sans base de données ni API JSON. Les données sont transmises dans le contexte de la requête et ne sont pas conservées après le traitement.

### Technologies

- Spring Boot 4.1.1
- Gradle avec wrapper (`gradlew` / `gradlew.bat`)
- Spring MVC
- Thymeleaf
- HTML et CSS
- JUnit via `spring-boot-starter-test`

### Commandes utiles

```bash
cd TP1
.\gradlew.bat test
.\gradlew.bat bootRun
```

## TP2 - Gestion des étudiants

### Concept

Le TP2 découple l'application en deux projets :

- `students-api` expose une API REST Spring Boot.
- `students-frontend` fournit l'interface React consommant cette API avec `fetch`.

Le back-end conserve une liste d'étudiants en mémoire et l'initialise avec cinq exemples. Un redémarrage de l'API réinitialise donc les données.

### API REST

Base URL : `http://localhost:8081/api/students`

| Méthode | Route | Description | Réponse principale |
| --- | --- | --- | --- |
| `GET` | `/api/students` | Lister les étudiants | `200 OK` |
| `GET` | `/api/students/{id}` | Consulter un étudiant | `200 OK` ou `404 Not Found` |
| `POST` | `/api/students` | Ajouter un étudiant | `201 Created` |
| `PUT` | `/api/students/{id}` | Modifier un étudiant | `200 OK` ou `404 Not Found` |
| `DELETE` | `/api/students/{id}` | Supprimer un étudiant | `204 No Content` ou `404 Not Found` |

Exemple de payload :

```json
{
  "firstName": "Nadia",
  "lastName": "Martin",
  "email": "nadia.martin@etud.fr",
  "age": 22,
  "filiere": "Informatique"
}
```

### Organisation du code

- `StudentController` : routes HTTP, codes de statut et sérialisation JSON.
- `StudentService` : opérations CRUD et génération des identifiants.
- `Student` : modèle échangé avec le client.
- `students-frontend/src/App.jsx` : chargement des données et orchestration des actions.
- `components/` : tableau, formulaire d'ajout et modal d'édition.

### Commandes utiles

```bash
cd TP2/students-api
mvn test
mvn spring-boot:run
```

```bash
cd TP2/students-frontend
npm run lint
npm run build
npm run dev
```

Le contrôleur autorise l'origine Vite `http://localhost:5173` via CORS.

## TP3 - RestoPilot

### Concept

RestoPilot est l'application métier la plus complète du dépôt. Elle gère trois ressources liées :

- les plats de la carte ;
- les employés ;
- les commandes composées de plusieurs lignes de commande.

L'interface Angular propose trois onglets et communique avec l'API via `ApiService`. Le back-end utilise Spring Data JPA pour persister les entités dans MySQL.

### Modèle métier

```text
Employee ───────< CustomerOrder ───────< OrderItem >────── MenuItem
```

- Une commande peut être affectée à un employé.
- Une commande contient au moins une ligne grâce à `@NotEmpty`.
- Chaque ligne référence un plat et une quantité.
- Le statut d'une commande peut être `PENDING`, `IN_PROGRESS`, `DELIVERED` ou `CANCELLED`.

### API REST

Base URL : `http://localhost:8081/api`

| Ressource | Routes disponibles |
| --- | --- |
| Plats | `GET/POST /menu-items`, `GET/PUT/DELETE /menu-items/{id}` |
| Employés | `GET/POST /employees`, `GET/PUT/DELETE /employees/{id}` |
| Commandes | `GET/POST /orders`, `GET/PUT/DELETE /orders/{id}` |

Exemple de création de commande :

```json
{
  "orderDate": "2026-10-21T17:30:00",
  "status": "PENDING",
  "employeeId": 1,
  "items": [
    {
      "menuItemId": 1,
      "quantity": 2
    }
  ]
}
```

Le service de commande vérifie l'existence de l'employé et de chaque plat, reconstruit les lignes de commande et lève une erreur métier explicite lorsqu'une ressource est inconnue.

### Technologies

- Spring Boot 3.5.6
- Maven
- Spring Web, Spring Data JPA et Bean Validation
- MySQL Connector/J
- Angular 20, TypeScript et RxJS
- Formulaires Angular avec `FormsModule`
- CORS pour `http://localhost:4200`

### Configuration

Le fichier [application.properties](./TP3/backend/src/main/resources/application.properties) utilise les valeurs suivantes par défaut :

```text
URL MySQL : jdbc:mysql://localhost:3306/restaurant_db
Utilisateur : root
Mot de passe : valeur fournie par DB_PASSWORD
API : http://localhost:8081
```

Les identifiants peuvent être fournis par variables d'environnement :

```powershell
$env:DB_USERNAME = "root"
$env:DB_PASSWORD = "votre_mot_de_passe"
mvn spring-boot:run
```

Ne jamais versionner de mot de passe. Le fichier `application-local.properties` est prévu pour les réglages locaux et ignoré par Git.

### Commandes utiles

```bash
cd TP3/backend
mvn test
mvn package
```

```bash
cd TP3/frontend
npm test
npm run build
npm start
```

## Contrats HTTP

Les front-ends attendent que les back-ends soient démarrés sur les ports configurés :

| Client | URL d'API codée dans le client |
| --- | --- |
| TP2 React | `http://localhost:8081/api/students` |
| TP3 Angular | `http://localhost:8081/api` |

Si le port de l'API est modifié, mettre à jour à la fois `server.port` dans Spring Boot et l'URL dans `App.jsx` ou `api.service.ts`.

## Méthode de travail recommandée

1. Choisir un seul TP à exécuter afin d'éviter le conflit sur le port `8081`.
2. Démarrer le back-end avant le client React ou Angular.
3. Vérifier l'API directement avec le navigateur, `curl` ou un client REST.
4. Démarrer l'interface avec le script prévu dans son `package.json`.
5. Tester les parcours nominaux : création, lecture, modification et suppression.
6. Contrôler la console du navigateur et les logs Spring Boot en cas d'erreur.

## Tests et qualité

### Tests automatisés

- TP1 : tests Gradle/JUnit présents dans `src/test`.
- TP2 API : suite Maven/Spring Boot.
- TP2 front-end : lint Oxlint et build Vite.
- TP3 back-end : tests Maven/Spring Boot.
- TP3 front-end : tests Angular sans mode watch et build de production.

Commandes de vérification :

```bash
cd TP1
.\gradlew.bat test

cd ../TP2/students-api
mvn test

cd ../students-frontend
npm run lint
npm run build

cd ../../TP3/backend
mvn test

cd ../frontend
npm test
npm run build
```

Les répertoires générés ne doivent pas être modifiés manuellement ni ajoutés au commit. Les changements fonctionnels doivent être réalisés dans `src/`, les fichiers de build ou les configurations locales prévues.

## Dépannage

### Le port 8081 est déjà utilisé

Arrêter l'autre application Spring Boot, ou changer `server.port` dans le TP concerné et l'URL d'API dans le client associé.

### Le front-end affiche une erreur réseau

Vérifier :

1. que l'API est démarrée ;
2. que l'URL et le port correspondent ;
3. que CORS autorise l'origine du front-end ;
4. que le navigateur n'affiche pas une erreur de pré-vol `OPTIONS`.

### TP3 ne démarre pas avec MySQL

Vérifier que MySQL est démarré, que `restaurant_db` existe ou que la création automatique est autorisée, puis contrôler `DB_USERNAME`, `DB_PASSWORD` et `DB_URL`.

### Les données du TP2 ont disparu

C'est le comportement attendu : le TP2 utilise une `ArrayList` en mémoire, sans base de données.

### Une dépendance front-end manque

Depuis le dossier du front-end concerné, exécuter :

```bash
npm install
```

Puis relancer le script de développement.

## Limites et évolutions

Le dépôt est avant tout pédagogique. Les évolutions naturelles seraient :

- ajouter une validation métier complète et des DTO dédiés au TP2 ;
- remplacer le stockage en mémoire du TP2 par une base de données ;
- déplacer les URLs d'API des front-ends vers des fichiers de configuration par environnement ;
- ajouter des tests d'intégration REST et des tests de composants front-end ;
- sécuriser l'API avec une authentification et une autorisation ;
- ajouter pagination, recherche et tri ;
- améliorer la confirmation des suppressions et la gestion des états de chargement ;
- documenter l'API TP2 et TP3 avec OpenAPI/Swagger ;
- mettre en place une intégration continue pour les builds, tests et lint.

Ce README décrit l'état réellement présent dans le dépôt et sert de point d'entrée pour exécuter, comprendre et faire évoluer chaque travail pratique.
