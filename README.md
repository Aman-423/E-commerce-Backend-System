# E-Commerce Backend System

Production-grade e-commerce backend built with Java 21, Spring Boot 3, Spring Security, JWT, Spring Data JPA, Hibernate, Flyway, PostgreSQL/MySQL compatibility, and Maven.

## Highlights

- Clean layered architecture with controller, service, repository, DTO, entity, config, exception packages
- JWT authentication and role-based authorization
- Product, category, review, cart, order, payment, and admin dashboard modules
- Pagination, sorting, filtering, validation, logging, and global exception handling
- Flyway database migrations
- OpenAPI / Swagger documentation
- Dockerized deployment with PostgreSQL

## Tech Stack

- Java 21
- Spring Boot 3
- Spring Security
- JWT
- Spring Data JPA / Hibernate
- Flyway
- PostgreSQL / MySQL
- Maven
- Lombok
- springdoc-openapi

## Project Structure

```text
src/main/java/com/example/ecommerce
+-- config
+-- controller
+-- dto
+-- entity
+-- enums
+-- exception
+-- mapper
+-- repository
+-- security
+-- service
+-- specification
+-- EcommerceBackendApplication.java
```

## Run With Maven Wrapper

Windows:
```powershell
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run
```

macOS / Linux:
```bash
./mvnw clean test
./mvnw spring-boot:run
```

## Run With Docker

```powershell
copy .env.example .env
docker compose up --build
```

## Swagger

- API Docs: `http://localhost:8080/api/v1/docs/api`
- Swagger UI: `http://localhost:8080/api/v1/swagger-ui.html`

## API Inventory

See [API_ENDPOINTS.md](API_ENDPOINTS.md) and [postman/ecommerce-backend.postman_collection.json](postman/ecommerce-backend.postman_collection.json).

## Environment Variables

- `SPRING_DATASOURCE_URL`
- `SPRING_DATASOURCE_USERNAME`
- `SPRING_DATASOURCE_PASSWORD`
- `SPRING_DATASOURCE_DRIVER_CLASS_NAME`
- `APP_JWT_SECRET`
- `APP_JWT_EXPIRATION_MS`
- `APP_CORS_ALLOWED_ORIGINS`
- `APP_BOOTSTRAP_ADMIN_ENABLED`
- `APP_BOOTSTRAP_ADMIN_FIRST_NAME`
- `APP_BOOTSTRAP_ADMIN_LAST_NAME`
- `APP_BOOTSTRAP_ADMIN_EMAIL`
- `APP_BOOTSTRAP_ADMIN_PASSWORD`

## Default Roles

- `ROLE_USER`
- `ROLE_ADMIN`

## Optional Admin Bootstrap

Set `APP_BOOTSTRAP_ADMIN_ENABLED=true` to auto-create an admin account on startup. Disable it after the first admin account is created.

## Core Modules

- Authentication & User Management
- Category Management
- Product Management
- Review & Rating System
- Cart System
- Order Management
- Mock Payment Module
- Admin Dashboard APIs

## Deployment

See [DEPLOYMENT.md](DEPLOYMENT.md).
