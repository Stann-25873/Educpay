# EduPay — SaaS Multi-Tenant School Financial Management

A production-ready SaaS platform for managing school finances and administration across multiple institutions (private/public schools, universities, training centers).

## Architecture

```
┌─────────────────────┐     ┌──────────────────────┐     ┌─────────────────┐
│  Frontend (React 18)│     │  Backend (Spring Boot)│     │  PostgreSQL     │
│  ─────────────────  │     │  ───────────────────  │     │  ───────────    │
│  React Router v6    │◄───►│  REST Controllers     │◄───►│  edupay_saas_db │
│  Context API        │     │  Service Layer        │     │  Multi-tenant   │
│  Axios HTTP Client  │     │  JPA Repositories     │     │  Flyway migr.   │
│  Tailwind CSS       │     │  Spring Security JWT  │     │  15 migrations  │
│  Recharts Charts    │     │  BCrypt Password Hash │     │  + indexes      │
│  Framer Motion      │     │  Bean Validation      │     └─────────────────┘
└─────────────────────┘     └──────────────────────┘
```

## Tech Stack

### Frontend
- **React 18** with React Router v6
- **Context API** for auth, tenant, notifications state
- **Axios** HTTP client with interceptors (JWT auto-refresh)
- **Tailwind CSS** for UI (Indigo primary, Green accent, Red danger)
- **React Hook Form** + **Zod** for form validation
- **Recharts** for dashboards and analytics
- **Framer Motion** for animations
- **React Icons** (Heroicons)

### Backend
- **Java 21** + **Spring Boot 3.3.2**
- **Spring Security** with JWT (access 15 min + refresh cookie HttpOnly)
- **Spring Data JPA** with Hibernate (parametrized queries only)
- **PostgreSQL** with strict tenant isolation
- **Flyway** for database migrations
- **Bean Validation** (Jakarta) server-side validation
- **BCrypt** password hashing (cost ≥ 12)
- **Rate limiting** with account lockout after 5 failed attempts

### Security
- JWT access token (15 min) + refresh token (30 days) in HttpOnly/Secure/SameSite=Strict cookie
- `@PreAuthorize` on every endpoint with tenant ownership verification (anti-IDOR)
- CSP, HSTS, X-Content-Type-Options nosniff, X-Frame-Options DENY, CORS allow-list
- Multi-tenant: `tenant_id` derived from JWT, never from client

## Project Structure

```
EducPay/
├── frontend/                    # React SPA
│   └── src/
│       ├── components/          # Reusable UI components
│       │   ├── common/          # Button, Card, Table, Modal, Badge, etc.
│       │   ├── layout/          # Sidebar, Topbar, DashboardLayout, AuthLayout
│       │   ├── charts/          # RevenueChart, FeeDistributionChart, etc.
│       │   └── forms/           # StudentForm, ParentForm, UserForm, etc.
│       ├── pages/               # Page components (auth, dashboard, CRUD)
│       ├── contexts/            # AuthContext, TenantContext, NotificationContext
│       ├── hooks/               # useAuth, useTenant, usePagination, useNotifications
│       ├── services/            # Axios API services
│       ├── routes/              # AppRouter, PrivateRoute, RoleBasedRoute
│       └── utils/               # formatters, validators, constants
├── backend/                     # Spring Boot application
│   └── src/main/java/com/edupay/
│       ├── controller/          # REST controllers (14 total)
│       ├── service/             # Service interfaces
│       ├── serviceImpl/         # Service implementations
│       ├── repository/          # JPA repositories
│       ├── entity/              # JPA entities (13 entities)
│       ├── dto/request/         # Request DTOs
│       ├── dto/response/        # Response DTOs
│       ├── mapper/              # Entity-to-DTO mappers
│       ├── config/              # Security, JWT, CORS, Swagger, Persistence config
│       ├── security/            # JWT provider, filter, auth service
│       ├── exception/           # Global exception handler + custom exceptions
│       ├── validation/          # Custom validators
│       └── utils/               # DateUtils, FileUtils
└── backend/src/main/resources/db/migration/  # Flyway SQL migrations (V1-V15)
```

## Prerequisites

- Java 21+
- Node.js 18+
- PostgreSQL 15+
- Maven 3.9+

## Setup Instructions

### 1. Database

```bash
createdb edupay_saas_db
```

### 2. Backend

```bash
cd backend

# Configure environment variables (create .env or export):
export EDUPAY_DB_URL=jdbc:postgresql://localhost:5432/edupay_saas_db
export EDUPAY_DB_USERNAME=postgres
export EDUPAY_DB_PASSWORD=your_password
export EDUPAY_JWT_SECRET_BASE64=$(echo -n "your-256-bit-secret-key" | base64)
export EDUPAY_CORS_ALLOWED_ORIGINS=http://localhost:3000

# Build (without running tests):
mvn clean package -DskipTests
```

### 3. Frontend

```bash
cd frontend
npm install
```

### 4. Run

```bash
# Terminal 1 - Backend
cd backend && mvn spring-boot:run

# Terminal 2 - Frontend
cd frontend && npm start
```

The backend runs on http://localhost:8080 and frontend on http://localhost:3000.

## API Endpoints

| Method | Path | Description | Auth |
|--------|------|-------------|------|
| POST | /api/auth/login | Login | Public |
| POST | /api/auth/refresh | Refresh token | Public |
| POST | /api/auth/logout | Logout | Authenticated |
| GET | /api/auth/me | Current user | Authenticated |
| POST | /api/institutions | Create institution | Public |
| GET/PUT | /api/institutions/{id} | Get/Update institution | ADMIN |
| POST | /api/users | Create user | SUPER_ADMIN/SCHOOL_ADMIN |
| GET | /api/users | List users | SUPER_ADMIN/SCHOOL_ADMIN/ACCOUNTANT |
| GET/PUT/DELETE | /api/users/{id} | Get/Update/Delete user | Varies |
| POST | /api/students | Create student | ADMIN/ACCOUNTANT |
| GET | /api/students | List students | Authenticated |
| GET/PUT/DELETE | /api/students/{id} | Get/Update/Delete student | Varies |
| POST | /api/parents | Create parent | ADMIN/ACCOUNTANT |
| GET | /api/parents | List parents | Authenticated |
| POST | /api/teachers | Create teacher | ADMIN |
| GET | /api/teachers | List teachers | Authenticated |
| POST | /api/accountants | Create accountant | ADMIN |
| GET | /api/accountants | List accountants | Authenticated |
| POST | /api/fees | Create fee | ADMIN/ACCOUNTANT |
| GET | /api/fees | List fees | Authenticated |
| POST | /api/payments | Create payment | ADMIN/ACCOUNTANT |
| GET | /api/payments | List payments | Authenticated |
| GET | /api/invoices | List invoices | Authenticated |
| GET | /api/invoices/by-status/{status} | Filter by status | Authenticated |
| GET | /api/receipts | List receipts | Authenticated |
| GET | /api/reports/revenue-summary | Revenue report | ADMIN/ACCOUNTANT |
| GET | /api/notifications | User notifications | Authenticated |
| GET | /api/audit-logs | Audit logs | ADMIN |

## Database Migrations

15 Flyway migrations (V1-V15) covering all entities, constraints, foreign keys, indexes.

**Entities**: Institution → Roles → Users → Students → Parents → Teachers → Accountants → Fees → Payments → Invoices → Receipts → Notifications → AuditLogs → Student-Parent link → Indexes

## License

Proprietary commercial software.