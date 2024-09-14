# Bonus Microservice for Gaming Platform

This project implements the **Bonus Microservice** for a gaming platform using **Quarkus** and **Apache Kafka**. The service listens for player login events via Kafka, calculates bonuses, and updates the player's bonus through the Login Service, ensuring efficient communication between the two services.

## Table of Contents
1. [Architecture](#architecture)
2. [Technologies Used](#technologies-used)
3. [Setup and Installation](#setup-and-installation)
4. [Running the Application](#running-the-application)
5. [Kafka Topics](#kafka-topics)
6. [Database Schema](#database-schema)
7. [Testing](#testing)
8. [Scalability](#scalability)
9. [Security Considerations](#security-considerations)

## Architecture

- **Login Service**: Emits login events to Kafka (`player-login-events`) and listens for bonus update events on `player-bonus-updates`.
- **Bonus Microservice**:
    - Subscribes to login events (`player-login-events`) to calculate bonuses.
    - Updates the Login Service by producing messages on `player-bonus-updates`.

This microservice communicates with a relational database to track player bonuses and ensures idempotency for bonus processing to avoid duplication.

## Technologies Used

- **Java 11+**
- **Quarkus** (MicroProfile Reactive Messaging)
- **Apache Kafka** (Event-driven architecture)
- **PostgreSQL** (Relational database)
- **Hibernate ORM with Panache** (ORM layer)
- **JUnit 5** and **RestAssured** (Testing)

## Setup and Installation

### Prerequisites

- **Java 11+**
- **Apache Kafka** (Running instance required)
- **PostgreSQL** (or another relational database)
- **Maven** (for building the project)


## Configure Kafka and Database
### 1. Kafka:

- Ensure a running instance of Kafka.
- Create the following topics:
```
kafka-topics.sh --create --topic player-login-events --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
kafka-topics.sh --create --topic player-bonus-updates --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1
```
### 2. Database:

- Set up PostgreSQL and create the schema (see Database Schema).
- Update src/main/resources/application.properties with your Kafka and database configuration:
properties

```
# Kafka configuration
mp.messaging.incoming.player-login-events.connector=smallrye-kafka
mp.messaging.incoming.player-login-events.topic=player-login-events
mp.messaging.outgoing.player-bonus-updates.connector=smallrye-kafka
mp.messaging.outgoing.player-bonus-updates.topic=player-bonus-updates

# Database configuration
quarkus.datasource.db-kind=postgresql
quarkus.datasource.username=your-username
quarkus.datasource.password=your-password
quarkus.datasource.jdbc.url=jdbc:postgresql://localhost:5432/bonus_db
quarkus.hibernate-orm.database.generation=update
```

## Install Dependencies
```
mvn clean install
```

## Run the Application
Start the microservice in development mode:

```
mvn quarkus:dev
```
The application will be accessible at http://localhost:8080.


## Kafka Topics
- player-login-events: Topic for receiving login events from the Login Service.
- player-bonus-updates: Topic for sending bonus update events to the Login Service.

## Database Schema
Create the player_bonus table in PostgreSQL:
```
CREATE TABLE player_bonus (
player_id UUID PRIMARY KEY,
total_bonus DECIMAL NOT NULL,
last_updated TIMESTAMP NOT NULL
);
```
- player_id: Unique identifier for the player.
- total_bonus: Cumulative bonus amount.
- last_updated: Timestamp of the last bonus update.

## Testing
The project includes unit and integration tests. To run the tests:
```
mvn test
```

## Adding Tests
- ### Unit Tests: 
    Add tests in src/test/java to validate individual components.
- ### Integration Tests: 
    Use RestAssured for testing REST endpoints and Kafka integration.

## Scalability
To scale the microservice:

- ### Horizontal Scaling: 
    Deploy multiple instances of the Bonus Microservice. Kafka and Quarkus handle message distribution.
- ### Kafka Partitioning: 
    Use multiple partitions for Kafka topics to distribute load across instances.
- ### Load Balancing: 
    Implement a load balancer to distribute traffic to multiple instances.

## Security Considerations
- ### Data Encryption: 
    Ensure TLS/SSL is used for encrypting Kafka communication.
- ### Authentication: 
    Implement authentication (e.g., OAuth2) for API endpoints and Kafka topics.
- ### Database Security: 
    Use SSL/TLS for PostgreSQL connections. Manage database credentials securely.
