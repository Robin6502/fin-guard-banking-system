# FinGuard

A banking transaction and fraud detection system built with Java and Spring Boot.

FinGuard lets you create accounts, transfer money between them, and checks every transaction against fraud rules.

## Features

- Create accounts and transfer money
- Transaction history
- Fraud detection with four rules: high amount, velocity, unusual hour and balance drain
- Error handling for invalid transfers

## Tech Stack

- Java
- Spring Boot
- Maven
- PostgreSQL

## How to Run

1. Clone the repository:

   ```bash
   git clone https://github.com/Robin6502/fin-guard-banking-system.git
   cd fin-guard-banking-system
   ```

2. Create a PostgreSQL database called `finguard`.

3. Set your database login:

   ```bash
   export DB_USER=postgres
   export DB_PASSWORD=your_password_here
   ```

4. Start the app:

   ```bash
   mvn spring-boot:run
   ```

5. The app runs at http://localhost:8080.

## Planned

- Unit tests
- User login
- Docker setup
- SoloBanking: a voice assistant for visually impaired users

## Author

Robin Tigere | [GitHub](https://github.com/Robin6502) | [LinkedIn](https://www.linkedin.com/in/robin-tigere-14ba772a0/)
