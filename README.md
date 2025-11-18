> ⚠️ This README is currently being updated. Some sections are incomplete.

# ONLINE COURSE PLATFORM

A full-stack learning marketplace that allows users to discover online courses, enables instructors to publish and manage their content, and provides administrators the tools to oversee the entire platform. The system is built with a Spring Boot REST API and a modern front-end application, sharing a MySQL data store seeded with realistic course data.

---

## • Architecture Overview


The backend provides services such as course management, order processing, authentication & authorization, email notifications, and profile management.  
The frontend communicates with the API to render course listings, handle user interactions, and manage the learning workflow.

---

## • Key Features

## Authentication & Security

The platform implements a secure, token-based authentication flow with strict access control, role management, and protection against brute-force attacks.

- **Token generation endpoint**  
  Users authenticate through `POST /auth/token`, which issues a signed JWT access token upon valid credentials.

- **Token validation endpoint**  
  All services validate sessions via `POST /auth/introspect`, ensuring token integrity, expiration, and associated permissions.

- **OAuth2-based configuration**  
  Authentication is built on Spring Security OAuth2 components for standardized and extensible security configuration.

- **Role-based access control**  
  The system maintains three roles across all learning and administrative workflows:  
  - **USER** — browse courses, enroll, manage profile  
  - **INSTRUCTOR** — create, publish, edit courses  
  - **ADMIN** — platform-level controls, user and course administration  

- **Account lockout policy**  
  To protect against brute-force login attempts, the system enforces a tiered lockout mechanism:  
  - **5 failed attempts:** temporary lock for **5 minutes**  
  - **15 failed attempts:** **permanent lock** requiring admin intervention  
  Lockout counters automatically reset upon successful authentication.

- **Password reset with OTP verification**  
  Users can reset their password by providing a verified account email:  
  1. User submits email → system sends a one-time OTP  
  2. User confirms OTP  
  3. User sets a new password  
     
  The new password **cannot match any of the last 5 previously used passwords**, ensuring long-term credential security.

- **CORS enforcement for cross-origin security**  
  The backend applies strict, per-endpoint CORS policies to ensure that only approved client origins can interact with authentication endpoints (`/auth/*`) and protected APIs, enabling safe token exchange between frontend and backend.
## Course discovery

The platform provides a unified course-search experience through a single endpoint:

- **GET /api/courses/homepage**  
  Returns a filtered and ranked list of courses based on multiple criteria, including:
  - course category / type  
  - pricing ranges  
  - popularity (enroll count, rating, trending metrics)  
  - instructor attributes  
  - recommended courses  
  - and other dynamic filters

A structured domain model powers the search layer, including entities such as **Course**, **Category**, **Instructors**, **Course**, **Comment**, **Reviews** and **Enrollment**, each mapped via dedicated DTO transformers to ensure consistent API responses.

The frontend consumes this endpoint to render:
- homepage course sections  
- filterable course lists  
- “top” and “recommended” course groups  
- dynamic sorting and interactive browsing UI 
## User Experience

- **HTML/CSS/jQuery SPA** with dedicated sections for:
  - Home discovery
  - Property detail pages
  - Order flow
  - User profile
  - Instructor profile
  - Admin dashboard
  - ...

- **AJAX token handling**  
  All AJAX requests automatically include JWT tokens stored in `localStorage`. Failed requests are retried after token refresh to preserve session state.

- **Role-based access control**  
  Frontend prevents unauthorized users from accessing protected sections:
  - USER: browse properties, shopping,view courses, manage profile,...
  - INSTRUCTOR/OWNER: create/update course, manage instructor dashboard,...
  - ADMIN: platform-level management

- **Responsive UI**  
  Built with Bootstrap 5, Font Awesome icons, and Bootstrap components such as modals, dropdowns, and carousels for a mobile-friendly experience.
## Operations & Administration

- **Admin endpoints**  
  REST API endpoints for managing:
  - Users
  - Courses(modules,lessons,resources)
  - Instructors
  - Payment
  - Orders

- **Modular service layer**  
  The backend separates business logic from controllers and repositories:
  - `service/` for service interfaces
  - `service/Imp/` for service implementations 

---

## • Technology Stack

| Layer    | Technologies |
|----------|--------------|
| Backend  | Java 21, Spring Boot 3.5, Spring Security, Spring Data JPA, OAuth2 Client, WebFlux, MySQL, Flyway-style migrations, Lombok,Redis,Kafka,mail |
| Frontend | HTML, CSS, jQuery, AJAX, Bootstrap 5, Font Awesome |
| Tooling  | Maven Wrapper, Docker Compose, npm, pnpm (lockfile) |
## • Project Structure
## Backend
```
backend/
├── src/main/java/com/udemine/course_manage
│ ├── configuration/ # Security, CORS, email, WebClient, RestTemplate configuration
│ ├── controller/ # REST controllers (Auth, Course, User, Admin, ...)
│ ├── service/ # Service interfaces plus Imp/ implementations
│ ├── repository/ # Spring Data repositories
│ ├── dto/ & payload/ # DTOs, request/response contracts
│ ├── Exception/ # manage form API exception
│ ├── Utils/ # JWt helper
│ └── Entity/, Enum/ mapper/, specification/ # Domain model helpers
├── src/main/resources/
│ └── apllication.yml # app
├── Dockerfile/ # generation image
├── .env/ # Enviroment
└── docker-compose.yml # Build container form image

```
---

## • Getting Started
- Prerequisites  
- Backend setup  
- Frontend setup  
- Running the full-stack  

---

## • Environment Configuration  
## • Database Seed Data  
## • Testing  
## • Reference Documentation  
## • Troubleshooting  
## • Contributing
