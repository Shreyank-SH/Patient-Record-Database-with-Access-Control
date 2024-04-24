# Patient Record Database with Access Control

The Patient Record Database with Access Control is a Java-based application that allows doctors and family members to manage patient health records. It provides features for adding, viewing, updating, and deleting health records, as well as user authentication and authorization.


**GUI Screenshot: **
Few GUI components screenshots are attached in the folder screenshots

## Features

- **User Authentication**: The system supports two types of users: doctors and family members. Doctors can sign up and log in with their credentials, while family members can log in with their names.
- **Record Management**: Doctors have full access to add, view, update, and delete health records. Family members can view records but cannot add, update, or delete them.
- **Database Integration**: The system uses a MySQL database to store health records and doctor credentials. The database is automatically created if it doesn't exist, and separate tables are created for each doctor to store their patient records.
- **User Interface**: The application provides a graphical user interface (GUI) for user interactions. The main window displays options for record management based on the user type (doctor or family member).

## Key Object-Oriented Concepts

The application follows Object-Oriented Programming (OOP) principles and incorporates several key OOP concepts:

1. **Inheritance**: The `User` class is an abstract base class that defines common properties and methods for users. The `Doctor` and `FamilyMember` classes inherit from the `User` class and override or extend its behavior as needed.
2. **Polymorphism**: The `AccessControl` interface defines a contract for accessing and modifying health records. The `User` class implements this interface, and the `Doctor` and `FamilyMember` classes provide their own implementations based on their access levels, demonstrating runtime polymorphism.
3. **Encapsulation**: The `HealthRecord` class encapsulates the data and behavior related to a patient's health record, providing getter and setter methods for accessing and modifying the record.
4. **Abstraction**: The `User` class is an abstract class, providing a common interface for user objects while allowing derived classes (`Doctor` and `FamilyMember`) to implement specific behaviors.
5. **Exception Handling**: The `UnauthorizedAccessException` class is a custom exception class used to handle unauthorized access attempts to health records.
6. **Interfaces**: The `AccessControl` interface defines a contract for accessing and modifying health records, promoting loose coupling and allowing for future extensions or modifications.
7. **Composition**: The `User` class has a composition relationship with the `HealthRecord` class, where a user can have multiple health records associated with them.
8. **Overloading**: The `addRecord`, `viewRecord`, `updateRecord`, and `deleteRecord` methods in the `User` class and its derived classes are overloaded to provide different implementations based on the input parameters.
9. **Overriding**: The `Doctor` and `FamilyMember` classes override certain methods from the `User` class to provide their own implementations specific to their access levels.
10. **Abstract Classes**: The `User` class is an abstract class, which defines common behavior for all user types and serves as a blueprint for concrete user classes like `Doctor` and `FamilyMember`.
11. **JDBC with MySQL Connection**: The `DatabaseManager` class uses JDBC (Java Database Connectivity) to connect to a MySQL database for storing and retrieving health records and doctor credentials.

## Logic and Implementation

1. **Database Manager (`DatabaseManager.java`)**: This class handles the database connection and operations. It creates the database and necessary tables if they don't exist. It also provides methods for inserting, retrieving, updating, and deleting health records.
2. **User Management (`User.java`, `Doctor.java`, `FamilyMember.java`)**: The `User` class is an abstract base class that defines the common properties and methods for users. The `Doctor` and `FamilyMember` classes extend the `User` class and implement specific behaviors for each user type.
3. **Health Record (`HealthRecord.java`)**: This class represents a health record, containing information such as patient name, diagnosis, treatment, and the doctor's name.
4. **Graphical User Interface (`HealthMonitoringGUI.java`)**: The main GUI class handles user authentication, record management, and window creation. It provides separate panels for doctors and family members, with appropriate functionality based on their access levels.
5. **Doctor Sign-Up (`Doctor.DoctorSignup`)**: This class provides a separate window for doctors to sign up by entering their name and password. The credentials are stored in the `doctor_verify` table in the database.
6. **Access Control (`AccessControl` interface)**: The `AccessControl` interface defines methods for viewing, updating, and deleting records. The `User` class implements this interface to enforce access control based on user type.

## Usage

1. Compile the Java files.
2. Run the `Patient_Record_With_Access_Control` class to launch the application.
3. Select the user type (doctor or family member) from the combo box.
4. If you are a doctor, you can sign up or log in with your credentials. If you are a family member, simply enter your name.
5. After successful authentication, the main application window will open, displaying the available options based on your user type.
6. Use the provided buttons and dialogs to manage health records (add, view, update, delete).

Note: The database configuration (URL, username, and password) is hardcoded in the `DatabaseManager` class. You may need to modify these values according to your MySQL setup.

**Important**: Before running the application, please update the following lines in the `DatabaseManager.java` file with your MySQL username and password:

```java
private static final String DB_USER = "Username";
private static final String DB_PASSWORD = "Password";
```
**Additional Notes**
- The application uses Java Swing for the graphical user interface.
- Error handling and input validation are implemented throughout the codebase.
- The application closes all open windows when changing modes or exiting.
- Appropriate access control measures are taken to prevent unauthorized access to records.

**Feel free to explore the codebase and modify it according to your needs. Contributions and improvements are welcome!**
