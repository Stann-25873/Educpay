-- V6: teachers
CREATE TABLE IF NOT EXISTS teachers (
  id            UUID PRIMARY KEY,
  tenant_id     UUID NOT NULL,
  institution_id UUID NOT NULL,
  first_name    VARCHAR(100) NOT NULL,
  last_name     VARCHAR(100) NOT NULL,
  email         VARCHAR(320),
  phone         VARCHAR(30),
  status        VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
  created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT teachers_email_unique_per_tenant UNIQUE (tenant_id, email),
  CONSTRAINT teachers_tenant_fk FOREIGN KEY (tenant_id) REFERENCES institutions(id) ON DELETE CASCADE,
  CONSTRAINT teachers_institution_fk FOREIGN KEY (institution_id) REFERENCES institutions(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_teachers_tenant_id ON teachers(tenant_id);


