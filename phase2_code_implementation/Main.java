//
// writer: ZHANG Haoxiang
// Last modification: 2023-12-13
//
// Connect to the database and start the program
// Print the main menu and take input from the user
// Call the corresponding function in AdministratorPage.java, Salesperson.java or Manager.java
//

import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Page {

    // Database credentials
    private static final String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db26?autoReconnect=true&useSSL=false&allowLoadLocalInfile=true";
    private static final String dbUsername = "Group26";
    private static final String dbPassword = "CSCI3170";

    // Connect to the database
    public static Connection connectToMySQL() {
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(dbAddress, dbUsername, dbPassword);
        } catch (ClassNotFoundException e) {
            System.out.println("[Error]: Java MySQL DB Driver not found!!");
            // System.exit(0);
        } catch (SQLException e) {
            System.out.println(e);
            System.out.println(e.getMessage());
        }
        return con;
    }

    // Take input from user and call the corresponding function
    public static void main(String[] args) throws SQLException {
        Connection conn = connectToMySQL();

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = takeChoiceInput(1, 4);
            switch (choice) {
                case 1:
                    AdministratorPage.start(conn);
                    break;
                case 2:
                    Salesperson.Salesperson_operation();
                    break;
                case 3:
                    Manager.Manager_operation();
                    break;
                default:
                    System.out.println("Bye Bye!");
                    running = false;
                    break;
            }
        }
    }
}
