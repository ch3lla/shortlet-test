# Payment Service

A comprehensive service for managing contracts and jobs, supporting functionality such as creating, updating, and paying for jobs. This service is built using Spring Boot and integrates with various repositories and services to ensure efficient handling of profiles, contracts, and jobs.

## Features

- **Contract Management:** Create, retrieve, and manage contracts between clients and contractors.
- **Job Management:** Create, update, and pay for jobs associated with contracts.
- **Profile Handling:** Manage profiles for clients and contractors, including balance management.

## Technologies

- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **JUnit 5**
- **Mockito**

## Project Structure

- **`com.payment.service.contract`**: Main package containing all classes and interfaces.
    - **`controllers`**: REST controllers for handling HTTP requests.
    - **`models`**: Entities representing the data model.
    - **`repositories`**: Interfaces for data access and persistence.
    - **`services`**: Service layer handling business logic.
    - **`services.job`**: Contains job-related services and implementations.
    - **`services.contract`**: Contains contract-related services and implementations.
    - **`auth`**: Contains authentication-related classes (e.g., interceptors).

## Setup

### Prerequisites

- Java 17
- Maven or Gradle
- An IDE (e.g., IntelliJ IDEA, Eclipse)

### Installation

1. **Clone the repository:**

    ```bash
    git clone https://github.com/your-repo/payment-service.git
    cd payment-service
    ```

2. **Build the project:**

   For Maven:
    ```bash
    mvn clean install
    ```

   For Gradle:
    ```bash
    ./gradlew build
    ```

3. **Run the application:**

    ```bash
    mvn spring-boot:run
    ```

   or

    ```bash
    ./gradlew bootRun
    ```

## API Endpoints

### Contracts

- **GET /api/v1/contracts**

  **Description:** Retrieves a list of contracts associated with a user (either a client or contractor). The list includes only contracts that are currently active and not terminated.

  **Request Header:**
    - `profileId`: ID of the profile making the request.

  **Response:**
    - Returns a list of active contracts.

``[
    {
        "id": 1,
        "uuid": "523e4567-e89b-12d3-a456-426614174004",
        "terms": "Develop a website",
        "status": "in_progress",
        "contractor": {
            "id": 1,
            "uuid": "123e4567-e89b-12d3-a456-426614174000",
            "firstName": "John",
            "lastName": "Doe",
            "profession": "Developer",
            "balance": 1500.0,
            "role": "contractor",
            "createdAt": "2024-09-01T11:30:37.660611",
            "updatedAt": "2024-09-01T11:59:14.154087"
        },
        "client": {
            "id": 3,
            "uuid": "323e4567-e89b-12d3-a456-426614174002",
            "firstName": "Alice",
            "lastName": "Johnson",
            "profession": "Project Manager",
            "balance": 4500.0,
            "role": "client",
            "createdAt": "2024-09-01T11:30:37.660611",
            "updatedAt": "2024-09-01T11:59:14.137081"
        },
        "createdAt": "2024-09-01T11:30:37.66269",
        "updatedAt": "2024-09-01T11:30:37.66269"
    }
]``
  

- **GET /api/v1/contracts/{id}**
    
    **Description:** Retrieves contact with given id associated to the user

    **Request param**
    - `contractId`: ID of the contract 

    **Request Header:**
    - `profileId`: ID of the profile making the request.
 

**Response:**
      Retrieves specific contract.

`` {
    "id": 2,
    "uuid": "623e4567-e89b-12d3-a456-426614174005",
    "terms": "Design a logo",
    "status": "NEW",
    "contractor": {
        "id": 2,
        "uuid": "223e4567-e89b-12d3-a456-426614174001",
        "firstName": "Jane",
        "lastName": "Smith",
        "profession": "Designer",
        "balance": 1500.0,
        "role": "contractor",
        "createdAt": "2024-09-01T11:30:37.660611",
        "updatedAt": "2024-09-01T11:30:37.660611"
    },
    "client": {
        "id": 3,
        "uuid": "323e4567-e89b-12d3-a456-426614174002",
        "firstName": "Alice",
        "lastName": "Johnson",
        "profession": "Project Manager",
        "balance": 4500.0,
        "role": "client",
        "createdAt": "2024-09-01T11:30:37.660611",
        "updatedAt": "2024-09-01T11:59:14.137081"
    },
    "createdAt": "2024-09-01T11:30:37.66269",
    "updatedAt": "2024-09-01T11:30:37.66269"
}``

### Jobs

- **GET /api/v1/jobs/unpaid**

  **Description:** Retrieves all unpaid jobs for a user (either a client or contractor) for active contracts only.

  **Request Header:**
    - `profileId`: ID of the profile making the request.

  **Response:**
    - Returns a list of unpaid jobs.

