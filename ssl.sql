CREATE TABLE Department (
    DepartmentID INT AUTO_INCREMENT PRIMARY KEY,
    DepartmentName VARCHAR(100) NOT NULL
);

CREATE TABLE Employee (
    EmployeeID INT AUTO_INCREMENT PRIMARY KEY,
    FirstName VARCHAR(100) NOT NULL,
    LastName VARCHAR(100) NOT NULL,
    Position VARCHAR(100),
    EmailAddress VARCHAR(100), 
    PhoneNumber VARCHAR(15),
    UserName VARCHAR(50),
    Password VARCHAR(50),
    DepartmentID INT,
    FOREIGN KEY (DepartmentID) REFERENCES Department(DepartmentID)
);

CREATE TABLE LeaveRecord (
    LeaveID INT AUTO_INCREMENT PRIMARY KEY,
    LeaveType VARCHAR(50) NOT NULL,
    StartDate DATE NOT NULL,
    EndDate DATE NOT NULL,
    EmployeeID INT,
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
);

CREATE TABLE Attendance (
    AttendanceID INT AUTO_INCREMENT PRIMARY KEY,
    EmployeeID INT,
    CheckInTime TIME,
    CheckOutTime TIME,
    HoursWorked TIME,
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
);

CREATE TABLE Payroll (
    PayrollProcessingID INT AUTO_INCREMENT PRIMARY KEY,
    EmployeeID INT,
    BasicSalary FLOAT NOT NULL,
    Allowance FLOAT NOT NULL,
    NetSalary FLOAT NOT NULL,
    PaymentDate DATE NOT NULL,
    FOREIGN KEY (EmployeeID) REFERENCES Employee(EmployeeID)
);
