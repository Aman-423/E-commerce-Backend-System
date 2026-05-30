# REST API Endpoint List

Base URL: `/api/v1`

## Authentication

- `POST /auth/register`
- `POST /auth/login`
- `POST /auth/forgot-password`
- `POST /auth/reset-password`

## User Management

- `GET /users/me`
- `PUT /users/me`
- `GET /users/me/addresses`
- `POST /users/me/addresses`
- `PUT /users/me/addresses/{addressId}`
- `DELETE /users/me/addresses/{addressId}`

## Categories

- `GET /categories`
- `POST /categories` `ADMIN`
- `PUT /categories/{id}` `ADMIN`
- `DELETE /categories/{id}` `ADMIN`

## Products

- `GET /products?search=&categoryId=&minPrice=&maxPrice=&page=&size=&sortBy=&sortDir=`
- `GET /products/{id}`
- `POST /products` `ADMIN`
- `PUT /products/{id}` `ADMIN`
- `DELETE /products/{id}` `ADMIN`

## Reviews

- `GET /reviews/product/{productId}`
- `POST /reviews/product/{productId}`

## Cart

- `GET /cart`
- `POST /cart/items`
- `PATCH /cart/items`
- `DELETE /cart/items/{productId}`
- `DELETE /cart`

## Orders

- `POST /orders/checkout`
- `GET /orders/me`
- `PATCH /orders/{orderId}/cancel`
- `PATCH /orders/{orderId}/status?status=PAID` `ADMIN`

## Payments

- `POST /payments/orders/{orderId}`

## Admin

- `GET /admin/dashboard` `ADMIN`