``
[
    {
        "id": 1,
        "uuid": "823e4567-e89b-12d3-a456-426614174007",
        "description": "Frontend development",
        "price": 500.0,
        "isPaid": false,
        "paidDate": null,
        "contract": {
            "id": 1,
            "uuid": "523e4567-e89b-12d3-a456-426614174004",
            "terms": "Develop a website",
            "status": "in_progress",
            "contractor": {
                "id": 1,
                "uuid": "123e4567-e89b-12d3-a456-426614174000",
                "firstName": "John",
                "lastName": "Doe",
                "profession": "Developer",
                "balance": 1000.0,
                "role": "contractor",
                "createdAt": "2024-09-01T16:51:00.035972",
                "updatedAt": "2024-09-01T16:51:00.035972"
            },
            "client": {
                "id": 3,
                "uuid": "323e4567-e89b-12d3-a456-426614174002",
                "firstName": "Alice",
                "lastName": "Johnson",
                "profession": "Project Manager",
                "balance": 5000.0,
                "role": "client",
                "createdAt": "2024-09-01T16:51:00.035972",
                "updatedAt": "2024-09-01T16:51:00.035972"
            },
            "createdAt": "2024-09-01T16:51:00.043242",
            "updatedAt": "2024-09-01T16:51:00.043242"
        },
        "createdAt": "2024-09-01T16:51:00.051863",
        "updatedAt": "2024-09-01T16:51:00.051863"
    }
]
``
- **POST /api/v1/jobs/{job_id}/pay**

  **Description:** Pays for a job. A client can only proceed if their balance is greater than or equal to the payment amount. If conditions are met, the payment amount is transferred from the client's balance to the contractor's balance.

  **Request Header:**
    - `profileId`: ID of the profile making the request.

  **Request Body:**
    - The job ID to be paid.

  **Response:**
    - Returns the updated job details.

``
{
    "id": 1,
    "uuid": "823e4567-e89b-12d3-a456-426614174007",
    "description": "Frontend development",
    "price": 500.0,
    "isPaid": true,
    "paidDate": "2024-09-01",
    "contract": {
        "id": 1,
        "uuid": "523e4567-e89b-12d3-a456-426614174004",
        "terms": "Develop a website",
        "status": "in_progress",
        "contractor": {
            "id": 1,
            "uuid": "123e4567-e89b-12d3-a456-426614174000",
            "firstName": "John",
            "lastName": "Doe",
            "profession": "Developer",
            "balance": 1500.0,
            "role": "contractor",
            "createdAt": "2024-09-01T16:51:00.035972",
            "updatedAt": "2024-09-01T17:00:29.413821"
        },
        "client": {
            "id": 3,
            "uuid": "323e4567-e89b-12d3-a456-426614174002",
            "firstName": "Alice",
            "lastName": "Johnson",
            "profession": "Project Manager",
            "balance": 4500.0,
            "role": "client",
            "createdAt": "2024-09-01T16:51:00.035972",
            "updatedAt": "2024-09-01T17:00:29.324826"
        },
        "createdAt": "2024-09-01T16:51:00.043242",
        "updatedAt": "2024-09-01T16:51:00.043242"
    },
    "createdAt": "2024-09-01T16:51:00.051863",
    "updatedAt": "2024-09-01T16:51:00.051863"
}
``
### Profiles

 **Client Profile**
- **POST /api/v1/balances/deposit/{userId}**

  **Description:** Deposits money into a client's balance. The deposit amount is restricted to 25% of the client’s total outstanding payments for jobs at the time of the deposit.

  **Request Header:**
    - `profileId`: ID of the profile making the request.

  **Request Body:**
    - The deposit amount.

  **Response:**
    - Returns the updated balance.
    - 
``
    Deposit exceeds 25% of total outstanding payments
``

**Admin Profile**
- **GET /api/v1/admin/best-profession?start=<date>&end=<date>**

  **Description:** Returns the profession that earned the most money, calculated as the sum of payments for jobs, among all contractors who worked within the specified time range.

  **Query Parameters:**
    - `start`: Start date of the range.
    - `end`: End date of the range.

  **Response:**
    - Returns the top-earning profession.

`` Developer``

- **GET /api/v1/admin/best-clients?start=<date>&end=<date>&limit=<integer>**

  **Description:** Returns the clients who paid the most for jobs within the specified period. Includes a limit parameter, with a default limit set to 2.

  **Query Parameters:**
    - `start`: Start date of the period.
    - `end`: End date of the period.
    - `limit`: Number of top clients to return (default is 2).

  **Response:**
    - Returns a list of top clients.


``
{
    "content": [
        {
            "id": 3,
            "uuid": "323e4567-e89b-12d3-a456-426614174002",
            "firstName": "Alice",
            "lastName": "Johnson",
            "profession": "Project Manager",
            "balance": 4500.0,
            "role": "client",
            "createdAt": "2024-09-01T16:51:00.035972",
            "updatedAt": "2024-09-01T17:00:29.324826"
        }
    ],
    "pageable": {
        "pageNumber": 0,
        "pageSize": 2,
        "sort": {
            "empty": true,
            "sorted": false,
            "unsorted": true
        },
        "offset": 0,
        "paged": true,
        "unpaged": false
    },
    "last": true,
    "totalPages": 1,
    "totalElements": 1,
    "size": 2,
    "number": 0,
    "sort": {
        "empty": true,
        "sorted": false,
        "unsorted": true
    },
    "first": true,
    "numberOfElements": 1,
    "empty": false
}
``
## Testing

To run the tests:

```bash
cd contract
mvn test
```
