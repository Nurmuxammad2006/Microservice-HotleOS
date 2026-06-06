# HotelOS – Real-Time Hotel Management System

## Overview

HotelOS is a microservice-based hotel management system developed using Java Spring Boot. The system simulates real hotel operations through independent services communicating via RabbitMQ events and displaying updates in real time through a WebSocket dashboard.

The project demonstrates:

* Microservice architecture
* Event-driven communication
* Real-time monitoring
* Room assignment algorithms
* Room service management
* Housekeeping management
* Maintenance request management
* Input validation and security controls

---

## Technologies Used

### Backend

* Java 17
* Spring Boot
* Spring Data JPA
* Spring Web
* Spring Validation
* Spring Security
* Spring WebSocket

### Database

* PostgreSQL

### Messaging

* RabbitMQ

### API Documentation

* Swagger / OpenAPI

### Build Tool

* Gradle

---

## System Architecture

The system consists of five microservices:

### Reception Service (Port 8081)

Responsible for:

* Guest check-in
* Guest check-out
* Room assignment
* Room availability management
* Billing calculations

### Housekeeping Service (Port 8082)

Responsible for:

* Cleaning tasks
* Room cleaning status updates
* Room vacancy notifications

### Maintenance Service (Port 8083)

Responsible for:

* Maintenance requests
* Issue tracking
* Repair completion notifications

### Room Service (Port 8084)

Responsible for:

* Food ordering
* Order preparation
* Delivery tracking
* Room service billing

### Dashboard Service (Port 8085)

Responsible for:

* Real-time event monitoring
* WebSocket updates
* Operational dashboard

---

## Security Features

### Input Validation

All external input is validated before processing.

Examples:

* Guest name cannot be blank.
* Room type must be provided.
* Number of nights must be greater than zero.

### Authentication

The dashboard requires authentication before access.

Default credentials:

Username:
admin

Password:
hotel123

### Data Protection

WebSocket events only transmit operational information such as:

* Room number
* Order status
* Maintenance status

Sensitive guest information is never transmitted through dashboard events.

### Error Handling

All application errors are handled through a global exception handler.

The system returns safe error messages and never exposes internal stack traces to users.

---

## Room Assignment Algorithm

The room assignment process follows these steps:

1. Select rooms matching the requested room type.
2. Exclude unavailable rooms.
3. Apply floor preference if specified.
4. Apply elevator or stairs preference if specified.
5. Select the room that has been clean for the longest period.
6. Assign the selected room to the guest.

This ensures fair room rotation and efficient room utilization.

---

## Database Setup

Create the following PostgreSQL databases:

* hos_reception_service
* hos_house_keeping_service
* hos_maintenance_service
* hos_room_service
* hos_dashboard_service

Execute the provided SQL scripts to create tables and seed data.

---

## Running RabbitMQ

Start RabbitMQ before launching the services.

Verify RabbitMQ is running:

```bash
sudo systemctl status rabbitmq-server
```

---

## Running the Application

Start services in the following order:

1. Reception Service
2. Housekeeping Service
3. Maintenance Service
4. Room Service
5. Dashboard Service
6. API Gateway

---

## Swagger Endpoints

Reception Service:
http://localhost:8081/swagger-ui.html

Housekeeping Service:
http://localhost:8082/swagger-ui.html

Maintenance Service:
http://localhost:8083/swagger-ui.html

Room Service:
http://localhost:8084/swagger-ui.html

---

## Dashboard

Dashboard URL:

http://localhost:8085

The dashboard displays real-time hotel events including:

* Room check-ins
* Room check-outs
* Cleaning updates
* Maintenance updates
* Room service updates

---

## Author

Nurmuxammad Gafurjanov

Pearson BTEC Level 5 – Programming Assignment

HotelOS: Real-Time Hotel Management System
