# SafetyNet Alerts

SafetyNet Alerts is a Java Spring Boot application designed to simulate an emergency alert system using data on residents, fire stations, and medical records. It exposes multiple RESTful endpoints for querying, modifying, and managing this information.

## 📚 Table of Contents

- [Features](#features)
- [Technologies](#technologies)
- [Installation](#installation)
- [Running the Application](#running-the-application)
- [Testing](#testing)
- [REST API](#rest-api)
- [Project Structure](#project-structure)

---

## ✅ Features

- Manage people, fire stations, and medical records (CRUD)
- Emergency-related endpoints for residents, children, and medical info
- Request/response logging with different log levels
- Follows MVC architecture and SOLID principles
- Code coverage with JaCoCo ≥ 80%

---

## 🛠️ Technologies

- Java 17+
- Spring Boot
- Maven
- JUnit 5
- JaCoCo
- Log4j or Tinylog
- Jackson or Gson (for JSON parsing)

---

## 🚀 Installation

1. **Clone the project**
   ```bash
   git clone https://github.com/<your-username>/safetynet-alerts.git
   cd safetynet-alerts
   ```

2. **Build the project and install dependencies**
   ```bash
   mvn clean install
   ```

---

## ▶️ Running the Application

```bash
mvn spring-boot:run
```

The server will be accessible at: `http://localhost:8080/`

---

## 🧪 Testing

Run the unit tests:
```bash
mvn test
```

Test reports:
- **JUnit report**: `target/surefire-reports/`
- **JaCoCo coverage report**: `target/site/jacoco/index.html`

---

## 🌐 REST API

### 🔎 Read-only Endpoints

| Endpoint | Description |
|----------|-------------|
| `/firestation?stationNumber=<number>` | List of people covered by a fire station with adult/child count |
| `/childAlert?address=<address>` | List of children at an address, with household members |
| `/phoneAlert?firestation=<number>` | List of phone numbers for residents served by a fire station |
| `/fire?address=<address>` | Residents at a given address, with station number and medical info |
| `/flood/stations?stations=<list>` | Households grouped by address, with residents and medical data |
| `/personInfo?lastName=<lastName>` | Full info on all residents with the given last name |
| `/communityEmail?city=<city>` | Email addresses of all residents in the city |

### 🔧 CRUD Endpoints

#### `/person`

- `POST`: Add a person
- `PUT`: Update a person
- `DELETE`: Delete a person (identified by first and last name)

#### `/firestation`

- `POST`: Add an address-to-station mapping
- `PUT`: Update a station number for an address
- `DELETE`: Remove a mapping

#### `/medicalRecord`

- `POST`: Add a medical record
- `PUT`: Update a medical record
- `DELETE`: Delete a medical record (identified by first and last name)

---

## 📁 Project Structure (MVC)

```
src/
├── main/
│   ├── java/com/safetynetalerts/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── service/
│   │   └── repository/
│   └── resources/
│       └── application.properties
├── test/
│   └── java/com/safetynetalerts/
```

---

## 🔐 Code Quality

- SOLID principles enforced:
    - Single Responsibility
    - Open/Closed
    - Liskov Substitution
    - Interface Segregation
    - Dependency Inversion
- Unit test coverage ≥ 80%
- Logging levels:
    - `INFO`: successful requests
    - `ERROR`: exceptions or failures
    - `DEBUG`: internal logic or processing steps

---

## 📫 Contact

For questions or suggestions, please contact the project maintainer or open an issue on GitHub.
