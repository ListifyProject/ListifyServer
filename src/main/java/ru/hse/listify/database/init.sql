CREATE TABLE IF NOT EXISTS users (
    id UUID PRIMARY KEY,
    password_hash VARCHAR(255) NOT NULL,
    name VARCHAR(100) NOT NULL,
    accessible_list_ids UUID[] DEFAULT '{}'
);

CREATE TABLE IF NOT EXISTS lists (
    id UUID PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    created_by UUID NOT NULL,
    editor_ids UUID[] DEFAULT '{}',
    created_at TIMESTAMP DEFAULT NOW()
);

CREATE TABLE IF NOT EXISTS items (
    id UUID PRIMARY KEY,
    list_id UUID NOT NULL,,
    title VARCHAR(500) NOT NULL,
    is_completed BOOLEAN DEFAULT FALSE,
    created_by UUID NOT NULL,
    created_at TIMESTAMP DEFAULT NOW()
);