-- V1: institutions
CREATE TABLE IF NOT EXISTS institutions (
  id              UUID PRIMARY KEY,
  name            VARCHAR(200) NOT NULL,
  type            VARCHAR(50) NOT NULL,
  created_at      TIMESTAMPTZ NOT NULL DEFAULT now()
);


