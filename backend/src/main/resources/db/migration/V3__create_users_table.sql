-- V3: users
CREATE TABLE IF NOT EXISTS users (
  id              UUID PRIMARY KEY,
  tenant_id       UUID NOT NULL,
  institution_id UUID NOT NULL,
  email           VARCHAR(320) NOT NULL,
  password_hash   VARCHAR(255) NOT NULL,
  first_name      VARCHAR(100) NOT NULL,
  last_name       VARCHAR(100) NOT NULL,
  phone           VARCHAR(30),
  is_active       BOOLEAN NOT NULL DEFAULT TRUE,
  last_login_at   TIMESTAMPTZ,
  created_at      TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT users_email_unique_per_tenant UNIQUE (tenant_id, email),
  CONSTRAINT users_tenant_fk FOREIGN KEY (tenant_id) REFERENCES institutions(id) ON DELETE CASCADE,
  CONSTRAINT users_institution_fk FOREIGN KEY (institution_id) REFERENCES institutions(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_users_tenant_id ON users(tenant_id);


