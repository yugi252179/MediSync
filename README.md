# MediSync - Healthcare & Hospital Management API

![Java](https://img.shields.io/badge/Java-21-orange.svg)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-brightgreen.svg)
![Spring Data JPA](https://img.shields.io/badge/Spring%20Data-JPA-blue.svg)


**MediSync** is a robust, production-ready Spring Boot backend API designed for hospital and clinical workflow automation. It streamlines patient reception, appointment scheduling, doctor queue management, prescription processing, and medical history tracking.

---

## 🚀 Features

- **Reception Desk Operations**:
  - Register new patients or auto-create profiles by mobile number.
  - Schedule appointments for patients with specified doctors and reasons.
  - View all appointments filtered by status (`WAITING`, `IN_PROGRESS`, `COMPLETED`, `CANCELLED`).
  - Cancel appointments when needed.

- **Doctor Operations & Queue Management**:
  - Doctor registration and profile management.
  - Real-time patient queue inspection (system-wide or filtered per doctor).
  - Issue prescriptions with diagnosis and prescribed medication.
  - Automatic transition of appointment status from `WAITING` to `COMPLETED` upon issuing a prescription.

- **Patient Portal & Records**:
  - Search patients by ID or mobile number.
  - Retrieve patient appointment history.
  - Access patient prescription history.

- **Enterprise & Resilient Design**:
  - Global REST Exception Handling (`GlobalExceptionHandler`) providing structured JSON error responses.
  - CORS enabled across all controllers.
  - Dual database support: In-memory **H2 Database** for zero-configuration local dev/testing & **MySQL** for production.

---

## 🛠️ Technology Stack

- **Language**: Java 21
- **Framework**: Spring Boot 3.4.2 (Spring Web, Spring Data JPA, Spring Validation)
- **Database**: H2 (Development & Testing) / MySQL (Production ready)
- **Build Tool**: Apache Maven (`mvnw` wrapper included)
- **Testing**: JUnit 5, Spring Boot Test

---

## 📂 Project Structure

```text
src/
├── main/
│   ├── java/com/yugi/medicare/
│   │   ├── MedicareApplication.java          # Main Application Entry Point
│   │   ├── controller/                        # REST Controllers
│   │   │   ├── DoctorController.java
│   │   │   ├── PatientController.java
│   │   │   ├── PrescriptionController.java
│   │   │   └── ReceptionController.java
│   │   ├── dto/                               # Data Transfer Objects
│   │   │   └── PrescriptionRequest.java
│   │   ├── entity/                            # JPA Entities & Enums
│   │   │   ├── Appointment.java
│   │   │   ├── AppointmentStatus.java
│   │   │   ├── Doctor.java
│   │   │   ├── Patient.java
│   │   │   └── Prescription.java
│   │   ├── exception/                         # Global Exception Handler
│   │   │   ├── GlobalExceptionHandler.java
│   │   │   └── ResourceNotFoundException.java
│   │   ├── repository/                        # Spring Data JPA Repositories
│   │   │   ├── AppointmentRepository.java
│   │   │   ├── DoctorRepository.java
│   │   │   ├── PatientRepository.java
│   │   │   └── PrescriptionRepository.java
│   │   └── service/                           # Business Logic Layer
│   │       ├── AppointmentService.java
│   │       ├── DoctorService.java
│   │       ├── PatientService.java
│   │       └── PrescriptionService.java
│   └── resources/
│       └── application.properties             # Application Configuration
└── test/
    └── java/com/yugi/medicare/
        └── MedicareApplicationTests.java      # Integration Tests
```

---

## 📡 API Endpoints Documentation

### 1. Reception Desk (`/reception`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/reception/appointment` | Book a new patient appointment |
| `GET` | `/reception/appointments` | List appointments (optional `?status=WAITING`) |
| `POST` | `/reception/patient` | Directly register a new patient |
| `GET` | `/reception/patients` | List all registered patients |
| `GET` | `/reception/doctors` | List all registered doctors |
| `PUT` | `/reception/appointment/{id}/cancel` | Cancel an appointment |

### 2. Doctor Portal (`/Doctor`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `POST` | `/Doctor/new_doctor` | Register a new doctor |
| `GET` | `/Doctor/all` | Get list of all doctors |
| `GET` | `/Doctor/{id}` | Get doctor details by ID |
| `GET` | `/Doctor/Queue` | View system-wide waiting patient queue |
| `GET` | `/Doctor/{doctorId}/queue` | View waiting patient queue for a specific doctor |
| `POST` | `/Doctor/prescription` | Issue prescription & mark appointment as `COMPLETED` |
| `PUT` | `/Doctor/appointment/{id}/status` | Update appointment status (`IN_PROGRESS`, etc.) |

### 3. Patient Records (`/patient`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/patient/{id}` | Get patient profile by ID |
| `GET` | `/patient/mobile/{mobile}` | Get patient profile by mobile number |
| `GET` | `/patient/{id}/appointments` | Get patient's appointment history |
| `GET` | `/patient/{id}/prescriptions` | Get patient's prescription history |

### 4. Prescriptions (`/prescription`)

| Method | Endpoint | Description |
| :--- | :--- | :--- |
| `GET` | `/prescription/{id}` | Get prescription details by ID |
| `GET` | `/prescription/appointment/{appointmentId}` | Get prescription by appointment ID |

---

## 💻 Getting Started & Installation

### Prerequisites
- **Java Development Kit (JDK)**: 21 or higher
- **Git**

### 1. Clone the Repository
```bash
git clone https://github.com/yugi252179/MediSync.git
cd MediSync
```

### 2. Build the Project
```bash
./mvnw clean compile
```

### 3. Run Integration Tests
```bash
./mvnw clean test
```

### 4. Run the Application
```bash
./mvnw spring-boot:run
```
The server will start on `http://localhost:8080`.

---

## 🗄️ Database Access & H2 Console

By default, the application runs using an **in-memory H2 database**.
- **H2 Console URL**: `http://localhost:8080/h2-console`
- **JDBC URL**: `jdbc:h2:mem:medicaredb`
- **Username**: `sa`
- **Password**: *(leave blank)*

### Switching to MySQL
To use MySQL, open `src/main/resources/application.properties` and uncomment the MySQL properties:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/medicare
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.username=your_username
spring.datasource.password=your_password
```

---

## 📝 License
This project is open-source and available under the [MIT License](LICENSE).
