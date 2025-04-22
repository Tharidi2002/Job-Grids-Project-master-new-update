JobGrids - Job Portal System
Project Description
JobGrids is a full-stack job portal application that connects job seekers with employers. The system features:

User Types: Job seekers, company representatives, and admin users

Key Functionalities:

Job postings and applications

Company profiles

Interactive chatbot for support

User profile management

Technical Stack:

Frontend: HTML5, CSS3, JavaScript, Bootstrap

Backend: Spring Boot (Java)

Database: MySQL

Screenshots
Home Page
Home Page
Landing page with job search functionality

User Dashboard
User Dashboard
Personalized dashboard for job seekers

Company Profile
Company Profile
Company management interface

Chat Support
Chat Support
Interactive FAQ chatbot

Setup Instructions
Backend Setup
Requirements:

Java 17+

MySQL 8.0+

Maven

Installation:

bash
git clone https://github.com/yourusername/jobgrids-backend.git
cd jobgrids-backend
Database Configuration:

Create MySQL database:

sql
CREATE DATABASE jobNet;
Update application.properties:

properties
spring.datasource.url=jdbc:mysql://localhost:3306/jobNet
spring.datasource.username=yourusername
spring.datasource.password=yourpassword
Run Application:

bash
mvn spring-boot:run
Frontend Setup
Requirements:

Modern web browser

Live server extension (for local development)

Installation:

bash
git clone https://github.com/yourusername/jobgrids-frontend.git
cd jobgrids-frontend
Configuration:

Update API endpoints in js/config.js:

javascript
const API_BASE_URL = "http://localhost:8082/api/v1";
Run Application:

Open index.html in browser or use live server

Demo Video
Watch JobGrids Demo