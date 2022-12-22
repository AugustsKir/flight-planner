# Flight Planner API 🛧

App made using Java & Spring Boot for storing airports and flights. App can be switched between an in-memory version and a PostgreSQL database.

## Instructions

1. Clone the project to your local computer.

2. If you want to run the app in database mode, have a postgres database set up :

```bash
docker run --name postgres -e POSTGRES_PASSWORD=codelex -d postgres
```
3. To run the app, open the terminal in the project folder and type :
```
./gradlew bootRun
```

## Endpoints

**ADMIN ENDPOINTS**

- /admin-api/flights | [PUT] | stores the flight inside the database.
- /admin-api/availabe | [GET] | shows all the stored flights.
- /admin-api/flights/{id} | [DELETE] | deletes a flight based on the flight ID.
- /admin-api/flights/{id} | [GET] | finds flight based on the flight ID.

---
**TESTING ENDPOINTS**

- /testing-api/clear | [POST] | clears all data.

---
**CUSTOMER ENDPOINTS**

- /api/airports | [GET] | finds airports based on input (Params).
- /api/flights/{id} | [GET] | finds flights based on the flight ID.
- /api/flights/search | [POST] | finds flights by sending a JSON object. 
## Application properties
To switch between memory storage and database storage, type the following in the **application.properties** file:
- Local memory configuration:
```
flight-app.storage=in-memory
```
- PostgreSQL database storage:
```
flight-app.storage=database
```
## Spring Security

To access the admin endpoints, use the following login:
```
username: codelex-admin
password: Password123
```
If you would like to change the login credentials, locate the **application.properties** file and change these two values:
```
spring.security.user.name=...
spring.security.user.password...
```

