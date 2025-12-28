-- Add system_role column to users table
-- According to SRS Section 4.2: Each user has exactly 1 System Role

ALTER TABLE users
    ADD COLUMN system_role VARCHAR(20) NOT NULL DEFAULT 'USER';

-- Add constraint to ensure valid system role values
ALTER TABLE users
    ADD CONSTRAINT chk_users_system_role
    CHECK (system_role IN ('SYSTEM_ADMIN', 'CTSV_STAFF', 'TRAINING_STAFF', 'USER', 'LEADER'));

-- Add index for role-based queries
CREATE INDEX idx_users_system_role ON users (system_role);

-- Add comment for documentation
COMMENT ON COLUMN users.system_role IS 'System role: SYSTEM_ADMIN, CTSV_STAFF, TRAINING_STAFF, USER, LEADER. Maps to SystemRole enum.';
