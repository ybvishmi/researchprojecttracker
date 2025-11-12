Research Project Tracker - Backend

Backend for the Research Project Tracker system. Provides REST APIs to manage users, projects, milestones, and documents with JWT authentication and role-based access control.

Features

    * User registration and login with JWT

    * CRUD operations for Projects, Milestones, and Documents

    * Role-based access: ADMIN, PI, MEMBER

    * Upload and manage project documents
    

Tech Stack

    * Java 17 & Spring Boot

    * Spring Data JPA / Hibernate

    * Spring Security with JWT

    * H2 Database (default, can be switched)

    

Getting Started

    * Clone the repository

    * git clone https://github.com/yourusername/research-project-tracker-backend.git
    * cd research-project-tracker-backend


Build and run

    * mvn clean install
    * mvn spring-boot:run


Access the API

      *http://localhost:8080/api

API Endpoints


Authentication

     * POST /api/auth/signup

     * POST /api/auth/login
     

Projects

      * GET /api/projects

      * POST /api/projects

      * PUT /api/projects/{id}

      * DELETE /api/projects/{id}
      

Milestones

      * GET /api/projects/{id}/milestones

      * POST /api/projects/{id}/milestones

      * PUT /api/milestones/{id}

      * DELETE /api/milestones/{id}
      

Documents

      * GET /api/projects/{id}/documents

      * POST /api/projects/{id}/documents

      * DELETE /api/documents/{id}

Notes

All secured endpoints require a valid JWT token in the Authorization header.

Certain endpoints are restricted by user roles.
