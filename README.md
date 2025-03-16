# SWIFT REST API

## Overview
This repository contains code for recruitment purposes at Remitly in Cracow. It parses SWIFT codes from CSV file, stores them in a database, and exposes a RESTful API to perform CRUD operations.

## Technologies 

- **Backend** - Java 23 with SpringBoot
- **Database** - PostgreSQL
- **Contenerization** - Docker Compose
- **Development enviroment** - Intellij IDEA, DataGrip, Postman, Swagger

## Setup

Clone repository and enter source directory.

```bash
git clone https://github.com/krudny/Remitly.git
cd Remitly/swift-api
```

Run `postgreSQL` database.

```bash
docker-compose up -d postgres
```

Run the application.

```bash
docker-compose up -d
```

Application will be ready at `localhost:8080`. 

## Tests

Run tests at source directory. 

```bash
mvnw test
```

## API Endpoints

The application provides user-friendly error feedback to ensure a smooth user experience. Additionally, it includes detailed error messages to help users understand and resolve issues quickly.

### 1. Retrieve details of a single SWIFT code
**GET**: `/v1/swift-codes/{swift-code}`
**Example usage**: `/v1/swift-codes/BPKOPLPWXXX`

### 2. Retrieve all SWIFT codes for a specific country
**GET**: `/v1/swift-codes/country/{countryISO2code}`
**Example usage**: `/v1/swift-codes/country/PL`

### 3. Add a new SWIFT code entry
**POST**: `/v1/swift-codes`

**Request Structure:**
```json
{
    "address": "string",
    "bankName": "string",
    "countryISO2": "string",
    "countryName": "string",
    "isHeadquarter": true,
    "swiftCode": "string"
}
```

### 4. Delete a SWIFT code entry
**DELETE**: `/v1/swift-codes/{swift-code}`
**Example usage**: `/v1/swift-codes/BPKOPLPWXXX`
