-- Users table
CREATE TABLE users (
                       id VARCHAR(36) PRIMARY KEY,
                       username VARCHAR(100) NOT NULL UNIQUE,
                       email VARCHAR(150) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       role ENUM('ADMIN', 'PI', 'MEMBER') DEFAULT 'MEMBER',
                       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Projects table
CREATE TABLE projects (
                          id VARCHAR(36) PRIMARY KEY,
                          title VARCHAR(255) NOT NULL,
                          description TEXT,
                          start_date DATE,
                          end_date DATE,
                          created_by VARCHAR(36),
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                          FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL
);

-- Milestones table
CREATE TABLE milestones (
                            id VARCHAR(36) PRIMARY KEY,
                            project_id VARCHAR(36) NOT NULL,
                            title VARCHAR(255) NOT NULL,
                            description TEXT,
                            due_date DATE,
                            is_completed BOOLEAN DEFAULT FALSE,
                            created_by VARCHAR(36),
                            created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                            FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
                            FOREIGN KEY (created_by) REFERENCES users(id) ON DELETE SET NULL
);

-- Documents table
CREATE TABLE documents (
                           id VARCHAR(36) PRIMARY KEY,
                           project_id VARCHAR(36) NOT NULL,
                           title VARCHAR(255) NOT NULL,
                           url_or_path VARCHAR(500) NOT NULL,
                           uploaded_by VARCHAR(36),
                           uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                           FOREIGN KEY (project_id) REFERENCES projects(id) ON DELETE CASCADE,
                           FOREIGN KEY (uploaded_by) REFERENCES users(id) ON DELETE SET NULL
);
