# RestoPilot

Application de gestion de restaurant avec une API Spring Boot et une interface Angular.

## Prérequis

- Java 17+
- Maven 3.9+
- Node.js 20.19+ et npm
- MySQL 8+

## Démarrer l'API

1. Créer une base MySQL (ou laisser `createDatabaseIfNotExist=true` la créer) :

```sql
CREATE DATABASE restaurant_db;
```

2. Adapter les identifiants avec les variables `DB_USERNAME` et `DB_PASSWORD` si nécessaire.
3. Depuis `backend/` :

```bash
mvn spring-boot:run
```

Pour un usage local sans variables d'environnement, créez `backend/application-local.properties`
(ce fichier est ignoré par Git) :

```properties
spring.datasource.username=root
spring.datasource.password=VotreMotDePasseMySQL
```

En lançant Maven depuis `backend/`, Spring charge automatiquement ce fichier :

```bash
mvn spring-boot:run
```

L'API est disponible sur `http://localhost:8081` :

- `GET|POST|PUT|DELETE /api/menu-items`
- `GET|POST|PUT|DELETE /api/employees`
- `GET|POST|PUT|DELETE /api/orders`

Une commande utilise le payload suivant :

```json
{
  "status": "PENDING",
  "employeeId": 1,
  "items": [{ "menuItemId": 1, "quantity": 2 }]
}
```

## Démarrer Angular

Depuis `frontend/` :

```bash
npm install
npm run dev
```

Ouvrir `http://localhost:4200`. L'URL de l'API est centralisée dans `src/app/api.service.ts`.

Le script `npm start` reste également disponible comme alias de lancement.
