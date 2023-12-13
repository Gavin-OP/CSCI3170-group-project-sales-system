import java.sql.Connection;
import java.sql.SQLException;

// Admin menu page takes input from user and call the corresponding function in Administrator.java 
public class AdministratorPage extends Page {
    public static void start(Connection conn) {
        boolean running = true;
        while (running) {
            System.out.println();
            printAdminMenu();
            int choice = takeChoiceInput(1, 5);
            try {
                switch (choice) {
                    case 1:
                        Administrator.createAllTables(conn);
                        System.out.println("Tables created successfully.");
                        break;
                    case 2:
                        Administrator.deleteAllTables(conn);
                        System.out.println("Tables deleted successfully.");
                        break;
                    case 3:
                        Administrator.loadDataFromFiles(conn);
                        System.out.println("Data loaded successfully.");
                        break;
                    case 4:
                        Administrator.showContent(conn);
                        break;
                    case 5:
                        running = false;
                        break;
                    default:
                        System.out.println("Invalid choice. Please choose a valid option.");
                        break;
                }
            } catch (SQLException e) {
                System.err.println("Error: " + e.getMessage());
            } finally {
                pressEnterToContinue();
            }
        }
    }
}
