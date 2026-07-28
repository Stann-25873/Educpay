-- V11: receipts
CREATE TABLE IF NOT EXISTS receipts (
  id            UUID PRIMARY KEY,
  tenant_id     UUID NOT NULL,
  institution_id UUID NOT NULL,
  receipt_number VARCHAR(80) NOT NULL,
  payment_id   UUID NOT NULL,
  invoice_id   UUID,
  CONSTRAINT receipts_payment_fk FOREIGN KEY (payment_id) REFERENCES payments(id) ON DELETE RESTRICT,
  CONSTRAINT receipts_invoice_fk FOREIGN KEY (invoice_id) REFERENCES invoices(id) ON DELETE SET NULL,

  amount        NUMERIC(12,2) NOT NULL,
  currency      VARCHAR(3) NOT NULL DEFAULT 'EUR',
  issued_at     TIMESTAMPTZ NOT NULL DEFAULT now(),
  created_at    TIMESTAMPTZ NOT NULL DEFAULT now(),
  CONSTRAINT receipts_receipt_number_unique_per_tenant UNIQUE (tenant_id, receipt_number),
  CONSTRAINT receipts_amount_positive CHECK (amount > 0),
  CONSTRAINT receipts_tenant_fk FOREIGN KEY (tenant_id) REFERENCES institutions(id) ON DELETE CASCADE,
  CONSTRAINT receipts_institution_fk FOREIGN KEY (institution_id) REFERENCES institutions(id) ON DELETE CASCADE
);
CREATE INDEX IF NOT EXISTS idx_receipts_tenant_id ON receipts(tenant_id);


