-- V12: notifications
CREATE TABLE IF NOT EXISTS notifications (
  id            UUID PRIMARY KEY,
  tenant_id     UUID NOT NULL,
  institution_id UUID NOT NULL,
  user_id       UUID NOT NULL,
  type          VARCHAR(50) NOT NULL,
  title         VARCHAR(200) NOT NULL,
  message       TEXT NOT NULL,
  is_read       BOOLEAN NOT NULL DEFAULT FALSE,
  created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT notifications_tenant_fk FOREIGN KEY (tenant_id) REFERENCES institutions(id) ON DELETE CASCADE,
  CONSTRAINT notifications_institution_fk FOREIGN KEY (institution_id) REFERENCES institutions(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_notifications_tenant_id ON notifications(tenant_id);


