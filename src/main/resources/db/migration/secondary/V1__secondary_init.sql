-- Initial database migration for secondary database
CREATE TABLE IF NOT EXISTS secondary_example_table (
    id SERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);
