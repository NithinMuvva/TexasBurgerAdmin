## Texas Burger Spring Boot Application

Added Swagger for better undersatding of API's: http://localhost:8080/v2/api-docs http://localhost:8080/swagger-ui.html 

### PreReq:

Download the project and run as Spring Boot App Please download MongoDb in your local and Start the MongoServer Update the application.properties if needed.

### App Structure:

**Model** : contains location, menu and reservation and open hours

**Repository Layer** : contains location, menu and reservation repositories implemented using mongoRepository for storing, retrieving and searching collection of objects from a database

**Service Layer** : contains business logic for searching, storing, deleting and updating data in different collections of database

**Controller Layer** : contains location, menu, reservation RESTful web controllers which exposes different kinds of Apis on all Models

API's exposed
- search nearest location by passing latitude and longitude
- search locations that are open now
- search location by name and status filters
- search menu by item name and category filters

Implemented pagination to Api's to improve application performance
