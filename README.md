# Kanban Board REST API with Spring Boot
## Overview
This project is a Kanban Board REST API built using Java Spring Boot. It provides functionalities for user authentication, project management, task creation, task assignment, progress tracking, and email notifications. The application is containerized using Docker, making it easy to deploy and run using Docker Compose.

## Features
User Authentication: Secure user registration and login using JWT (JSON Web Tokens).

Project Management: Create, update, and delete projects.

Board Management: Create and manage boards within projects.

Task Management: Create, update, delete, and move tasks across different stages (e.g., To Do, In Progress, Done).

Task Assignment: Assign tasks to users.

Email Notifications: Send email notifications for task assignments and updates.

Progress Tracking: Track the progress of tasks and teams.

## RESTful API: Fully RESTful API for easy integration with front-end applications.

## Prerequisites
Docker

Docker Compose

# Getting Started
1. Clone the Repository
bash
Copy
git clone https://github.com/farazabir/kanban.git
cd kanban-board
2. Run the Application
To run the application using Docker Compose, execute the following command:

bash
Copy
docker-compose up
This command will build the Docker images and start the containers for the Spring Boot application and the database.

3. Access the API
Once the application is running, you can access the API at:

Copy
```http
http://localhost:8080
```

## API Endpoints
## Authentication
```http
POST /api/v1/user:
```
Register a new user.

POST /api/v1/user/login: Authenticate a user and receive a JWT token.

## Projects
```http
GET /api/projects: Get all projects.
```
```http
POST /api/projects: Create a new project.
```
```http
GET /api/projects/{id}: Get a project by ID.
```
```http
PUT /api/projects/{id}: Update a project.
```
```http
DELETE /api/projects/{id}: Delete a project.
```

## Boards
GET /api/boards: Get all boards.

POST /api/boards: Create a new board.

GET /api/boards/{id}: Get a board by ID.

PUT /api/boards/{id}: Update a board.

DELETE /api/boards/{id}: Delete a board.

## Tasks
GET /api/tasks: Get all tasks.

POST /api/tasks: Create a new task.

GET /api/tasks/{id}: Get a task by ID.

PUT /api/tasks/{id}: Update a task.

DELETE /api/tasks/{id}: Delete a task.

PUT /api/tasks/{id}/move: Move a task to a different stage.

PUT /api/tasks/{id}/assign: Assign a task to a user.

## Progress Tracking
GET /api/progress: Get progress tracking data.

POST /api/progress: Create a new progress entry.

GET /api/progress/{id}: Get progress by ID.

PUT /api/progress/{id}: Update progress.

DELETE /api/progress/{id}: Delete progress.



## Configuration
## Environment Variables
The following environment variables can be configured in the docker-compose.yml file:

SPRING_DATASOURCE_URL: The URL for the database connection.

SPRING_DATASOURCE_USERNAME: The username for the database.

SPRING_DATASOURCE_PASSWORD: The password for the database.

JWT_SECRET: The secret key used for JWT token generation.

EMAIL_HOST: The SMTP host for sending emails.

EMAIL_PORT: The SMTP port for sending emails.

EMAIL_USERNAME: The username for the email account.

EMAIL_PASSWORD: The password for the email account.

## Database
The application uses a PostgreSQL database. The database is automatically initialized with the necessary schema when the application starts.
