-- V9: payments
CREATE TABLE IF NOT EXISTS payments (
  id            UUID PRIMARY KEY,
  tenant_id     UUID NOT NULL,
  institution_id UUID NOT NULL,
  student_id   UUID NOT NULL,
  invoice_id   UUID,
  fee_id        UUID,
  CONSTRAINT payments_invoice_fk FOREIGN KEY (invoice_id) REFERENCES invoices(id) ON DELETE SET NULL,
  CONSTRAINT payments_fee_fk FOREIGN KEY (fee_id) REFERENCES fees(id) ON DELETE SET NULL,

  amount        NUMERIC(12,2) NOT NULL,
  currency      VARCHAR(3) NOT NULL DEFAULT 'EUR',
  method        VARCHAR(50) NOT NULL, -- CARD/CASH/BANK_TRANSFER
  reference     VARCHAR(120),
  paid_at       TIMESTAMPTZ NOT NULL DEFAULT now(),
  created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT payments_amount_positive CHECK (amount > 0),
  CONSTRAINT payments_tenant_fk FOREIGN KEY (tenant_id) REFERENCES institutions(id) ON DELETE CASCADE,
  CONSTRAINT payments_institution_fk FOREIGN KEY (institution_id) REFERENCES institutions(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_payments_tenant_id ON payments(tenant_id);


