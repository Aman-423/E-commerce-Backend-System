# Deployment

## Required Runtime

- Java 21
- PostgreSQL 16 or MySQL 8
- Maven Wrapper included

## Build

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd clean package -DskipTests
```

## Run

```powershell
java -jar target\ecommerce-backend-1.0.0.jar --spring.profiles.active=prod
```

## Docker

```powershell
copy .env.example .env
docker compose up --build
```

## Production Notes

- Use a strong `APP_JWT_SECRET`.
- Keep `APP_BOOTSTRAP_ADMIN_ENABLED=false` after the first admin account is created.
- Flyway owns schema changes. Do not use Hibernate `ddl-auto=update` in production.
- Swagger UI is disabled in the `prod` profile.
