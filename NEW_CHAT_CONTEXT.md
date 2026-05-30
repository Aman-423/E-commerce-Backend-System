# New Chat Context: E-Commerce Backend System

Paste this into a new chat when you want the assistant to understand this project from the start.

```text
You are helping me with my Java Spring Boot backend project named E-Commerce Backend API.

Project location on my computer:
C:\Users\akp98\OneDrive\Documents\Playground\ecommerce-backend

Project summary:
This is a production-style e-commerce backend REST API built with Java 21, Spring Boot 3.3.5, Spring Security, JWT, Spring Data JPA, Hibernate, Flyway, PostgreSQL, Maven, Lombok, springdoc-openapi, and Docker.

Main features:
- User registration and login
- Forgot password and reset password APIs
- JWT authentication
- Role-based authorization with ROLE_USER and ROLE_ADMIN
- User profile and address management
- Category management
- Product management
- Product search, filtering, sorting, and pagination
- Product reviews and ratings
- Cart management
- Checkout and order management
- Mock payment module
- Admin dashboard API
- Global exception handling
- Request validation
- Flyway database migrations
- Swagger/OpenAPI documentation
- Docker support with PostgreSQL

Main package:
com.example.ecommerce

Project structure:
- config
- controller
- dto
- entity
- enums
- exception
- mapper
- repository
- security
- service
- specification
- EcommerceBackendApplication.java

Important files:
- pom.xml
- README.md
- API_ENDPOINTS.md
- Dockerfile
- docker-compose.yml
- .dockerignore
- .env.example
- schema.sql
- src/main/resources/application.properties
- src/main/resources/application-prod.properties
- src/main/resources/db/migration/V1__init_schema.sql
- src/main/resources/db/migration/V2__add_indexes.sql

Important note about GitHub:
Do not upload real secret files such as .env.
The .env.example file is okay to upload because it is only an example.
Do not upload build output like target/.
Do not upload IDE folders like .idea/ or .vscode/.

Existing .gitignore includes:
/target/
/.idea/
/.vscode/
*.iml
*.log
.DS_Store
.env

API base path:
/api/v1

Swagger URLs after running the app:
Swagger UI: http://localhost:8080/api/v1/swagger-ui.html
OpenAPI JSON: http://localhost:8080/api/v1/docs/api

Main endpoints:
Authentication:
- POST /auth/register
- POST /auth/login
- POST /auth/forgot-password
- POST /auth/reset-password

Users:
- GET /users/me
- PUT /users/me
- GET /users/me/addresses
- POST /users/me/addresses
- PUT /users/me/addresses/{addressId}
- DELETE /users/me/addresses/{addressId}

Categories:
- GET /categories
- POST /categories - Admin only
- PUT /categories/{id} - Admin only
- DELETE /categories/{id} - Admin only

Products:
- GET /products
- GET /products/{id}
- POST /products - Admin only
- PUT /products/{id} - Admin only
- DELETE /products/{id} - Admin only

Product query parameters:
search, categoryId, minPrice, maxPrice, page, size, sortBy, sortDir

Reviews:
- GET /reviews/product/{productId}
- POST /reviews/product/{productId}

Cart:
- GET /cart
- POST /cart/items
- PATCH /cart/items
- DELETE /cart/items/{productId}
- DELETE /cart

Orders:
- POST /orders/checkout
- GET /orders/me
- PATCH /orders/{orderId}/cancel
- PATCH /orders/{orderId}/status?status=PAID - Admin only

Payments:
- POST /payments/orders/{orderId}

Admin:
- GET /admin/dashboard - Admin only

Environment variables:
- SERVER_PORT
- SPRING_DATASOURCE_URL
- SPRING_DATASOURCE_USERNAME
- SPRING_DATASOURCE_PASSWORD
- SPRING_DATASOURCE_DRIVER_CLASS_NAME
- APP_JWT_SECRET
- APP_JWT_EXPIRATION_MS
- APP_CORS_ALLOWED_ORIGINS
- APP_BOOTSTRAP_ADMIN_ENABLED
- APP_BOOTSTRAP_ADMIN_FIRST_NAME
- APP_BOOTSTRAP_ADMIN_LAST_NAME
- APP_BOOTSTRAP_ADMIN_EMAIL
- APP_BOOTSTRAP_ADMIN_PASSWORD

Run locally on Windows:
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run

Run locally on macOS/Linux:
./mvnw clean test
./mvnw spring-boot:run

Run with Docker:
copy .env.example .env
docker compose up --build

Recent work done:
- A new GitHub-ready README.md was created.
- The README includes setup instructions, features, tech stack, API endpoints, Docker commands, Swagger URLs, database migration notes, testing commands, and security notes.
- The README no longer links to DEPLOYMENT.md because I may not want to upload that file publicly.
- A LinkedIn post was drafted for sharing the project.

When helping me, use simple explanations and practical steps. If editing code, follow the existing Spring Boot layered architecture and avoid changing unrelated files.
```
