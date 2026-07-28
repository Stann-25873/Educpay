-- V14: student_parent_link (N:N)
CREATE TABLE IF NOT EXISTS student_parent_link (
  id          UUID PRIMARY KEY,
  tenant_id   UUID NOT NULL,
  student_id  UUID NOT NULL,
  parent_id   UUID NOT NULL,
  created_at  TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT student_parent_link_unique_per_tenant UNIQUE (tenant_id, student_id, parent_id),
  CONSTRAINT student_parent_link_tenant_fk FOREIGN KEY (tenant_id) REFERENCES institutions(id) ON DELETE CASCADE,
  CONSTRAINT student_parent_link_student_fk FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
  CONSTRAINT student_parent_link_parent_fk FOREIGN KEY (parent_id) REFERENCES parents(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_student_parent_link_tenant_id ON student_parent_link(tenant_id);

