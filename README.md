# SpringBoot.WizardBank

![Spring Boot](https://img.shields.io/badge/Spring_Boot-4.0.0-6DB33F?logo=springboot)
![Java](https://img.shields.io/badge/Java-25-007396?logo=openjdk)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-Database-4169E1?logo=postgresql)
![Maven](https://img.shields.io/badge/Maven-Build-C71A36?logo=apachemaven)
![Spring Security](https://img.shields.io/badge/Spring_Security-OAuth2-6DB33F?logo=springsecurity)
![License](https://img.shields.io/badge/License-MIT-yellow)

Modern banking application built with **Spring Boot 4.0.0**, **Spring Security**, **Spring Data JPA**, and **PostgreSQL**. This RESTful API provides secure user management with OAuth2 authentication and follows best practices for enterprise-grade Spring Boot applications.

- **API Base**: Configurable via `application.properties` (default: `/api/v1`)

---

## 🚀 Features

- **Spring Boot 4.0.0** with Java 25 for cutting-edge performance and features
- **Spring Security** with OAuth2 client and resource server support
- **Spring Data JPA** for seamless database operations with PostgreSQL
- **RESTful API** with configurable API prefix and versioning (`/api/v1`)
- **User Management** with status tracking (ACTIVE, INACTIVE, SUSPENDED, PENDING)
- **Spring Boot Actuator** for production-ready monitoring and health checks
- **Email Integration** via Spring Mail for notifications
- **WebSocket Support** for real-time communication capabilities
- **Docker Compose** integration for containerized development
- **Spring DevTools** for enhanced development experience with hot reload
- **Testcontainers** integration for reliable integration testing

---

## 📂 Project Structure

```
WizardBank/
├── src/
│   ├── main/
│   │   ├── java/com/wizardcloud/wizardbank/
│   │   │   ├── WizardBankApplication.java    # Main Spring Boot application
│   │   │   ├── config/
│   │   │   │   ├── WebSecurity.java          # Security configuration
│   │   │   │   ├── ApiConfig.java            # API versioning config
│   │   │   │   └── WebMvcConfig.java         # MVC configuration
│   │   │   ├── controllers/
│   │   │   │   └── UserController.java       # REST endpoints for users
│   │   │   ├── data_transfer_objects/
│   │   │   │   └── UserDTO.java              # Data transfer objects
│   │   │   ├── entities/
│   │   │   │   └── UserEntity.java           # JPA entities
│   │   │   ├── enums/
│   │   │   │   └── UserStatus.java           # User status enumeration
│   │   │   ├── repositories/
│   │   │   │   └── UserRepository.java       # Spring Data repositories
│   │   │   ├── services/
│   │   │   │   └── UserService.java          # Business logic layer
│   │   │   └── utils/                        # Utility classes
│   │   └── resources/
│   │       ├── application.properties        # App configuration
│   │       ├── static/                       # Static resources
│   │       └── templates/                    # Template files
│   └── test/                                 # Test files with Testcontainers
├── target/                                   # Compiled classes (Maven build output)
├── compose.yaml                              # Docker Compose configuration
├── pom.xml                                   # Maven dependencies & build config
└── README.md
```

---

## ⚙️ Setup

### 1. Clone the repository

```bash
git clone https://github.com/ThisIsTheWizard/SpringBoot.WizardBank.git
cd SpringBoot.WizardBank
```

### 2. Configure PostgreSQL Database

Make sure you have PostgreSQL installed and running, or use Docker Compose:

```bash
docker-compose up -d
```

### 3. Configure application properties

Update `src/main/resources/application.properties`:

```properties
# Database Configuration
spring.datasource.url=jdbc:postgresql://localhost:5432/wizardbank
spring.datasource.username=your_username
spring.datasource.password=your_password

# JPA/Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

# API Configuration
api.prefix=/api
api.version=v1

# Security
spring.security.user.name=admin
spring.security.user.password=change_me
```

### 4. Build and run the application

Using Maven wrapper:

```bash
# Clean and build
./mvnw clean install

# Run the application
./mvnw spring-boot:run
```

Or using your IDE's Spring Boot run configuration.

The API will be available at `http://localhost:8080/api/v1`

---

## 🌐 API Endpoints

### User Management

| Method | Endpoint                 | Description                     |
| ------ | ------------------------ | ------------------------------- |
| GET    | `/api/v1/users`          | Get all users                   |
| GET    | `/api/v1/users/{id}`     | Get user by ID                  |
| POST   | `/api/v1/users`          | Create new user                 |
| PUT    | `/api/v1/users/{id}`     | Update user                     |
| DELETE | `/api/v1/users/{id}`     | Delete user                     |

### Health & Monitoring (Spring Boot Actuator)

| Method | Endpoint                 | Description                     |
| ------ | ------------------------ | ------------------------------- |
| GET    | `/actuator/health`       | Application health status       |
| GET    | `/actuator/info`         | Application information         |
| GET    | `/actuator/metrics`      | Application metrics             |

---

## 📦 Maven Commands

| Command                          | Description                                          |
| -------------------------------- | ---------------------------------------------------- |
| `./mvnw clean install`           | Clean and build the project                          |
| `./mvnw spring-boot:run`         | Run the application in development mode              |
| `./mvnw test`                    | Run all tests including Testcontainers integration   |
| `./mvnw package`                 | Package the application as a JAR file                |
| `./mvnw spring-boot:build-image` | Build a Docker image using Spring Boot buildpacks    |

---

## 🐳 Docker Deployment

Run with Docker Compose:

```bash
docker-compose up -d
```

This will start the PostgreSQL database and the Spring Boot application in containers.

---

## 🔧 Technology Stack

- **Spring Boot 4.0.0** - Main framework
- **Java 25** - Programming language
- **Spring Security** - Authentication & Authorization (OAuth2)
- **Spring Data JPA** - Database operations
- **PostgreSQL** - Relational database
- **Spring Boot Actuator** - Monitoring and health checks
- **Spring Mail** - Email functionality
- **WebSocket** - Real-time communication
- **Testcontainers** - Integration testing
- **Maven** - Build tool
- **Docker Compose** - Container orchestration

---

## 📝 License

MIT © [The Wizard](https://github.com/ThisIsTheWizard)

Feel free to fork, adapt, and extend this banking application for your own use cases.

---

👋 Created by [Elias Shekh](https://portfolio.wizardcld.com)  
If you find this useful, ⭐ the repo or reach out!
