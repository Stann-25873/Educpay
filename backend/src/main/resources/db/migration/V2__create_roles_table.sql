-- V2: roles
CREATE TABLE IF NOT EXISTS roles (
  id          UUID PRIMARY KEY,
  tenant_id   UUID NOT NULL,
  code        VARCHAR(50) NOT NULL,
  name        VARCHAR(120) NOT NULL,
  created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT roles_code_unique_per_tenant UNIQUE (tenant_id, code),
  CONSTRAINT roles_tenant_fk FOREIGN KEY (tenant_id) REFERENCES institutions(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_roles_tenant_id ON roles(tenant_id);


