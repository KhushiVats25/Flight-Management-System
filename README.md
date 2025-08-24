# ✈️ Flight Management System

<div align="center">

[![GitHub forks](https://img.shields.io/github/forks/KhushiVats25/Flight-Management-System?style=for-the-badge)](https://github.com/KhushiVats25/Flight-Management-System/network)
[![GitHub stars](https://img.shields.io/github/stars/KhushiVats25/Flight-Management-System?style=for-the-badge)](https://github.com/KhushiVats25/Flight-Management-System/stargazers)
[![GitHub issues](https://img.shields.io/github/issues/KhushiVats25/Flight-Management-System?style=for-the-badge)](https://github.com/KhushiVats25/Flight-Management-System/issues)
[![GitHub language](https://img.shields.io/github/languages/top/KhushiVats25/Flight-Management-System?style=for-the-badge)](https://github.com/KhushiVats25/Flight-Management-System)

</div>

## 📖 Overview

A Java-based backend application for managing flight operations. It provides RESTful APIs to handle flight scheduling, passenger bookings, and administrative tasks. Built using Spring Boot and Maven, it’s designed for scalability and modularity.

---

## ✨ Features

- ✈️ Add, update, delete flights
- 🧑‍✈️ Manage passengers and bookings
- 📅 View flight schedules
- 📊 Generate flight reports
- 🔍 Search flights by route/date

---

## 🛠️ Tech Stack

- **Language:** Java
- **Framework:** Spring Boot
- **Build Tool:** Maven
- **Database:** H2 (in-memory) or MySQL (configurable)
- **API Style:** RESTful

---

## 🚀 Quick Start

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Apache Maven

### Installation

```bash
git clone https://github.com/KhushiVats25/Flight-Management-System.git
cd Flight-Management-System
./mvnw clean install

```
### 📡 REST API Documentation

### ✈️ Flights
Get All Flights - GET /api/aircrafts
Get Flight by ID - GET /api/aircrafts/id
Create a Flight - POST /api/aircrafts
Update a Flight - PUT /api/aircrafts/id
Delete a Flight - DELETE /api/aircrafts/id


### ✈️ Airlines
Get All Airlines - GET /api/airlines
Get Airline by ID - GET /api/airlines/id
Register an Airline - POST /api/airlines
Update an Airline - PUT /api/airlines/id
Delete an Airline - DELETE /api/airlines/id

### 👤 Passengers

Get All Passengers - GET /api/passengers
Get Passenger by ID - GET /api/passenger/id

###  📦 Bookings
Get All Bookings - GET /api/bookings
Get Booking by ID - GET /api/bookings/id
Create booking - POST /api/bookings
Update booking - PUT /api/bookings/id
Delete a booking - DELETE /api/bookings/id


### 👤 Users
Get All Users - GET /api/users
Get User by ID - GET /api/users/id
Create a User - POST /api/users
Update a User - PUT /api/users/id
Delete a User - DELETE /api/users/id

### 🗺️ Routes
Get All Routes - GET /api/routes
Get Route by ID - GET /api/routes/id
Create a Route - POST /api/routes
Update a Route - PUT /api/routes/id
Delete a Route - DELETE /api/routes/id

### 🛡️ Roles

Get All Roles - GET /api/roles
Get Role by ID - GET /api/roles/id
Update a Role - PUT /api/roles/id
Delete a Role - DELETE /api/roles/id

### 💳 Payments

Get All Payments - GET /api/payments
Get Payment by ID - GET /api/payments/id
Get Payment by status - GET /api/payments/status

### 📝 Feedback
Get Feedback by ID - GET /api/feedback/id
Submit Feedback - POST /api/feedback
Delete Feedback - DELETE /api/feedback/id
 

##  ⚙️ Configuration

Update application.properties

spring.datasource.url=jdbc:mysql://localhost:5432/flightdb
spring.datasource.username=root
spring.datasource.password=yourpassword

## 📁 Project Structure

Flight-Management-System/
├── src/
│   ├── main/
│   │   └── java/
│   │           ├── controller/         
│   │           ├── service/            
│   │           ├── repository/       
│   │           ├── entities/              
│   │           ├── dto/               
│   │           ├── enums/              
│   │           ├── mapper/             
│   │           ├── security/          
│   │           ├── exceptions/         
│   │           ├── aspect/             
│   │           ├── utility/            
│   │           └── FlightManagementSystemApplication.java  
│   └── resources/
│       ├── application.properties      
│       
├── src/
│   └── test/
│       └── java/
│           └── com.flightmanagement/
│               ├── service/     
├── pom.xml                           
└── README.md  

       

                

<div >

### Made by 
Khushi
Kumar Sanjeev
Bollina Sai


</div>




















