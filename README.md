A modern, scalable social media platform built with Spring Boot 3.5.0 and Java 17 LTS, featuring microservices architecture for high availability and performance.

##  Features

### Core Functionality
- **User Management**: Registration, authentication, profiles, and social connections
- **Content Creation**: Posts, media uploads, and rich content sharing
- **Real-Time Messaging**: WebSocket-based chat and direct messaging
- **Notifications**: Async notifications with email and push support
- **Analytics**: User engagement metrics and performance insights
- **Media Handling**: Image/video upload, storage, and streaming

### Technical Features
- **Microservices Architecture**: 8 independent Spring Boot services
- **Service Discovery**: Eureka-based automatic registration and discovery
- **API Gateway**: Spring Cloud Gateway with routing and rate limiting
- **Observability**: Prometheus metrics, distributed tracing, and health monitoring
- **Containerization**: Docker support for all services
- **Kubernetes Ready**: Production deployment manifests included

##  Architecture

```
┌──────────────────────────────────────────────────────────┐
│                      API Clients                           │
│              (Web, Mobile, Third-party)                    │
└──────────────────┬───────────────────────────────────────┘
                   │ HTTPS
        ┌──────────▼──────────┐
        │   API Gateway       │
        │  (Spring Cloud)     │
        │  - Routing          │
        │  - Auth Check       │
        │  - Rate Limiting    │
        └──────────┬──────────┘
                   │
        ┌──────────┴──────────┬─────────────┬─────────┐
        │                     │             │         │
   ┌────▼─────┐        ┌─────▼────┐  ┌────▼───┐  ┌──▼──┐
   │  User    │        │  Post    │  │ Media  │  │Chat │
   │ Service  │        │ Service  │  │Service │  │Srvcs│
   │(8081)    │        │(8082)    │  │(8083)  │  │(8084)│
   └────┬─────┘        └─────┬────┘  └────┬───┘  └──┬──┘
        │                    │            │        │
   ┌────▼────────────────────▼────────────▼────────▼──┐
   │    Service Discovery (Eureka Server)             │
   │                 (8761)                            │
   └───────────────────────────────────────────────────┘
```

### Services Overview

| Service | Port | Description |
|---------|------|-------------|
| **API Gateway** | 8080 | Entry point, routing, authentication |
| **Service Discovery** | 8761 | Eureka server for service registration |
| **User Service** | 8081 | User management and authentication |
| **Post Service** | 8082 | Content creation and feed management |
| **Media Service** | 8083 | File upload and media processing |
| **Chat Service** | 8084 | Real-time messaging |
| **Notification Service** | 8085 | Async notifications |
| **Analytics Service** | 8086 | Metrics and reporting |

##  Prerequisites

- **Java 17 LTS** or higher
- **Maven 3.9+**
- **Docker** (optional, for containerized deployment)
- **Kubernetes** (optional, for production deployment)

##  Installation & Setup

### 1. Clone the Repository
```bash
git clone <repository-url>
cd social-media-platform
```

### 2. Build All Services
```bash
# Full build with tests
mvn clean install

# Or skip tests for faster build
mvn clean install -DskipTests
```

### 3. Start Infrastructure Services

#### Start Service Discovery (Required First)
```bash
cd service-discovery
mvn spring-boot:run
# Eureka Dashboard: http://localhost:8761
```

#### Start API Gateway
```bash
cd ../api-gateway
mvn spring-boot:run
# Health check: http://localhost:8080/actuator/health
```

### 4. Start Individual Services
Open separate terminals for each service:

```bash
# User Service
cd user-service && mvn spring-boot:run

# Post Service
cd post-service && mvn spring-boot:run

# Media Service
cd media-service && mvn spring-boot:run

# Chat Service
cd chat-service && mvn spring-boot:run

# Notification Service
cd notification-service && mvn spring-boot:run

# Analytics Service
cd analytics-service && mvn spring-boot:run
```

### 5. Verify Installation
```bash
# Check service discovery
curl http://localhost:8761/eureka/apps

# Check gateway health
curl http://localhost:8080/actuator/health
```

##  Usage

### API Endpoints
All services are accessible through the API Gateway at `http://localhost:8080`

#### User Service
- `POST /api/users/register` - User registration
- `POST /api/users/login` - User authentication
- `GET /api/users/{id}` - Get user profile
- `PUT /api/users/{id}` - Update user profile

#### Post Service
- `POST /api/posts` - Create new post
- `GET /api/posts` - Get user feed
- `GET /api/posts/{id}` - Get specific post
- `PUT /api/posts/{id}` - Update post
- `DELETE /api/posts/{id}` - Delete post

#### Media Service
- `POST /api/media/upload` - Upload media file
- `GET /api/media/{id}` - Get media file
- `DELETE /api/media/{id}` - Delete media file

#### Chat Service (WebSocket)
- `ws://localhost:8080/chat` - WebSocket endpoint for real-time messaging

### Docker Deployment
```bash
# Build all Docker images
docker-compose up --build

# Or build individual services
cd user-service
docker build -t social-media/user-service .
```

### Kubernetes Deployment
```bash
cd kubernetes
kubectl apply -f namespace.yaml
kubectl apply -f configmaps.yaml
kubectl apply -f *-deployment.yaml
```

##  Configuration

### Application Properties
Each service has its own `application.yml` with service-specific configuration:

- **Database**: PostgreSQL connection settings
- **Eureka**: Service discovery configuration
- **Security**: JWT and authentication settings
- **Messaging**: RabbitMQ configuration
- **Monitoring**: Metrics and health check endpoints

### Environment Variables
```bash
# Database
SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/social_media
SPRING_DATASOURCE_USERNAME=postgres
SPRING_DATASOURCE_PASSWORD=password

# Eureka
EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://localhost:8761/eureka/

# JWT
JWT_SECRET=your-secret-key
JWT_EXPIRATION=86400000

# RabbitMQ
SPRING_RABBITMQ_HOST=localhost
SPRING_RABBITMQ_PORT=5672
```

##  Monitoring & Observability

### Health Checks
- `http://localhost:{port}/actuator/health` - Service health status
- `http://localhost:{port}/actuator/info` - Service information
- `http://localhost:{port}/actuator/metrics` - Application metrics

### Service Discovery Dashboard
- **Eureka Dashboard**: `http://localhost:8761`

### Metrics Collection
- **Prometheus**: Ready for metrics collection
- **Distributed Tracing**: Spring Cloud Sleuth integration

##  Testing

```bash
# Run all tests
mvn test

# Run specific service tests
cd user-service
mvn test

# Integration tests with Testcontainers
mvn verify
```

##  Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request






- Spring Boot and Spring Cloud teams
- Netflix OSS contributors
- Open source community
