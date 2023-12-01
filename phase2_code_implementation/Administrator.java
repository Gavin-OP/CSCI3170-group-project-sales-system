import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Administrator extends Operation {
    private static final String CREATE_CATEGORY =
            "CREATE TABLE IF NOT EXISTS category (" +
                    "c_id INT NOT NULL AUTO_INCREMENT," +
                    "c_name VARCHAR(20) NOT NULL," +
                    "PRIMARY KEY (c_id)" +
            ")";

    private static final String CREATE_MANUFACTURER =
            "CREATE TABLE IF NOT EXISTS manufacturer (" +
                    "m_id INT NOT NULL AUTO_INCREMENT," +
                    "m_name VARCHAR(20) NOT NULL," +
                    "m_address VARCHAR(50) NOT NULL," +
                    "m_phone_number INT NOT NULL," +
                    "PRIMARY KEY (m_id)" +
            ")";

    private static final String CREATE_PART =
            "CREATE TABLE IF NOT EXISTS part (" +
                    "p_id INT NOT NULL AUTO_INCREMENT," +
                    "p_name VARCHAR(20) NOT NULL," +
                    "price INT NOT NULL," +
                    "manufacturer_id INT NOT NULL," +
                    "category_id INT NOT NULL," +
                    "warranty INT NOT NULL," +
                    "available_quantity INT NOT NULL," +
                    "PRIMARY KEY (p_id)," +
                    "FOREIGN KEY (manufacturer_id) REFERENCES manufacturer(m_id)," +
                    "FOREIGN KEY (category_id) REFERENCES category(c_id)" +
            ")";

    private static final String CREATE_SALESPERSON =
            "CREATE TABLE IF NOT EXISTS salesperson (" +
                    "s_id INT NOT NULL AUTO_INCREMENT," +
                    "s_name VARCHAR(20) NOT NULL," +
                    "s_address VARCHAR(50) NOT NULL," +
                    "s_phone_number INT NOT NULL," +
                    "experience INT NOT NULL," +
                    "PRIMARY KEY (s_id)" +
            ")";

    private static final String CREATE_TRANSACTION =
            "CREATE TABLE IF NOT EXISTS transaction (" +
                    "t_id INT NOT NULL AUTO_INCREMENT," +
                    "part_id INT NOT NULL," +
                    "salesperson_id INT NOT NULL," +
                    "date VARCHAR(10) NOT NULL," +
                    "PRIMARY KEY (t_id)," +
                    "FOREIGN KEY (part_id) REFERENCES part(p_id)," +
                    "FOREIGN KEY (salesperson_id) REFERENCES salesperson(s_id)" +
            ")";

    private static final String DELETE_TRANSACTION = "DROP TABLE IF EXISTS transaction";
    private static final String DELETE_SALESPERSON = "DROP TABLE IF EXISTS salesperson";
    private static final String DELETE_PART = "DROP TABLE IF EXISTS part";
    private static final String DELETE_MANUFACTURER = "DROP TABLE IF EXISTS manufacturer;";
    private static final String DELETE_CATEGORY = "DROP TABLE IF EXISTS category";
    private static String currentPath = System.getProperty("user.dir");
    private static final String LOAD_DATA = "LOAD DATA LOCAL INFILE '" + currentPath + "/";
    private static final String[] createTables = {CREATE_CATEGORY, CREATE_MANUFACTURER, CREATE_PART, CREATE_SALESPERSON, CREATE_TRANSACTION};
    private static final String[] deleteTables = {DELETE_TRANSACTION, DELETE_SALESPERSON, DELETE_PART, DELETE_MANUFACTURER, DELETE_CATEGORY};
    private static final String[] tableNames = {"category", "manufacturer", "part", "salesperson", "transaction"};
    private static final Set<String> tables = new HashSet<>(Arrays.asList("transaction", "salesperson", "part", "manufacturer", "category"));

    public static void createAllTables(Connection conn) throws SQLException {
        System.out.print("Processing...");
        Statement stmt = conn.createStatement();
        for (int i = 0; i < createTables.length; i++) {
//            System.out.println(createTables[i]);
            stmt.executeUpdate(createTables[i]);
        }
        stmt.close();
        System.out.println("Done! Database is initialized!");
        System.out.println();
    }
    public static void deleteAllTables(Connection conn) throws SQLException {
        System.out.print("Processing...");
        Statement stmt = conn.createStatement();
        for (int i = 0; i < deleteTables.length; i++) {
//            System.out.println(deleteTables[i]);
            stmt.executeUpdate(deleteTables[i]);
        }
        stmt.close();
        System.out.println("Done! Database is removed!");
        System.out.println();
    }

    public static void loadDataFromFiles(Connection conn) throws SQLException {
        System.out.print("Type in the Source Data Folder Path: ");
        String folder = Page.takeStringInput();
        System.out.print("Processing...");
        try {
            Statement stmt = conn.createStatement();
            String load;
            for (int i = 0; i < tableNames.length; i++) {
                load = LOAD_DATA + folder + "/" + tableNames[i] + ".txt'" + " INTO TABLE " + tableNames[i];
    //            System.out.println(load);
                stmt.executeUpdate(load);
            }
            stmt.close();
            System.out.println("Done! Data is inputted to the database!");
        } catch (SQLException e) {
            System.err.println();
            System.err.println("Error!");
            System.err.println("Please make sure the folder path is correct and all tables are created");
            System.err.println();
        } catch (NumberFormatException e) {
            System.err.println();
            System.err.println("Error!");
            System.err.println("Please make sure the folder path is correct and all tables are created");
            System.err.println();
        }
    }

    public static void showContent(Connection conn) throws SQLException {
        System.out.print("Which table would you like to show: ");
        String tableName = Page.takeStringInput();
        Statement stmt = conn.createStatement();
        String query = "SELECT * FROM " + tableName;
        System.out.println("Content of table " + tableName + ":");
        display(stmt, query);
        System.out.println();
        stmt.close();
    }

    public static void display(Statement stmt, String query) {
        ResultSet rs;
        try {
            rs = stmt.executeQuery(query);
            ResultSetMetaData rsmd = rs.getMetaData();
            int colNumber = rsmd.getColumnCount();
            for (int i = 1; i <= colNumber; i++) {
                System.out.print("| " + rsmd.getColumnName(i) + " ");
            }
            System.out.println("|");
            while (rs.next()) {
                for (int i = 1; i <= colNumber; i++) {
                    String colVal = rs.getString(i);
                    System.out.print("| " + colVal + " ");
                }
                System.out.println("|");
            }
        } catch (SQLException e) {
            System.err.println();
            System.err.println("Error!");
            System.err.println("Table does not exist");
        }
    }

}
