-- Create password_reset_tokens table
-- Stores password reset tokens for forgot password functionality

CREATE TABLE password_reset_tokens (
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    user_id UUID NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    token_hash VARCHAR(255) NOT NULL UNIQUE,
    expires_at TIMESTAMPTZ NOT NULL,
    used_at TIMESTAMPTZ,
    created_at TIMESTAMPTZ NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Add check constraint to ensure expires_at is after created_at
ALTER TABLE password_reset_tokens
    ADD CONSTRAINT chk_expires_after_created
    CHECK (expires_at > created_at);

-- Add indexes for performance (matching @Index annotations in entity)
CREATE INDEX idx_prt_token_hash ON password_reset_tokens(token_hash);
CREATE INDEX idx_prt_user_id ON password_reset_tokens(user_id);
CREATE INDEX idx_password_reset_tokens_expires_at ON password_reset_tokens(expires_at);

-- Add comment for documentation
COMMENT ON TABLE password_reset_tokens IS 'Stores password reset tokens for forgot password functionality';
COMMENT ON COLUMN password_reset_tokens.token_hash IS 'SHA-256 hash of the reset token (Base64 encoded, ~44 chars)';
COMMENT ON COLUMN password_reset_tokens.used_at IS 'Timestamp when the token was used to reset password (NULL if not used yet)';
COMMENT ON COLUMN password_reset_tokens.expires_at IS 'Token expiration timestamp (typically 1 hour from creation)';
COMMENT ON CONSTRAINT chk_expires_after_created ON password_reset_tokens IS 'Ensures expires_at is always after created_at';
