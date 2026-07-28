-- V10: invoices
CREATE TABLE IF NOT EXISTS invoices (
  id            UUID PRIMARY KEY,
  tenant_id     UUID NOT NULL,
  institution_id UUID NOT NULL,
  invoice_number VARCHAR(80) NOT NULL,
  student_id   UUID NOT NULL,
  fee_id        UUID NOT NULL,
  CONSTRAINT invoices_student_fk FOREIGN KEY (student_id) REFERENCES students(id) ON DELETE CASCADE,
  CONSTRAINT invoices_fee_fk FOREIGN KEY (fee_id) REFERENCES fees(id) ON DELETE RESTRICT,

  issue_date    DATE NOT NULL,
  due_date      DATE NOT NULL,
  status        VARCHAR(30) NOT NULL DEFAULT 'SENT', -- DRAFT/SENT/PAID/VOID
  total_amount  NUMERIC(12,2) NOT NULL,
  paid_amount   NUMERIC(12,2) NOT NULL DEFAULT 0,
  created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT invoices_invoice_number_unique_per_tenant UNIQUE (tenant_id, invoice_number),
  CONSTRAINT invoices_total_positive CHECK (total_amount >= 0),
  CONSTRAINT invoices_paid_amount_nonneg CHECK (paid_amount >= 0),
  CONSTRAINT invoices_tenant_fk FOREIGN KEY (tenant_id) REFERENCES institutions(id) ON DELETE CASCADE,
  CONSTRAINT invoices_institution_fk FOREIGN KEY (institution_id) REFERENCES institutions(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_invoices_tenant_id ON invoices(tenant_id);


