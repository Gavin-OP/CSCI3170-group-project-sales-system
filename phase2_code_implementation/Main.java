import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class Main extends Page {
    public static void main(String[] args) throws SQLException {
        String driverName = "com.mysql.jdbc.Driver";
        String dbAddress = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db60?autoReconnect=true&useSSL=false";
        String userName = "Group26";
        String userPwd = "CSCI3170";
        Connection conn;

        try {
            Class.forName(driverName);

            conn = DriverManager.getConnection(dbAddress, userName, userPwd);

        } catch(Exception e) {
            e.printStackTrace();

            System.out.println("Connection failed!");

            return;
        }

        boolean running = true;
        while (running) {
            printMainMenu();
            int choice = takeChoiceInput(1, 4);
            switch (choice) {
                case 1:
                    AdministratorPage.start(conn);
                    break;
                case 2:
                    break;
                case 3:
                    break;
                default:
                    System.out.println("Bye Bye!");
                    running = false;
                    break;
            }
        }

    }
}