FinGuard simulates the core of a retail banking backend. It manages accounts, processes transfers between them, and runs every transaction through a set of fraud rules before it is recorded. 

The project focuses on clean backend structure: controllers handle requests, services hold the business logic, and repositories handle persistence. Fraud checks are separate rules, so new ones can be added without touching the transfer logic.

Contents
Features
How it works
Fraud rules
Tech stack
Project structure
Getting started
Roadmap
Author
Features
Create accounts and transfer money between them
Complete transaction history with status tracking
Rule-based fraud detection that scores every transaction
Clear error handling for invalid transfers, such as insufficient funds, unknown accounts and invalid amounts
Lightweight dashboard served by Spring Boot, with no separate frontend build
How it works
Transfer request
      |
      v
 Validation  --->  Rejected (error returned)
      |
      v
 Fraud rules  --->  Risk score calculated
      |
      v
 Transaction saved with its status
      |
      v
 Shown on the dashboard
A transfer request arrives at the transaction controller.
The transaction service checks the accounts and balance.
The fraud rules evaluate the transaction and add up a risk score.
The transaction is saved with its final status and appears in the history.
Fraud rules
Rule	What it flags
High amount	Transfers that are unusually large
Velocity	Too many transactions from one account in a short period
Unusual hour	Transactions made at odd hours of the day
Balance drain	A single transfer that takes 90% or more of the account balance (adds 30 points)

Rules are independent of each other, so a transaction can trigger several at once and the scores combine.

Tech stack
Layer	Technology
Language	Java (JDK 25)
Framework	Spring Boot 4
Build tool	Maven
Database	PostgreSQL
Frontend	Static HTML dashboard
Project structure
src/main/java/.../
├── account/        Account, AccountController, AccountRepository
├── transaction/    Transaction, TransactionController, TransactionService,
│                   TransactionRepository, TransactionStatus, TransactionType
└── fraud/          Fraud rules and risk scoring
src/main/resources/
├── static/         Dashboard page
└── application.properties
Getting started
Prerequisites
JDK 25 (or the version set in pom.xml)
Maven 3.9 or newer
PostgreSQL running locally
Run it
bash
# 1. Clone the repository
git clone https://github.com/Robin6502/fin-guard-banking-system.git
cd fin-guard-banking-system

# 2. Create the database (in psql)
#    CREATE DATABASE finguard;

# 3. Set your database credentials
export DB_USER=postgres
export DB_PASSWORD=your_password_here

# 4. Start the application
mvn spring-boot:run

Then open http://localhost:8080 to see the dashboard.

Run the tests
bash
mvn test
Roadmap
 Unit tests for the fraud rules
 User login and authentication
 Docker and Docker Compose setup
 Alert history for flagged transactions
 SoloBanking, a voice banking assistant for accessibility: visually impaired users can check their balance, hear their transaction history and send money by speaking, hands-free and in their preferred language
Author

Robin Tigere, Software Engineering graduate, Harare, Zimbabwe

LinkedIn | GitHub
