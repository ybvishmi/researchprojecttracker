Research Project Tracker - Backend

This is the backend for the Research Project Tracker system. It provides REST APIs to manage users, projects, milestones, and documents with JWT-based authentication and role-based access control.

Features

User registration and login with JWT

CRUD operations for projects, milestones, and documents

Role-based access: ADMIN, PI, MEMBER

Upload and manage project documents

Tech Stack

Java 17, Spring Boot

Spring Data JPA / Hibernate

Spring Security with JWT

MySQL Database (default)

Getting Started

Clone the repository:

git clone https://github.com/yourusername/research-project-tracker-backend.git
cd research-project-tracker-backend


Build and run:

mvn clean install
mvn spring-boot:run


Access the API at: http://localhost:8080/api

API Endpoints

Auth: /api/auth/signup, /api/auth/login

Projects: /api/projects

Milestones: /api/projects/{id}/milestones

Documents: /api/projects/{id}/documents

Notes

Use JWT token for secured endpoints.

Role-based restrictions apply on certain actions.
