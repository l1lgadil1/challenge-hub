# ChallengeHub API

This is the backend API for the ChallengeHub application, a platform for creating, participating in, and tracking self-development challenges.

## Technology Stack

- Java 17
- Spring Boot 3.2.3
- Spring Security with JWT Authentication
- Spring Data JPA
- H2 Database (for development)
- OpenAPI (Swagger) for API Documentation

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven

### Running the Application

1. Clone the repository
2. Navigate to the project directory
3. Run the application using Maven:

```bash
mvn spring:boot run
```

The application will start on port 8080 with the context path `/api`.

## API Documentation

Once the application is running, you can access the Swagger UI at:

```
http://localhost:8080/api/swagger-ui.html
```

## Authentication

The API uses JWT for authentication. To access protected endpoints, you need to:

1. Register a user using the `/api/auth/register` endpoint
2. Login using the `/api/auth/login` endpoint to get a JWT token
3. Include the token in the Authorization header of your requests:

```
Authorization: Bearer your_jwt_token
```

## Default Users

For testing purposes, the application creates two default users:

1. Admin User:
   - Email: admin@example.com
   - Password: password
   - Role: ADMIN

2. Regular User:
   - Email: user@example.com
   - Password: password
   - Role: USER

## API Endpoints

### Authentication

- `POST /api/auth/register` - Register a new user
- `POST /api/auth/login` - Login and get JWT token
- `POST /api/auth/refresh` - Refresh an expired JWT token

### Challenges

- `GET /api/challenges` - Get all challenges
- `GET /api/challenges/{id}` - Get challenge by ID
- `GET /api/challenges/category/{category}` - Get challenges by category
- `POST /api/challenges` - Create a new challenge (admin only)
- `PUT /api/challenges/{id}` - Update a challenge (admin only)
- `DELETE /api/challenges/{id}` - Delete a challenge (admin only)

### User Challenges

- `GET /api/user-challenges` - Get all user challenges
- `GET /api/user-challenges/completed` - Get user challenges by completion status
- `GET /api/user-challenges/{id}` - Get user challenge by ID
- `POST /api/user-challenges` - Start a new challenge
- `PUT /api/user-challenges/{id}` - Update user challenge progress
- `DELETE /api/user-challenges/{id}` - Delete a user challenge

## Security

The application implements the following security measures:

- Password hashing using BCrypt
- JWT token-based authentication
- Role-based access control
- CSRF protection
- Input validation

## Development

### Database

The application uses an H2 in-memory database for development. You can access the H2 console at:

```
http://localhost:8080/api/h2-console
```

Connection details:
- JDBC URL: jdbc:h2:mem:challengehub
- Username: sa
- Password: password 