# API BoPhieu (Event Management Server)

Backend API for the Event Management System, built with Spring Boot. This system provides a comprehensive solution for managing events, attendees, polls, and real-time interactions.

## Key Features

### 🔐 Authentication & Security
- **Secure Access**: JWT-based authentication with Access Token and Refresh Token (HttpOnly Cookies).
- **User Management**: Registration, Login, Logout.
- **Password Management**: Change password, Forgot password, Reset password flows.
- **Role-based Access Control**: Fine-grained permissions for Admins, Managers, and Staff.

### 📅 Event Management
- **CRUD Operations**: Create, update, delete, and view events.
- **Banner Upload**: Support for event banner images.
- **AI Integration**: Auto-generate event descriptions using AI assistants.
- **Real-time Updates**: SSE (Server-Sent Events) for live event lists and status changes.
- **Event Joining**: Join events via unique event tokens.

### 👥 Attendant Management
- **Import/Export**: 
  - Import participants from files (Excel/CSV) with asynchronous processing.
  - Export participant lists to Excel.
- **Check-in System**: 
  - QR Code generation for events.
  - Mobile check-in via QR scan or Event Token.
  - Real-time check-in monitoring via SSE.
- **Participant Management**: Add, remove, or view participants.

### 📊 Polling & Voting
- **Polls**: Create and manage polls within events.
- **Voting**: Real-time voting on options.
- **Statistics**: View detailed poll statistics.
- **Export**: Export poll results to Excel.

### 💬 Communication
- **Chatbot Assistant**: Integrated AI chatbot to answer event-related questions.
- **Chat History**: Persisted chat history for each event context.

## Tech Stack

- **Core**: Java 17, Spring Boot 3.3.2
- **Database**: MySQL 8.0, Redis (optional, for caching/session)
- **Security**: Spring Security, JWT (jjwt 0.11.5)
- **Real-time**: Server-Sent Events (SSE)
- **Data Processing**: Apache POI (Excel), Commons CSV
- **Integration**: Cloudinary (Image storage), AI Services
- **Tools**: Maven, Docker, Lombok, MapStruct, Swagger/OpenAPI

## Getting Started

### 1. Prerequisites
- Java 17+
- Maven
- Docker (optional)
- MySQL Database

### 2. Configuration
Create a `.env` file or configure `application.properties`/`docker-compose.yml` with:

```properties
# Database
SPRING_DATASOURCE_URL=jdbc:mysql://localhost:3307/event_management
SPRING_DATASOURCE_USERNAME=root
SPRING_DATASOURCE_PASSWORD=your_password

# Security
JWT_SECRET=your_very_long_secret_key_minimum_64_bytes
APP_COOKIE_SECURE=false # Set true for HTTPS

# AI / Cloudinary (if used)
# CLOUDINARY_URL=...
# AI_API_KEY=...
```

### 3. Running the Application

**Using Maven:**
```bash
./mvnw clean install
./mvnw spring-boot:run
```

**Using Docker:**
```bash
docker-compose up -d --build
```

The API will be available at `http://localhost:8080`.

## API Documentation
Interactive API documentation via Swagger UI:
👉 `http://localhost:8080/swagger-ui/index.html`