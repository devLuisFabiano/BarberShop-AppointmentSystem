# Barbearia Presidente — API

REST API for a barbershop appointment scheduling system, built with Spring Boot. Originally developed as an academic project (Systems Analysis and Development, UMC) as a Thymeleaf server-rendered application, and refactored into a stateless REST API with JWT authentication.

## Tech Stack

- **Java / Spring Boot**
- **Spring Security + OAuth2 Resource Server** — JWT authentication (RSA-signed tokens)
- **Spring Data JPA / Hibernate**
- **H2** (development) — file/in-memory database
- **Maven**
- **Docker** (optional, for containerized runs)

## Architecture

- **Dual-ID pattern** — every entity exposes an internal `Long` primary key (never returned to clients) and an external `UUID publicId` used in API responses and path variables.
- **Entities**: `User` (auth target, holds `Role`: `CLIENT` / `BARBER` / `ADMIN`), `Client`, `Barber`, `Appointment`, `Service`, `WorkSchedule`, `TimeOff` — `Client` and `Barber` are linked to `User` via `@OneToOne`.
- **Stateless authentication** — no server-side sessions; every request is authenticated via a signed JWT sent in the `Authorization` header.
- **Structured error responses** — all errors follow a consistent JSON shape (see [Error Handling](#error-handling)).

## Authentication

Authentication is JWT-based, using an RSA key pair (private key signs, public key verifies). Tokens are short-lived (**5 minutes**) and stateless — no session state is kept server-side.

### Register

```
POST /register
```

**Request body**
```json
{
  "name": "Teste",
  "email": "teste@barbearia.com",
  "password": "barbeariaTeste1!",
  "confirmPassword": "barbeariaTeste1!"
}
```

**Response — `201 Created`**
```json
{
  "publicId": "b7d9d959-854a-4f8e-8709-75a37a5c00fe",
  "name": "Teste",
  "email": "teste@barbearia.com",
  "role": "CLIENT"
}
```

Validation includes required fields, valid email format, a minimum-strength password policy, and password/confirmation matching. Duplicate emails return `409 Conflict`.

### Login

```
POST /login
```

**Request body**
```json
{
  "email": "teste@barbearia.com",
  "password": "barbeariaTeste1!"
}
```

**Response — `200 OK`**
```json
{
  "accessToken": "<JWT>",
  "expiresIn": 300
}
```

Include the token on subsequent requests:
```
Authorization: Bearer <accessToken>
```

## Endpoints

| Method | Path | Auth | Description |
|---|---|---|---|
| `POST` | `/register` | Public | Register a new client account |
| `POST` | `/login` | Public | Authenticate and receive a JWT |
| `POST` | `/barbers` | `ADMIN` | Create a new barber account |
| `PUT` | `/barbers/{publicId}` | `BARBER` (self) or `ADMIN` | Update a barber's profile |
| `POST` | `/appointments` | `CLIENT` | Book a new appointment |
| `GET` | `/appointments/me` | `CLIENT` | List the authenticated client's appointments |
| `PUT` | `/appointments/{publicId}` | Owner (`CLIENT`) | Reschedule an appointment (service/time) |
| `PATCH` | `/appointments/{publicId}/status` | Owner / `BARBER` / `ADMIN` | Update appointment status (e.g. cancel) |

> Ownership and ADMIN-override rules are enforced in addition to role checks — e.g. a `CLIENT` may only view or modify their own appointments; a `BARBER` may only edit their own profile unless the caller is an `ADMIN`.

### Example — Create appointment

```
POST /appointments
Authorization: Bearer <accessToken>
```
```json
{
  "barberId": "a91e9c30-6b2d-4b3a-9f1e-8c2d4e5f6a7b",
  "serviceId": "f2a1c9e0-1234-4abc-9def-56789abcdef0",
  "dateTime": "2026-08-15T14:00:00"
}
```

**Response — `201 Created`**
```json
{
  "publicId": "b7e2f9a1-3c4d-4e5f-8a9b-1c2d3e4f5a6b",
  "barberName": "Carlos Silva",
  "dateTime": "2026-08-15T14:00:00",
  "status": "SCHEDULED",
  "serviceName": "Haircut"
}
```

Requested slots are validated against the barber's schedule and existing bookings; conflicts return `409 Conflict`.

## Error Handling

All errors follow a consistent structure so API consumers only need one parsing path, regardless of which rule failed.

**Validation errors (`400`)** — every failing field is returned together, not just the first one:
```json
{
  "timestamp": "2026-07-13T19:47:12.421467500Z",
  "status": 400,
  "error": "Validation Failed",
  "path": "/register",
  "fieldErrors": {
    "confirmPassword": "Passwords do not match",
    "email": "Email must be valid",
    "password": "Password must be at least 12 characters, contain an uppercase letter and a symbol",
    "name": "Name is required"
  }
}
```

**Conflict errors (`409`)** — e.g. duplicate email, unavailable appointment slot, invalid status transition:
```json
{
  "timestamp": "2026-07-13T19:50:02.001Z",
  "status": 409,
  "error": "Conflict",
  "path": "/register",
  "fieldErrors": {
    "email": "Email already registered: teste@barbearia.com"
  }
}
```

| Status | Meaning |
|---|---|
| `400` | Malformed request / failed field validation |
| `401` | Missing or invalid authentication |
| `403` | Authenticated, but not permitted to act on this resource |
| `404` | Referenced resource does not exist |
| `409` | Request conflicts with current state (duplicate, unavailable slot, invalid transition) |

## Running Locally

```bash
mvn spring-boot:run
```

By default the app runs on `http://localhost:8080` with an H2 database. Access the H2 console (dev only) at:
```
http://localhost:8080/h2-console
```

## Running with Docker

```bash
docker build -t barbearia-presidente .
docker run -p 8080:8080 barbearia-presidente
```

Or with `docker-compose` (app + PostgreSQL):
```bash
docker compose up --build
```

## Project History

This project began as a Thymeleaf server-rendered application built for a university course, and is being progressively rewritten as a standalone REST API — separating the backend from any specific frontend, adding stateless JWT authentication, and formalizing error handling and API conventions along the way.

## Status

Actively in development. Upcoming work includes barber-account creation flows, service catalog endpoints, and automated tests.
