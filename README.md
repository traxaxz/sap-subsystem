Welcome to Sab Subsystem task

This is the Backend service of the program.

Steps to run the service:
    - Clone the repo
    - In the root folder of the service type the command 'docker compose up'

The service should start properly, it will create a postgresSql image,
Vault image for storing sensitive data like in the following case Github token and owner values.

Testing the BE application can be done by Postman, here are the following endpoints to hit:

Create Repository (POST)
-
The service will create a real repository in github and then store it in PostgresDB

- Endpoint: http://localhost:8081/repository
  - Json body: {"repository": "test"}
  
List All Repositories (GET)
-
This will list all created repositories that are stored in PostgresDB

- Endpoint: http://localhost:8081/repository
    - Response: [{ "businessId": "some uuid", "repository": "test"}]

There are secrets attached to the repository the response will be:
- [
  {
  "businessId": "some uuid",
  "repository": "test",
  "secrets": [
  {
  "businessId": "some uuid",
  "secret": "someSecret"
  }
  ]
  }
  ]

List specific repository (GET)
-
This endpoint will return a repository by the given id 

        