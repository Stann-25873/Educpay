-- V5: parents
CREATE TABLE IF NOT EXISTS parents (
  id            UUID PRIMARY KEY,
  tenant_id     UUID NOT NULL,
  institution_id UUID NOT NULL,
  first_name    VARCHAR(100) NOT NULL,
  last_name     VARCHAR(100) NOT NULL,
  email         VARCHAR(320),
  phone         VARCHAR(30),
  status        VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
  created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT parents_email_unique_per_tenant UNIQUE (tenant_id, email),
  CONSTRAINT parents_tenant_fk FOREIGN KEY (tenant_id) REFERENCES institutions(id) ON DELETE CASCADE,
  CONSTRAINT parents_institution_fk FOREIGN KEY (institution_id) REFERENCES institutions(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_parents_tenant_id ON parents(tenant_id);


