-- Add missing database indexes for query performance
-- Covers tenant isolation lookups, foreign key queries, and status/date filters

-- Institutions
CREATE INDEX IF NOT EXISTS idx_institutions_type ON institutions(type);
CREATE INDEX IF NOT EXISTS idx_institutions_created_at ON institutions(created_at);

-- Users
CREATE INDEX IF NOT EXISTS idx_users_email ON users(email);
CREATE INDEX IF NOT EXISTS idx_users_institution_id ON users(institution_id);
CREATE INDEX IF NOT EXISTS idx_users_role_id ON users(role_id);
CREATE INDEX IF NOT EXISTS idx_users_status ON users(status);
CREATE INDEX IF NOT EXISTS idx_users_email_tenant ON users(email, institution_id);

-- Roles
CREATE INDEX IF NOT EXISTS idx_roles_name ON roles(name);

-- Students
CREATE INDEX IF NOT EXISTS idx_students_institution_id ON students(institution_id);
CREATE INDEX IF NOT EXISTS idx_students_level ON students(level);
CREATE INDEX IF NOT EXISTS idx_students_status ON students(status);
CREATE INDEX IF NOT EXISTS idx_students_external_ref ON students(external_ref);

-- Parents
CREATE INDEX IF NOT EXISTS idx_parents_institution_id ON parents(institution_id);
CREATE INDEX IF NOT EXISTS idx_parents_email ON parents(email);

-- Teachers
CREATE INDEX IF NOT EXISTS idx_teachers_institution_id ON teachers(institution_id);

-- Accountants
CREATE INDEX IF NOT EXISTS idx_accountants_institution_id ON accountants(institution_id);

-- Fees
CREATE INDEX IF NOT EXISTS idx_fees_institution_id ON fees(institution_id);
CREATE INDEX IF NOT EXISTS idx_fees_level ON fees(level);
CREATE INDEX IF NOT EXISTS idx_fees_active ON fees(is_active);

-- Payments
CREATE INDEX IF NOT EXISTS idx_payments_institution_id ON payments(institution_id);
CREATE INDEX IF NOT EXISTS idx_payments_student_id ON payments(student_id);
CREATE INDEX IF NOT EXISTS idx_payments_invoice_id ON payments(invoice_id);
CREATE INDEX IF NOT EXISTS idx_payments_reference ON payments(reference);
CREATE INDEX IF NOT EXISTS idx_payments_method ON payments(payment_method);
CREATE INDEX IF NOT EXISTS idx_payments_date ON payments(payment_date);
CREATE INDEX IF NOT EXISTS idx_payments_status ON payments(status);

-- Invoices
CREATE INDEX IF NOT EXISTS idx_invoices_institution_id ON invoices(institution_id);
CREATE INDEX IF NOT EXISTS idx_invoices_student_id ON invoices(student_id);
CREATE INDEX IF NOT EXISTS idx_invoices_status ON invoices(status);
CREATE INDEX IF NOT EXISTS idx_invoices_due_date ON invoices(due_date);
CREATE INDEX IF NOT EXISTS idx_invoices_created_at ON invoices(created_at);

-- Receipts
CREATE INDEX IF NOT EXISTS idx_receipts_institution_id ON receipts(institution_id);
CREATE INDEX IF NOT EXISTS idx_receipts_payment_id ON receipts(payment_id);

-- Notifications
CREATE INDEX IF NOT EXISTS idx_notifications_institution_id ON notifications(institution_id);
CREATE INDEX IF NOT EXISTS idx_notifications_recipient_id ON notifications(recipient_id);
CREATE INDEX IF NOT EXISTS idx_notifications_read ON notifications(is_read);
CREATE INDEX IF NOT EXISTS idx_notifications_created_at ON notifications(created_at);

-- Audit Logs
CREATE INDEX IF NOT EXISTS idx_audit_logs_institution_id ON audit_logs(institution_id);
CREATE INDEX IF NOT EXISTS idx_audit_logs_actor_id ON audit_logs(actor_id);
CREATE INDEX IF NOT EXISTS idx_audit_logs_action ON audit_logs(action);
CREATE INDEX IF NOT EXISTS idx_audit_logs_created_at ON audit_logs(created_at);

-- Junction table
CREATE INDEX IF NOT EXISTS idx_student_parent_student_id ON student_parent(student_id);
CREATE INDEX IF NOT EXISTS idx_student_parent_parent_id ON student_parent(parent_id);
</create_file>
