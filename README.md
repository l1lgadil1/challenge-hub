# ChallengeHub - Self-Development Challenge Platform

ChallengeHub is a full-stack application that allows users to discover, participate in, and track self-development challenges to improve their skills and reach their goals.

## Project Structure

This project consists of two main components:

1. **Backend**: A Spring Boot application that provides the REST API
2. **Frontend**: A Next.js application that provides the user interface

## Technology Stack

### Backend
- Java 17
- Spring Boot 3.2.3
- Spring Security with JWT Authentication
- Spring Data JPA
- H2 Database (for development)
- OpenAPI (Swagger) for API Documentation

### Frontend
- Next.js 14 with App Router
- TypeScript
- Tailwind CSS
- shadcn/ui components
- Zustand for state management
- React Hook Form with Zod validation
- Axios for API requests

## Getting Started

### Prerequisites
- Java 17 or higher
- Node.js 18.17 or higher
- npm or yarn
- Maven

### Running the Application

You can run both the backend and frontend with a single command using the provided script:

```bash
./start.sh
```

This will start:
- The backend on http://localhost:8080/api
- The frontend on http://localhost:3000

Alternatively, you can run each component separately:

#### Backend
```bash
cd backend
mvn spring-boot:run
```

#### Frontend
```bash
cd frontend
npm install
npm run dev
```

## Default Users

For testing purposes, the backend creates two default users:

1. Admin User:
   - Email: admin@example.com
   - Password: password
   - Role: ADMIN

2. Regular User:
   - Email: user@example.com
   - Password: password
   - Role: USER

## API Documentation

Once the backend is running, you can access the Swagger UI at:

```
http://localhost:8080/api/swagger-ui.html
```

## CORS Configuration

The application is configured to handle Cross-Origin Resource Sharing (CORS) between the frontend and backend:

- The backend allows requests from http://localhost:3000 (the frontend origin)
- CORS is configured in multiple places to ensure proper handling:
  - WebConfig.java: Configures CORS at the Spring MVC level
  - SecurityConfig.java: Configures CORS at the Spring Security level
  - application.yml: Contains additional CORS configuration
  - JwtAuthenticationFilter.java: Handles CORS preflight requests

To test the CORS configuration, you can use the provided script:

```bash
./test-cors.sh
```

## Features

- User authentication and registration
- Browse and filter challenges by category and difficulty
- Start challenges and track progress
- User profile management
- Progress tracking and points system
- Admin dashboard for managing challenges

## License

This project is licensed under the MIT License. # challenge-hub
