# MagUsers Project Overview

I’ve created a user profile management service that exposes multiple endpoints, as requested. This project illustrates how microservices can be designed to handle a specific domain (in this case, user profiles) while maintaining flexibility, scalability, and separation of concerns. It follows key principles of microservice architecture:

## Key Microservices Pillars:

- **Independence**: The service is independent, meaning it can be easily replaced or scaled horizontally by running multiple instances without affecting the overall system.
- **Scalability**: The system can be scaled in or out based on demand, allowing flexibility in handling high traffic.
- **Stateless**: The service maintains stateless interactions. Each request is independent and doesn't rely on a server maintaining previous request information.
- **Service Communication**: Services are designed to communicate asynchronously via RabbitMQ, showcasing how microservices can integrate for event-driven architectures.

Below, it's included the procedures to run the application showing these concepts in action.

## Endpoints Implemented:

The service exposes several REST API endpoints that meet the specified requirements:

1. **Get Specific User by ID**:
    - **Endpoint**: `GET localhost:8080/v1/api/users/{id}`
    - This endpoint fetches a specific user by their unique ID, including all associated information.

2. **Search Users by Date Range**:
    - **Endpoint**: `GET localhost:8080/v1/api/users/search?startDate=2025-02-01&endDate=2025-02-28`
    - This endpoint returns a list of users created between a specified date range.

3. **Search Users by Profession**:
    - **Endpoint**: `GET localhost:8080/v1/api/users/search?profession=doctor`
    - This returns a list of users with a specific profession.

4. **Custom Endpoint for CRUD Operations**:
    - `POST localhost:8080/v1/api/users` – Create a new user.
    - `PUT localhost:8080/v1/api/users` – Update an existing user.
    - `DELETE localhost:8080/v1/api/users/{id}` – Delete an existing user.

Additionally, I’ve provided a **postman_collection.json** to easily test and interact with all the endpoints.

## Additional Features Implemented:

- **Asynchronous Event Handling**:
    - When a new user is created, a message is sent to a RabbitMQ topic. Other services in the system can subscribe to this message for real-time processing. This showcases inter-service communication using event-driven architecture.
    - Technologies used: Spring Boot, RabbitMQ, Spring Cloud.

- **Data Validation**:
    - The obejcts received by the endpoints, mainly the ones for creation or deletion, uses Spring Boot validation annotations to ensure correct data formats.

- **Data Layer Separation**:
    - The code is organized into different layers: Controller, Service, and DAO layers. This improves maintainability by separating the concerns.

- **Unit Testing**:
    - Unit tests have been implemented to ensure the business logic works correctly and that the service performs as expected.

## Suggested Improvements:

- **Environment Configuration**:
    - The application currently runs in the default profile, but it would be beneficial to have separate profiles for development, testing, and production to ensure proper configuration management across different environments.

- **CSV as a Data Source**:
    - While using a CSV file as a data source is a temporary solution for this project (mainly because of the time of challenging), it’s not ideal for a production system. The lack of support for transactions (ACID properties) in CSVs could lead to data consistency issues. A relational database (e.g., PostgreSQL) or NoSQL database (e.g., MongoDB) would be more appropriate for persistent data.
    - I used OpenCSV to integrate with the CSV, and while it handles concurrency, it's not ideal for high-volume or real-time data handling.

- **Security**:
    - The current system doesn't include any security mechanisms like user authentication or authorization. This could be handled by Spring Security or delegated to another service in the system . Implementing security would be a very important next step to ensure access to sensitive information.

- **Monitoring and Observability**:
    - For a production-ready service, it's crucial to integrate monitoring and observability tools such as Prometheus, Grafana. This would help monitor the service's health, collect metrics, and provide insight into the performance of the system.

- **Fault Tolerance**:
    - While the current setup is straightforward, implementing mechanisms such as retries, circuit breakers, and failover strategies would make the service more resilient in case of failures.

## Running the Service:

To run the service using Docker Compose, follow these steps:

1. **Install Docker Desktop** (Download link).
2. Ensure **Java JDK 17 or higher** is installed.
3. **Clone the project** and navigate to the project folder.
4. Build the Docker image by running `./build-docker-image.sh`.
5. Go to the `docker-compose` folder and run `docker compose up -d` to start:
    - Three instances of the microservice.
    - One instance of NGINX to simulate an API Gateway.
    - One instance of RabbitMQ
6. You can scale the service up or down with Docker Compose using `docker compose up --scale magusers=5` or `docker compose up --scale magusers=5`.
7. To stop the services, run `docker compose down`.

### Optional Standalone Mode:
If you prefer to run the service without Docker, you can run it in standalone mode using your favorite IDE. In this case, you don't need RabbitMQ or the load balancer, but the same endpoints and Postman collection file will still work. You can also use the command `mvn spring-boot:run` to start the service locally.


## Answers to the Questions:

### **What did you think of the project?**
This project provides a great opportunity to showcase the fundamental concepts of microservices, such as scalability, statelessness, communication between services, and independent deployment. It’s a simple yet effective demonstration of how to build and manage microservices.

### **What didn’t you like about the project?**
The use of a CSV file as a data source was the most limiting aspect. While it was fine for the scope of this project, it’s not a scalable solution for production. A proper database solution would be necessary for durability, consistency, and performance.

### **How would you change the project or approach?**
The overall approach I followed seems solid for the task at hand, but I would have preferred a more detailed specification, especially for business logic. In the future, I would include a relational database or a more robust data storage solution. Additionally, I would integrate monitoring, observability, and security features. Using Spring Data JPA for database integration and adding versioning for APIs would also be improvements.

### **Anything else you would like to share?**
This project is designed to run on-premises, but in a cloud environment (e.g., AWS, GCP), many of the required components such as API Gateway, load balancing, and messaging services are readily available. If I had the opportunity, I would migrate the service to the cloud and replace certain components with native cloud services for better scalability, reliability, and cost-efficiency.