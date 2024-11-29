//import com.sun.tools.javac.code.Attribute;

import java.sql.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.InputMismatchException;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Connection conn = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/employee_db",
                    "root", "Karibu@1234");
//            conn = DatabaseConnection.getConnection();
            System.out.println("Welcome to the HRM System");

            while (true) {
                // Display th…
[19:22, 11/29/2024] Enock KCA MSC: import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;

class Employee {
    private int employeeID;
    private String firstName;
    private String lastName;
    private String position;
    private String emailAddress;
    private String phoneNumber;
    private String userName;
    private String password;

    Employee(int employeeID, String firstName, String lastName, String position,
             String emailAddress, String phoneNumber, String userName, String password) {
        this.employeeID = employeeID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.position = position;
        this.emailAddress = emailAddress;
        this.phoneNumber = phoneNumber;
        this.userName = userName;
        this.password = password;
    }

    // CRUD Operations
    static void addEmployee(Connection conn, Employee employee) throws SQLException {
        String query = "INSERT INTO Employee (FirstName, LastName, Position, EmailAddress, PhoneNumber, UserName, Password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, employee.firstName);
        stmt.setString(2, employee.lastName);
        stmt.setString(3, employee.position);
        stmt.setString(4, employee.emailAddress);
        stmt.setString(5, employee.phoneNumber);
        stmt.setString(6, employee.userName);
        stmt.setString(7, employee.password);
        stmt.executeUpdate();
    }

    static void searchEmployee(Connection conn, int employeeID) throws SQLException {
        String query = "SELECT * FROM Employee WHERE EmployeeID = ?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, employeeID);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getString("FirstName") + " " + rs.getString("LastName"));
        }
    }

    static void editEmployee(Connection conn, Employee employee) throws SQLException {
        String query = "UPDATE Employee SET FirstName=?, LastName=?, Position=?, EmailAddress=?, PhoneNumber=?, UserName=?, Password=? WHERE EmployeeID=?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, employee.firstName);
        stmt.setString(2, employee.lastName);
        stmt.setString(3, employee.position);
        stmt.setString(4, employee.emailAddress);
        stmt.setString(5, employee.phoneNumber);
        stmt.setString(6, employee.userName);
        stmt.setString(7, employee.password);
        stmt.setInt(8, employee.employeeID);
        stmt.executeUpdate();
    }

    static void deleteEmployee(Connection conn, int employeeID) throws SQLException {
        String query = "DELETE FROM Employee WHERE EmployeeID=?";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, employeeID);
        stmt.executeUpdate();
    }
}

class Leave {
    private int leaveID;
    private String leaveType;
    private LocalDate startDate;
    private LocalDate endDate;

    public Leave(int leaveID, String leaveType, LocalDate startDate, LocalDate endDate) {
        this.leaveID = leaveID;
        this.leaveType = leaveType;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static void applyLeave(Connection conn, Leave leave, int employeeID) throws SQLException {
        String query = "INSERT INTO LeaveRecord (LeaveType, StartDate, EndDate, EmployeeID) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, leave.leaveType);
        stmt.setDate(2, Date.valueOf(leave.startDate));
        stmt.setDate(3, Date.valueOf(leave.endDate));
        stmt.setInt(4, employeeID);
        stmt.executeUpdate();
    }

    // Approve, Reject and other operations can be defined similarly
}

class Attendance {
    private LocalTime checkInTime;
    private LocalTime checkOutTime;
    private LocalTime hoursWorked;

    public Attendance(LocalTime checkInTime, LocalTime checkOutTime, LocalTime hoursWorked) {
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.hoursWorked = hoursWorked;
    }

    public static void markAttendance(Connection conn, int employeeID) throws SQLException {
        String query = "INSERT INTO Attendance (EmployeeID, CheckInTime, CheckOutTime, HoursWorked) VALUES (?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, employeeID);
        stmt.setTime(2, Time.valueOf(LocalTime.now()));
        stmt.setTime(3, null);
        stmt.setTime(4, null);
        stmt.executeUpdate();
    }

    public static void markCheckOut(Connection conn, int employeeID) throws SQLException {
        String query = "UPDATE Attendance SET CheckOutTime=?, HoursWorked=? WHERE EmployeeID=? AND CheckOutTime IS NULL";
        PreparedStatement stmt = conn.prepareStatement(query);
        LocalTime checkOutTime = LocalTime.now();
        stmt.setTime(1, Time.valueOf(checkOutTime));
        stmt.setTime(2, Time.valueOf(LocalTime.now().minusHours(8))); // Assuming 8 hours shift
        stmt.setInt(3, employeeID);
        stmt.executeUpdate();
    }
}

class Payroll {
    private int payrollProcessingID;
    private float basicSalary;
    private float allowance;
    private float netSalary;
    private LocalDate paymentDate;

    public Payroll(int payrollProcessingID, float basicSalary, float allowance, float netSalary, LocalDate paymentDate) {
        this.payrollProcessingID = payrollProcessingID;
        this.basicSalary = basicSalary;
        this.allowance = allowance;
        this.netSalary = netSalary;
        this.paymentDate = paymentDate;
    }

    public static void processPayroll(Connection conn, Payroll payroll, int employeeID) throws SQLException {
        String query = "INSERT INTO Payroll (EmployeeID, BasicSalary, Allowance, NetSalary, PaymentDate) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setInt(1, employeeID);
        stmt.setFloat(2, payroll.basicSalary);
        stmt.setFloat(3, payroll.allowance);
        stmt.setFloat(4, payroll.netSalary);
        stmt.setDate(5, Date.valueOf(payroll.paymentDate));
        stmt.executeUpdate();
    }
}

class Department {
    private int departmentID;
    private String departmentName;

    public Department(int departmentID, String departmentName) {
        this.departmentID = departmentID;
        this.departmentName = departmentName;
    }

    public static void addDepartment(Connection conn, Department department) throws SQLException {
        String query = "INSERT INTO Department (DepartmentName) VALUES (?)";
        PreparedStatement stmt = conn.prepareStatement(query);
        stmt.setString(1, department.departmentName);
        stmt.executeUpdate();
    }
}
[19:22, 11/29/2024] Enock KCA MSC: CREATE TABLE Department (
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
