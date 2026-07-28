-- V7: accountants
CREATE TABLE IF NOT EXISTS accountants (
  id            UUID PRIMARY KEY,
  tenant_id     UUID NOT NULL,
  institution_id UUID NOT NULL,
  user_id       UUID,
  created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT accountants_tenant_fk FOREIGN KEY (tenant_id) REFERENCES institutions(id) ON DELETE CASCADE,
  CONSTRAINT accountants_institution_fk FOREIGN KEY (institution_id) REFERENCES institutions(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_accountants_tenant_id ON accountants(tenant_id);


