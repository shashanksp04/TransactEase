# Any Time Money (ATM) Machine Replica

This project is a replica of an Any Time Money (ATM) machine for a bank. It operates like a normal ATM machine, allowing users to create an account, deposit money, withdraw money, check balance and transaction history, and change their PIN. The project is built using Java and utilizes PostgreSQL for data storage.

## Features

The ATM machine replica offers the following features:

1. Account Creation: Users can create a new account by providing their personal information and selecting a PIN.

2. Deposit: Users can deposit money into their account by entering the desired amount.

3. Withdrawal: Users can withdraw money from their account by entering the amount they wish to withdraw, provided they have sufficient funds.

4. Check Balance: Users can check the balance of their account to see the available funds.

5. Transaction History: Users can view the transaction history of their account, including deposits and withdrawals.

6. PIN Change: In case a user forgets their PIN or wishes to change it, the ATM machine provides assistance in updating the PIN.

## User-Friendly Interface

One of the main challenges during the development of this project was to ensure a user-friendly experience. To address this challenge, the following features were implemented:

1. Prompting for Further Actions: After every transaction, the ATM machine asks the user whether they need to perform any additional actions. This feature allows users to easily perform multiple transactions without having to navigate through the entire menu again.

2. Transaction Declination: If a user enters incorrect information for more than three times, the ATM machine automatically declines the transaction. In case of incorrect PIN entries, the machine assists the user in changing their PIN if they wish to do so.

## Technologies Used

The project utilizes the following technologies:

- Java: The core programming language used for implementing the ATM machine functionality.
- PostgreSQL: The chosen database management system for storing account details, transaction history, and PIN information.
- JDBC: Java Database Connectivity is employed to establish a connection between the Java application and the PostgreSQL database.

## Getting Started

To run the ATM machine replica locally, follow these steps:

1. Clone the repository to your local machine.

2. Set up a PostgreSQL database and configure the connection details in the application.

3. Build the Java application using an IDE or command line tools.

4. Run the application and start using the ATM machine replica.

## Contributions

Contributions to the project are welcome! If you encounter any issues or have suggestions for improvement, please open an issue or submit a pull request on the GitHub repository.

## License

This project is licensed under the [MIT License](LICENSE). Feel free to modify and use the code for your own purposes.

## Acknowledgements

- Special thanks to the open-source community for providing resources and inspiration for building this Any Time Money (ATM) machine replica.

## Contact

If you have any questions or inquiries regarding the project, please feel free to contact the project owner at [email address].
