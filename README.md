# Account Management System

## Overview

This project is an Account Management System built using Spring Boot and Spring Security. It provides a secure RESTful API that enables the management of user accounts, authentication, and authorization using JWT (JSON Web Tokens).

Key features include:
- JWT-based authentication and authorization.
- CRUD operations for managing insurance accounts.
- Integration with external authentication service.
- Logging and error handling for production-ready deployment.

## Technologies Used

- **Spring Boot** - For creating the RESTful API.
- **Spring Security** - For handling authentication and authorization.
- **JWT** - For secure token-based authentication.
- **Spring Data JPA** - For data persistence.
- **RestTemplate** - For integrating with an external authentication service.


## Features

### 1. **JWT Authentication**

The system uses JWT for authentication. The `JwtAuthenticationEntryPoint` handles unauthorized access, while `JwtAuthenticationFilter` intercepts requests to validate JWT tokens.

### 2. **Account Management**

The system supports CRUD operations on insurance accounts. You can:
- **Add** new accounts.
- **Fetch** all accounts with pagination.
- **Update** existing account details.
- **Delete** an account.

The `AccountServiceImpl` handles these operations with proper exception handling and logging.

### 3. **External Authentication Service**

The `AuthService` fetches an authorization token from an external authentication service for secure access.

## Setup Instructions

### Prerequisites
- **Java 17** or later.
- **Maven** or **Gradle** for dependency management.
- **MySQL** or any other relational database (update the `application.properties` file with the correct database details).

### Steps to Run Locally

1. **Clone the repository:**

   ```bash
   git clone https://github.com/yourusername/insurance-management.git
   cd insurance-management

2. **Configure the application properties:**
  - Update the application.properties file with your database credentials and any other necessary configuration.
    
    ```bash
    spring.application.name=Insurance-Management
    spring.datasource.url=jdbc:mysql://localhost:3306/insuranceDb
    spring.datasource.username=root
    spring.datasource.password=root
    spring.jpa.show-sql=true

    spring.jpa.hibernate.ddl-auto=update
    logging.level.org.hibernate.SQL=DEBUG
    logging.level.org.hibernate.type.descriptor.sql=TRACE

    idms.api.base-url=https://idms.dealersocket.com
    idms.api.username=testerAPI@drivesoft.tech
    idms.api.password=HeoVIaCST3st@@Main
    idms.api.layout-id=2006084
    idms.api.account-status=a
    idms.api.institution-id=107007
    idms.api.page-number=1
    
    jwt.secret=yourSecretKey
    jwt.expiration.ms=86400000

3. **Build the project:**
   - If using Maven:

     ```bash
     mvn clean install

4. **Run the application:**
   - If using Maven:
  
     ```bash
     mvn spring-boot:run

5. **Access the API:**
   - Once the application is running, you can access the API endpoints at http://localhost:8080.
  
    ***API Endpoints:***
      - POST:	[/api/accounts](http://localhost:8080/api/account/create)
         Add a new account.
         Headers : Authorization = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczNDU5MzM3MSwiZXhwIjoxNzM0NjExMzcxfQ.QrAkNbsdhZ8mUKYcQ-fS7vxvn02JdbEY-cK1Yi_O9MEvSWSFBM4KT_aOXttXPja-REUn21bT2AK06BCuL0Vsyg"
     ![Create New Accoutn](https://github.com/user-attachments/assets/bd33ca0f-67c2-4ccd-990f-906b6c3db237)

      - GET:	[/api/accounts](http://localhost:8080/api/account/fetchAll?page=0&size=10)
          Get a paginated list of accounts.
          Headers : Authorization = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczNDU5MzM3MSwiZXhwIjoxNzM0NjExMzcxfQ.QrAkNbsdhZ8mUKYcQ-fS7vxvn02JdbEY-cK1Yi_O9MEvSWSFBM4KT_aOXttXPja-REUn21bT2AK06BCuL0Vsyg"
        ![Fetch All Account Lists](https://github.com/user-attachments/assets/acdf53a6-06d3-450a-9054-807cecb5d5de)

      - GET:	[/api/authenticate](http://localhost:8080/api/account/GetAccountList?token=D3E8712A-2FE0-4581-8C98-6B4277F9638F)
         Fetch and save using authentication token (from external API).
        ![Fetch and save Account list](https://github.com/user-attachments/assets/e02be8b0-a210-4958-a34a-f58d481f4c87)

        
      - PUT:	[/api/accounts/{id}](http://localhost:8080/api/account/fetchAll?page=0&size=10)
        Update an existing account.
        Headers : Authorization = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczNDU5MzM3MSwiZXhwIjoxNzM0NjExMzcxfQ.QrAkNbsdhZ8mUKYcQ-fS7vxvn02JdbEY-cK1Yi_O9MEvSWSFBM4KT_aOXttXPja-REUn21bT2AK06BCuL0Vsyg"
        ![Update an account fiels](https://github.com/user-attachments/assets/5ba17b08-0a2c-4b58-9633-4d364f37fe97)


      - DELETE:	[/api/accounts/{id}](http://localhost:8080/api/account/891188)
        Delete an account.
        Headers : Authorization = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTczNDU5MzM3MSwiZXhwIjoxNzM0NjExMzcxfQ.QrAkNbsdhZ8mUKYcQ-fS7vxvn02JdbEY-cK1Yi_O9MEvSWSFBM4KT_aOXttXPja-REUn21bT2AK06BCuL0Vsyg"
        ![Delete an Account](https://github.com/user-attachments/assets/9ba1522c-045b-409b-861a-17908764631f)







