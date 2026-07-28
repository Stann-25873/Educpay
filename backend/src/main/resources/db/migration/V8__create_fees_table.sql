-- V8: fees
CREATE TABLE IF NOT EXISTS fees (
  id            UUID PRIMARY KEY,
  tenant_id     UUID NOT NULL,
  institution_id UUID NOT NULL,
  code          VARCHAR(100) NOT NULL,
  title         VARCHAR(200) NOT NULL,
  description   TEXT,
  amount        NUMERIC(12,2) NOT NULL,
  currency      VARCHAR(3) NOT NULL DEFAULT 'EUR',
  billing_period VARCHAR(50) NOT NULL, -- MONTHLY/TRIMESTER/ANNUAL
  level         VARCHAR(100),
  created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT fees_code_unique_per_tenant UNIQUE (tenant_id, code),
  CONSTRAINT fees_tenant_fk FOREIGN KEY (tenant_id) REFERENCES institutions(id) ON DELETE CASCADE,
  CONSTRAINT fees_institution_fk FOREIGN KEY (institution_id) REFERENCES institutions(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_fees_tenant_id ON fees(tenant_id);


