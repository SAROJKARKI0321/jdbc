import java.sql.*;
import java.util.Scanner;

public class JDBC {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            Connection con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/studentdatabase", "root", "Birthday$19");
            Statement st = con.createStatement();

            while ((true)) {
                System.out.println("CHOOSE AN ACTION");
                System.out.println("1. Insert a new student");
                System.out.println("2. View all students");
                System.out.println("3. Update a student's GPA");
                System.out.println("4. Delete a student by ID");
                System.out.println("5. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine();

                switch (choice) {
                    case 1:
                        System.out.println("Enter students name:");
                        String name = scanner.nextLine();
                        System.out.println("Enter GPA:");
                        double gpa = scanner.nextDouble();
                        scanner.nextLine();

                        String insertquery = "INSERT INTO studentsdata(name,GPA) VALUES(?,?)";
                        PreparedStatement insertStatement = con.prepareStatement(insertquery);
                        insertStatement.setString(1, name);
                        insertStatement.setDouble(2, gpa);
                        int rowsadded = insertStatement.executeUpdate();
                        System.out.println("Student inserted sucessfully" + "+" + rowsadded);
                        break;


                    case 2:
                        String selectQuery = "SELECT * FROM studentsdata";
                        ResultSet reSet = st.executeQuery(selectQuery);
                        System.out.println("ALL STUDENTS:");
                        while (reSet.next()) {
                            System.out.println("ID:" + reSet.getInt("id"));
                            System.out.println("Name:" + reSet.getString("name"));
                            System.out.println("GPA" + reSet.getDouble("GPA"));
                            System.out.println("---------------------------");
                        }
                        break;

                    case 3:
                        System.out.println("ENTER STUDENTS ID TO UPDATE GPA:");
                        int studentId = scanner.nextInt();

                        System.out.println("Enter new GPA:");
                        double newGPA = scanner.nextDouble();
                        scanner.nextLine();

                        String updateQuery = "UPDATE studentsdata SET GPA=? WHERE id=?";
                        PreparedStatement updateStatement = con.prepareStatement(updateQuery);
                        updateStatement.setDouble(1, newGPA);
                        updateStatement.setInt(2, studentId);
                        int rowsUpdated = updateStatement.executeUpdate();
                        if (rowsUpdated > 0) {
                            System.out.println("GPA updated sucessfully");
                        } else {
                            System.out.println("Student data not found" + studentId);
                        }
                        break;

                    case 4:
                        System.out.println("Enter students id to remove from database");
                        int delId = scanner.nextInt();
                        scanner.nextLine();
                        String delQuery = "DELETE FROM studentsdata WHERE id=?";
                        PreparedStatement delStatement = con.prepareStatement(delQuery);
                        delStatement.setInt(1, delId);
                        int rowsDeleted = delStatement.executeUpdate();
                        if (rowsDeleted > 0) {
                            System.out.println("Student deleted successfully!");
                        } else {
                            System.out.println("No student found with ID " + delId);
                        }
                        break;

                    case 5:
                        System.out.println("EXITING THE PROGRAM>>>>>>>>>>>");
                        con.close();
                        scanner.close();
                        return;

                    default:
                        System.out.println("Invalid entry.");
                }

                }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}






