# SwiftBank (Banking Application)

A Java Swing desktop application simulating a real-world banking system, built with a modular architecture and connected to a MySQL database.

## Features

- 🔐 **Secure Authentication**  
  Users can sign up or log in using their account number. Passwords are securely hashed.

- 🏦 **Account Management**  
  - Create checking and linked savings accounts  
  - Auto-generated RIB and account numbers  
  - Switch between accounts dynamically  

- 💳 **Transactions**  
  - Deposit, withdraw, and transfer funds  
  - Add and manage beneficiaries  
  - Filterable transaction history  

- 📊 **User Dashboard**  
  - Interactive Swing-based GUI  
  - Sidebar navigation for all operations  

- 📁 **Persistence**  
  All transactions are logged and stored in a MySQL database.

## Architecture

This application follows the **MVC design pattern** and uses the **DAO pattern** for database interactions.

```
banqapp/
└── src/
├── app # Application entry point
├── controller # Controllers handling user actions
├── dao # Data access layer
├── dto # Data transfer objects
├── exception # Custom exceptions
├── model # Domain models
├── service # Business logic
├── util # Utility classes
└── view # Swing UI components
```

## Technologies

- Java (Swing GUI)
- MySQL
- JDBC
- MVC & DAO Patterns

## Getting Started

1. Clone the repository  
   ```bash
   git clone https://github.com/yourusername/banqapp.git
## Getting Started

- Import into Eclipse or any Java IDE  
- Set up the MySQL database using the provided SQL scripts  
- Run the application from the `app` package
   