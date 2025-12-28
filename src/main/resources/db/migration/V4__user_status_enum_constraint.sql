ALTER TABLE users DROP CONSTRAINT IF EXISTS chk_users_status;

ALTER TABLE users
    ADD CONSTRAINT chk_users_status
    CHECK (status IN ('ACTIVE', 'SUSPENDED', 'DELETED'));

COMMENT ON COLUMN users.status IS 'User account status: ACTIVE, SUSPENDED, or DELETED. Maps to UserStatus enum in Java.';
