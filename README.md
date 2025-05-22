
# Receipt Processor

This is a Spring Boot application that processes receipts and calculates reward points based on specific rules.

## 📦 Features

- Submit a receipt via a REST endpoint and get a unique ID.
- Retrieve the calculated reward points for a given receipt.
- Modular design with repository, service, and controller layers.
- Unit and integration tests with JUnit and Mockito.

## How to run the application
Ensure Docker is installed on your system

First build the docker using the following command: 

- docker build -t receipt-processor .

After the docker is built you can run the application using the following command:

- docker run -p 8080:8080 receipt-processor

Use this URL to access the API 

- http://localhost:8080
