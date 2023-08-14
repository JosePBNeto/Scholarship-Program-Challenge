# Scholarship-Program-Challenge
CHALLENGE WEEK VIII - Spring Boot - Back-End Journey | AWS

### Description:
The Scholarship program is a REST API in a Spring Boot application that provides endpoints for managing a scholarship program. 
It allows you to perform operations such as creating, updating, deleting, and retrieving class, organizer, student and squads

### Prerequisites
Java JDK (17 or higher)
Maven
MySQL Database

### How to run the Application
1. Clone Repository from github
2. Build the project using Maven
3. Run the application



#### API Endpoints:

#### Class<br/>
GET /v1/classes: Get a list of all class records. <br/>
GET /v1/classes/{id}: Get details of a specific class by ID. <br />
POST /v1/classes: Create a new class record.<br />
PUT /v1/classes/{id}: Update an existing class record by ID.<br />
DELETE /v1/classes/{id}: Delete a class record by ID.<br />
PUT /v1/classes/{id}/start: Mark a class as started.<br />
PUT /v1/classes/{id}/finish: Mark a class as finished.<br />

#### Organizer<br />
GET /v1/organizers: Get a list of all organizer records.<br />
GET /v1/organizers/{id}: Get details of a specific organizer by ID.<br />
POST /v1/organizers: Create a new organizer record.<br />
PUT /v1/organizers/{id}: Update an existing organizer record by ID.<br />
DELETE /v1/organizers/{id}: Delete an organizer record by ID.<br />

#### Student<br />
GET /v1/students: Get a list of all student records.<br />
GET /v1/students/{id}: Get details of a specific student by ID.<br />
POST /v1/students: Create a new student record.<br />
PUT /v1/students/{id}: Update an existing student record by ID.<br />
DELETE /v1/students/{id}: Delete a student record by ID.<br />

#### Squad<br />

GET /v1/squads: Get a list of all squad records.<br />
GET /v1/squads/{id}: Get details of a specific squad by ID.<br />
POST /v1/squads: Create a new squad record.<br />
#### Database Population<br />
POST /v1/populate: Populate the database with sample data for organizers and students.<br />











