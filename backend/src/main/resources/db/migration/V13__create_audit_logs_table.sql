-- V13: audit logs
CREATE TABLE IF NOT EXISTS audit_logs (
  id            UUID PRIMARY KEY,
  tenant_id     UUID NOT NULL,
  institution_id UUID NOT NULL,
  actor_user_id UUID,
  action        VARCHAR(80) NOT NULL,
  entity_type   VARCHAR(120),
  entity_id     UUID,
  metadata      JSONB,
  created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT audit_logs_action_nonempty CHECK (char_length(action) > 0),
  CONSTRAINT audit_logs_tenant_fk FOREIGN KEY (tenant_id) REFERENCES institutions(id) ON DELETE CASCADE,
  CONSTRAINT audit_logs_institution_fk FOREIGN KEY (institution_id) REFERENCES institutions(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_audit_logs_tenant_id ON audit_logs(tenant_id);
CREATE INDEX IF NOT EXISTS idx_audit_logs_created_at ON audit_logs(created_at);


