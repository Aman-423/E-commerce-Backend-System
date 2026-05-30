# E-Commerce Backend API

A production-ready e-commerce backend built with Java 21, Spring Boot 3, Spring Security, JWT authentication, Spring Data JPA, Flyway, PostgreSQL, and Maven.

The project provides REST APIs for authentication, users, categories, products, reviews, cart, orders, payments, and admin dashboard operations.

## Features

- User registration, login, forgot password, and reset password APIs
- JWT-based authentication
- Role-based authorization with `ROLE_USER` and `ROLE_ADMIN`
- Product and category management
- Product search, filtering, sorting, and pagination
- Review and rating support
- Cart and checkout flow
- Order management and order status updates
- Mock payment module
- Admin dashboard API
- Global exception handling with clean API error responses
- Request validation using Jakarta Bean Validation
- Database migrations using Flyway
- Swagger/OpenAPI documentation
- Docker support with PostgreSQL

## Tech Stack

- Java 21
- Spring Boot 3.3.5
- Spring Security
- Spring Data JPA
- Hibernate
- Flyway
- PostgreSQL
- MySQL runtime support
- Maven
- Lombok
- springdoc-openapi
- Docker

## Project Structure

```text
src/main/java/com/example/ecommerce
|-- config
|-- controller
|-- dto
|-- entity
|-- enums
|-- exception
|-- mapper
|-- repository
|-- security
|-- service
|-- specification
`-- EcommerceBackendApplication.java
```

## Getting Started

### Prerequisites

Install the following before running the project:

- Java 21
- Maven, or use the included Maven Wrapper
- PostgreSQL 16, or Docker Desktop

## Environment Variables

Create a `.env` file from the example file:

```powershell
copy .env.example .env
```

For macOS/Linux:

```bash
cp .env.example .env
```

Update the values in `.env` as needed:

```env
SERVER_PORT=8080

SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/ecommerce_db
SPRING_DATASOURCE_USERNAME=ecommerce
SPRING_DATASOURCE_PASSWORD=ecommerce
SPRING_DATASOURCE_DRIVER_CLASS_NAME=org.postgresql.Driver

APP_JWT_SECRET=replace-with-a-strong-32-plus-character-secret
APP_JWT_EXPIRATION_MS=86400000
APP_CORS_ALLOWED_ORIGINS=http://localhost:3000,http://localhost:5173

APP_BOOTSTRAP_ADMIN_ENABLED=true
APP_BOOTSTRAP_ADMIN_FIRST_NAME=Admin
APP_BOOTSTRAP_ADMIN_LAST_NAME=User
APP_BOOTSTRAP_ADMIN_EMAIL=admin@ecommerce.com
APP_BOOTSTRAP_ADMIN_PASSWORD=Admin@12345
```

Do not commit your real `.env` file to GitHub.

## Run Locally

Make sure PostgreSQL is running and a database named `ecommerce_db` exists.

Windows:

```powershell
.\mvnw.cmd clean test
.\mvnw.cmd spring-boot:run
```

macOS/Linux:

```bash
./mvnw clean test
./mvnw spring-boot:run
```

The API will start at:

```text
http://localhost:8080/api/v1
```

## Run With Docker

Create the environment file first:

```powershell
copy .env.example .env
```

Start the application and PostgreSQL:

```powershell
docker compose up --build
```

Stop the containers:

```powershell
docker compose down
```

## API Documentation

After starting the application, open:

```text
Swagger UI: http://localhost:8080/api/v1/swagger-ui.html
OpenAPI JSON: http://localhost:8080/api/v1/docs/api
```

## Main API Endpoints

Base URL:

```text
/api/v1
```

### Authentication

- `POST /auth/register`
- `POST /auth/login`
- `POST /auth/forgot-password`
- `POST /auth/reset-password`

### Users

- `GET /users/me`
- `PUT /users/me`
- `GET /users/me/addresses`
- `POST /users/me/addresses`
- `PUT /users/me/addresses/{addressId}`
- `DELETE /users/me/addresses/{addressId}`

### Categories

- `GET /categories`
- `POST /categories` - Admin only
- `PUT /categories/{id}` - Admin only
- `DELETE /categories/{id}` - Admin only

### Products

- `GET /products`
- `GET /products/{id}`
- `POST /products` - Admin only
- `PUT /products/{id}` - Admin only
- `DELETE /products/{id}` - Admin only

Supported product query parameters:

```text
search, categoryId, minPrice, maxPrice, page, size, sortBy, sortDir
```

### Reviews

- `GET /reviews/product/{productId}`
- `POST /reviews/product/{productId}`

### Cart

- `GET /cart`
- `POST /cart/items`
- `PATCH /cart/items`
- `DELETE /cart/items/{productId}`
- `DELETE /cart`

### Orders

- `POST /orders/checkout`
- `GET /orders/me`
- `PATCH /orders/{orderId}/cancel`
- `PATCH /orders/{orderId}/status?status=PAID` - Admin only

### Payments

- `POST /payments/orders/{orderId}`

### Admin

- `GET /admin/dashboard` - Admin only

More endpoint details are available in [API_ENDPOINTS.md](API_ENDPOINTS.md).

## Database Migrations

Flyway migrations are stored in:

```text
src/main/resources/db/migration
```

Current migrations:

- `V1__init_schema.sql`
- `V2__add_indexes.sql`

The application validates the schema on startup using:

```properties
spring.jpa.hibernate.ddl-auto=validate
```

## Admin Bootstrap

To automatically create an admin account on startup, set:

```env
APP_BOOTSTRAP_ADMIN_ENABLED=true
```

Default admin values come from the `.env` file:

```env
APP_BOOTSTRAP_ADMIN_EMAIL=admin@ecommerce.com
APP_BOOTSTRAP_ADMIN_PASSWORD=Admin@12345
```

For production, use a strong password and disable admin bootstrap after the first admin account is created.

## Testing

Run the test suite:

Windows:

```powershell
.\mvnw.cmd test
```

macOS/Linux:

```bash
./mvnw test
```

## Security Notes

- Never commit `.env` or real secrets.
- Use a strong `APP_JWT_SECRET` in production.
- Change default database credentials before deploying.
- Disable admin bootstrap after creating the first admin user.
- Restrict CORS origins to your actual frontend domains in production.

## License

This project is available for learning and portfolio use.
