-- V4: students
CREATE TABLE IF NOT EXISTS students (
  id            UUID PRIMARY KEY,
  tenant_id     UUID NOT NULL,
  institution_id UUID NOT NULL,
  first_name    VARCHAR(100) NOT NULL,
  last_name     VARCHAR(100) NOT NULL,
  external_ref  VARCHAR(100),
  level         VARCHAR(100),
  status        VARCHAR(50) NOT NULL DEFAULT 'ACTIVE',
  created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT students_external_ref_unique_per_tenant UNIQUE (tenant_id, external_ref),
  CONSTRAINT students_tenant_fk FOREIGN KEY (tenant_id) REFERENCES institutions(id) ON DELETE CASCADE,
  CONSTRAINT students_institution_fk FOREIGN KEY (institution_id) REFERENCES institutions(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_students_tenant_id ON students(tenant_id);


