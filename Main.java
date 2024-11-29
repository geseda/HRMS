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
                // Display the menu
                displayMenu();
                int choice = 0;

                // Handle invalid input type for choice
                try {
                    choice = scanner.nextInt();
                } catch (InputMismatchException e) {
                    System.out.println("Invalid input. Please enter a number from the menu.");
                    scanner.next(); // Clear the invalid input
                    continue;
                }

                // Process user choice
                switch (choice) {
                    case 1: // Add Employee
                        addEmployee(conn, scanner);
                        break;
                    case 2: // Search Employee
                        searchEmployee(conn, scanner);
                        break;
                    case 3: // Edit Employee
                        editEmployee(conn, scanner);
                        break;
                    case 4: // Delete Employee
                        deleteEmployee(conn, scanner);
                        break;
                    case 5: // Apply Leave
                        applyLeave(conn, scanner);
                        break;
                    case 6: // Mark Attendance
                        markAttendance(conn, scanner);
                        break;
                    case 7: // Mark Check-Out
                        markCheckOut(conn, scanner);
                        break;
                    case 8: // Process Payroll
                        processPayroll(conn, scanner);
                        break;
                    case 9: // Add Department
                        addDepartment(conn, scanner);
                        break;
                    case 10: // Quit
                        System.out.println("Exiting the system...");
                        return;
                    default:
                        System.out.println("Invalid choice. Please select a valid option.");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\nPlease select an option:");
        System.out.println("1. Add Employee");
        System.out.println("2. Search Employee");
        System.out.println("3. Edit Employee");
        System.out.println("4. Delete Employee");
        System.out.println("5. Apply for Leave");
        System.out.println("6. Mark Attendance");
        System.out.println("7. Mark Check-Out");
        System.out.println("8. Process Payroll");
        System.out.println("9. Add Department");
        System.out.println("10. Quit");
        System.out.print("Enter your choice: ");
    }

    // Add Employee
    private static void addEmployee(Connection conn, Scanner scanner) {
        try {
            System.out.println("Enter First Name: ");
            String firstName = scanner.next();
            System.out.println("Enter Last Name: ");
            String lastName = scanner.next();
            System.out.println("Enter Position: ");
            String position = scanner.next();
            System.out.println("Enter Email Address: ");
            String email = scanner.next();
            System.out.println("Enter Phone Number: ");
            String phone = scanner.next();
            System.out.println("Enter Username: ");
            String username = scanner.next();
            System.out.println("Enter Password: ");
            String password = scanner.next();

            Employee employee = new Employee(0, firstName, lastName, position, email, phone, username, password);
            Employee.addEmployee(conn, employee);
            System.out.println("Employee added successfully!");

        } catch (SQLException e) {
            System.out.println("Error adding employee: " + e.getMessage());
        }
    }

    // Search Employee
    private static void searchEmployee(Connection conn, Scanner scanner) {
        try {
            System.out.println("Enter Employee ID to search: ");
            int employeeID = scanner.nextInt();
            Employee.searchEmployee(conn, employeeID);
        } catch (SQLException | InputMismatchException e) {
            System.out.println("Error searching employee: " + e.getMessage());
            scanner.next(); // Handle input mismatch
        }
    }

    // Edit Employee
    private static void editEmployee(Connection conn, Scanner scanner) {
        try {
            System.out.println("Enter Employee ID to edit: ");
            int employeeID = scanner.nextInt();
            System.out.println("Enter new First Name: ");
            String firstName = scanner.next();
            System.out.println("Enter new Last Name: ");
            String lastName = scanner.next();
            System.out.println("Enter new Position: ");
            String position = scanner.next();
            System.out.println("Enter new Email Address: ");
            String email = scanner.next();
            System.out.println("Enter new Phone Number: ");
            String phone = scanner.next();
            System.out.println("Enter new Username: ");
            String username = scanner.next();
            System.out.println("Enter new Password: ");
            String password = scanner.next();

            Employee employee = new Employee(employeeID, firstName, lastName, position, email, phone, username, password);
            Employee.editEmployee(conn, employee);
            System.out.println("Employee updated successfully!");

        } catch (SQLException e) {
            System.out.println("Error editing employee: " + e.getMessage());
        }
    }

    // Delete Employee
    private static void deleteEmployee(Connection conn, Scanner scanner) {
        try {
            System.out.println("Enter Employee ID to delete: ");
            int employeeID = scanner.nextInt();
            Employee.deleteEmployee(conn, employeeID);
            System.out.println("Employee deleted successfully!");
        } catch (SQLException | InputMismatchException e) {
            System.out.println("Error deleting employee: " + e.getMessage());
            scanner.next(); // Handle input mismatch
        }
    }

    // Apply for Leave
    private static void applyLeave(Connection conn, Scanner scanner) {
        try {
            System.out.println("Enter Employee ID: ");
            int employeeID = scanner.nextInt();
            System.out.println("Enter Leave Type (e.g., Sick, Vacation): ");
            String leaveType = scanner.next();
            System.out.println("Enter Start Date (YYYY-MM-DD): ");
            String startDate = scanner.next();
            System.out.println("Enter End Date (YYYY-MM-DD): ");
            String endDate = scanner.next();

            Leave leave = new Leave(0, leaveType, LocalDate.parse(startDate), LocalDate.parse(endDate));
            Leave.applyLeave(conn, leave, employeeID);
            System.out.println("Leave applied successfully!");

        } catch (SQLException e) {
            System.out.println("Error applying leave: " + e.getMessage());
        }
    }

    // Mark Attendance
    private static void markAttendance(Connection conn, Scanner scanner) {
        try {
            System.out.println("Enter Employee ID to mark attendance: ");
            int employeeID = scanner.nextInt();
            Attendance.markAttendance(conn, employeeID);
            System.out.println("Attendance marked successfully!");
        } catch (SQLException | InputMismatchException e) {
            System.out.println("Error marking attendance: " + e.getMessage());
            scanner.next(); // Handle input mismatch
        }
    }

    // Mark Check-Out
    private static void markCheckOut(Connection conn, Scanner scanner) {
        try {
            System.out.println("Enter Employee ID to mark check-out: ");
            int employeeID = scanner.nextInt();
            Attendance.markCheckOut(conn, employeeID);
            System.out.println("Check-out marked successfully!");
        } catch (SQLException | InputMismatchException e) {
            System.out.println("Error marking check-out: " + e.getMessage());
            scanner.next(); // Handle input mismatch
        }
    }

    // Process Payroll
    private static void processPayroll(Connection conn, Scanner scanner) {
        try {
            System.out.println("Enter Employee ID: ");
            int employeeID = scanner.nextInt();
            System.out.println("Enter Basic Salary: ");
            float basicSalary = scanner.nextFloat();
            System.out.println("Enter Allowance: ");
            float allowance = scanner.nextFloat();
            System.out.println("Enter Net Salary: ");
            float netSalary = scanner.nextFloat();
            System.out.println("Enter Payment Date (YYYY-MM-DD): ");
            String paymentDate = scanner.next();

            Payroll payroll = new Payroll(0, basicSalary, allowance, netSalary, LocalDate.parse(paymentDate));
            Payroll.processPayroll(conn, payroll, employeeID);
            System.out.println("Payroll processed successfully!");

        } catch (SQLException | InputMismatchException e) {
            System.out.println("Error processing payroll: " + e.getMessage());
            scanner.next(); // Handle input mismatch
        }
    }

    // Add Department
    private static void addDepartment(Connection conn, Scanner scanner) {
        try {
            System.out.println("Enter Department Name: ");
            String departmentName = scanner.next();
            Department department = new Department(0, departmentName);
            Department.addDepartment(conn, department);
            System.out.println("Department added successfully!");
        } catch (SQLException e) {
            System.out.println("Error adding department: " + e.getMessage());
        }
    }
}
