import java.sql.Connection;
import java.sql.SQLException;

public class AdministratorPage extends Page {
    public static void start(Connection conn) throws SQLException {
        boolean running = true;
        while (running) {
            System.out.println();
            printAdminMenu();
            int choice = takeChoiceInput(1, 5);
            switch (choice) {
                case 1:
                    Administrator.createAllTables(conn);
                    pressEnterToContinue();
                    break;
                case 2:
                    Administrator.deleteAllTables(conn);
                    pressEnterToContinue();
                    break;
                case 3:
                    Administrator.loadDataFromFiles(conn);
                    System.out.println();
                    pressEnterToContinue();
                    break;
                case 4:
                    Administrator.showContent(conn);
                    pressEnterToContinue();
                    break;
                default:
                    running = false;
                    break;
            }
        }
    }

}