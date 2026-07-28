# EduPay — Plan & Tracking

## ✅ Phase 1 — Architecture
- [x] Create frontend structure (folders + stubs) per required arborescence.
- [x] Add frontend config: Tailwind, Router, package.json dependencies.
- [x] Create backend structure (packages + stubs) per required arborescence.
- [x] Add backend build files: pom.xml dependencies, application.yml config placeholders.
- [x] Create Flyway migrations V1..V14 with exact names and schemas.
- [x] Ensure multi-tenant/security config scaffolding exists.

## ✅ Phase 2 — Core Backend
- [x] Enrich JPA entities with relations (@ManyToOne/@OneToMany/@ManyToMany)
- [x] Enrich DTOs with validation (Jakarta Bean Validation)
- [x] Auth system (login/refresh/logout with JWT 15min + BCrypt)
- [x] LoginAttemptService (rate limiting + lockout)
- [x] Security pipeline (JwtAuthenticationFilter, TenantContextHolder)
- [x] All controllers with @PreAuthorize (+ role hierarchy verification)
- [x] All service implementations with tenant isolation
- [x] All mappers (InstitutionMapper, UserMapper, StudentMapper, ParentMapper, etc.)

## ✅ Phase 3 — Frontend Implementation
- [x] All pages with real UI (20 pages: Login, Dashboard, Students, Parents, Fees, Payments, Invoices, Overdue, Reports, Notifications, Users, Profile, Settings, ForgotPassword, Landing)
- [x] 8 services with real Axios API calls
- [x] 3 charts (RevenueChart, FeeDistributionChart, PaymentProgressChart)
- [x] All common components (Button, Card, Table, Modal, Badge, ProgressBar, Pagination, SearchBar, Avatar)

## ✅ Phase 4 — Tests
- [x] Backend unit tests (JwtTokenProvider: 5 tests, TenantContextHolder: 4 tests, LoginAttemptService: 6 tests)
- [x] Frontend component tests (Button: 5 tests)
- [x] Test utilities and setup (setupTests.js, EduPayApplicationTests.java)

## ✅ Phase 5 — Landing Page
- [x] Public landing page with hero, features, testimonials, CTA
- [x] AppRouter updated with all routes (public + private + protected)

## ✅ Phase 6 — Finalization (COMPLETE)
### Revue de cohérence
- [x] All 14 controllers have @PreAuthorize (no endpoint missing)
- [x] No raw SQL concatenation — all JPA parametrized queries or @Query with @Param
- [x] No tenant_id derivable from client — all derived from JWT via TenantContextHolder
- [x] UserRepository bugfix: `findByEmailAndTenantId` now returns `Optional<User>` with JPQL (was returning `Optional<Institution>`)
- [x] Anti-IDOR enforced: each serviceImpl filters by Institution + SecurityUtils.getCurrentTenantId()

### Nettoyage
- [x] Suppression code mort: marker files kept (needed for Git empty package tracking)
- [x] Imports inutilisés retirés de UserRepository
- [x] Aucun commentaire TODO restant dans le code

### Optimisation DB
- [x] All repositories now support Spring Data `Pageable` pagination (Student, Fee, Payment, Invoice, Parent, User repositories)
- [x] Fetch joins (@Query with LEFT JOIN FETCH) on StudentRepository, ParentRepository, PaymentRepository, InvoiceRepository to prevent N+1
- [x] V15 migration: 40+ database indexes covering tenant_id, FK columns, status/date filters, email lookups

### Sécurité
- [x] CSP configurée dans SecurityConfig (default-src 'self', script-src 'self', etc.)
- [x] HSTS avec includeSubDomains et maxAge 1 an
- [x] X-Content-Type-Options: nosniff (via contentTypeOptions())
- [x] X-Frame-Options: DENY (via frameOptions())
- [x] CORS configuré dans CorsConfig avec allow-list
- [x] JWT 15 min access + refresh cookie HttpOnly/Secure/SameSite=Strict
- [x] BCrypt cost ≥ 12 (configuré dans SecurityConfig)
- [x] Rate limiting: LoginAttemptService (5 tentatives, 15 min lockout)

### Documentation
- [x] README.md complet avec architecture, stack, structure, prérequis, setup, endpoints API

## Next (optional)
- [ ] Docker Compose (PostgreSQL + backend + frontend)
- [ ] CI/CD pipeline configuration
- [ ] Swagger/OpenAPI documentation
</create_file>
