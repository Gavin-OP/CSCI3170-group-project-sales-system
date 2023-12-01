import java.sql.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Administrator extends Operation {
    // Create tables
    private static final String CREATE_CATEGORY =
            "CREATE TABLE IF NOT EXISTS category (" +
                    "cID INT NOT NULL AUTO_INCREMENT," +
                    "cNname VARCHAR(20) NOT NULL," +
                    "PRIMARY KEY (cID)" +
                    // "CHECK (cID > 0 AND cID <= 9)" + 
            ")";

    private static final String CREATE_MANUFACTURER =
            "CREATE TABLE IF NOT EXISTS manufacturer (" +
                    "mID INT NOT NULL AUTO_INCREMENT," +
                    "mName VARCHAR(20) NOT NULL," +
                    "mAddress VARCHAR(50) NOT NULL," +
                    "mPhoneNumber INT NOT NULL," +
                    "PRIMARY KEY (mID)" +
                    // "CHECK (mID > 0 AND mID <= 99)," +
                    // "CHECK (mPhoneNumber >= 10000000 AND mPhoneNumber <= 99999999)" +
            ")";

    private static final String CREATE_PART =
            "CREATE TABLE IF NOT EXISTS part (" +
                    "pID INT NOT NULL AUTO_INCREMENT," +
                    "pName VARCHAR(20) NOT NULL," +
                    "pPrice INT NOT NULL," +
                    "pWarrantyPeriod INT NOT NULL," +
                    "pAvailableQuantity INT NOT NULL," +
                    "cID INT NOT NULL," +
                    "mID INT NOT NULL," +
                    "PRIMARY KEY (pID)," +
                    "FOREIGN KEY (mID) REFERENCES manufacturer(mID)," +
                    "FOREIGN KEY (cID) REFERENCES category(cID)" +
                    // "CHECK (pID > 0 AND pID <= 999)," +
                    // "CHECK (pPrice > 0 AND pPrice < 100000)," +
                    // "CHECK (mID > 0 AND mID <= 99)," +
                    // "CHECK (cID > 0 AND cID <= 9)," +
                    // "CHECK (pWarrantyPeriod > 0 AND pWarrantyPeriod <= 99)," +
                    // "CHECK (pAvailableQuantity > 0 AND pAvailableQuantity < 100)" +
            ")";

    private static final String CREATE_SALESPERSON =
            "CREATE TABLE IF NOT EXISTS salesperson (" +
                    "sID INT NOT NULL AUTO_INCREMENT," +
                    "sName VARCHAR(20) NOT NULL," +
                    "sAddress VARCHAR(50) NOT NULL," +
                    "sPhoneNumber INT NOT NULL," +
                    "sExperience INT NOT NULL," +
                    "PRIMARY KEY (sID)" +
                    // "CHECK (sID > 0 AND sID <= 99)," +
                    // "CHECK (sPhoneNumber >= 10000000 AND sPhoneNumber <= 99999999)," +
                    // "CHECK (sExperience >= 1 AND sExperience <= 9)" +
            ")";

    private static final String CREATE_TRANSACTION =
            "CREATE TABLE IF NOT EXISTS transaction (" +
                    "tID INT NOT NULL AUTO_INCREMENT," +
                    "tDate VARCHAR(10) NOT NULL," +
                    "pID INT NOT NULL," +
                    "sID INT NOT NULL," +
                    "PRIMARY KEY (tID)," +
                    "FOREIGN KEY (pID) REFERENCES part(pID)," +
                    "FOREIGN KEY (sID) REFERENCES salesperson(sID)" +
                    // "CHECK (tID > 0 AND tID <= 9999)," +
                    // "CHECK (STR_TO_DATE(tDate, '%d/%m/%Y'))," + 
                    // "CHECK (pID > 0 AND pID <= 999)," +
                    // "CHECK (sID > 0 AND sID <= 99)" +
            ")";
    private static final String[] createTables = {CREATE_CATEGORY, CREATE_MANUFACTURER, CREATE_PART, CREATE_SALESPERSON, CREATE_TRANSACTION};

    // Delete tables
    private static final String DELETE_TRANSACTION = "DROP TABLE IF EXISTS transaction";
    private static final String DELETE_SALESPERSON = "DROP TABLE IF EXISTS salesperson";
    private static final String DELETE_PART = "DROP TABLE IF EXISTS part";
    private static final String DELETE_MANUFACTURER = "DROP TABLE IF EXISTS manufacturer;";
    private static final String DELETE_CATEGORY = "DROP TABLE IF EXISTS category";
    private static final String[] deleteTables = {DELETE_TRANSACTION, DELETE_SALESPERSON, DELETE_PART, DELETE_MANUFACTURER, DELETE_CATEGORY};

    // Load data
    private static String currentPath = System.getProperty("user.dir");
    private static final String LOAD_DATA = "LOAD DATA LOCAL INFILE '" + currentPath + "/";
    private static final String[] tableNames = {"category", "manufacturer", "part", "salesperson", "transaction"};
    private static final Set<String> tables = new HashSet<>(Arrays.asList("transaction", "salesperson", "part", "manufacturer", "category"));

    // Create all table functions
    public static void createAllTables(Connection conn) throws SQLException {
        System.out.print("Processing...");
        Statement stmt = conn.createStatement();
        for (int i = 0; i < createTables.length; i++) {
            System.out.println(createTables[i]);
            stmt.executeUpdate(createTables[i]);
        }
        stmt.close();
        System.out.println("Done! Database is initialized!");
        System.out.println();
    }

    // Delete all table functions
    public static void deleteAllTables(Connection conn) throws SQLException {
        System.out.print("Processing...");
        Statement stmt = conn.createStatement();
        for (int i = 0; i < deleteTables.length; i++) {
            // System.out.println(deleteTables[i]);
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
                System.out.println(load);
                stmt.executeUpdate(load);
            }
            stmt.close();
            System.out.println("Done! Data is inputted to the database!");
            System.out.println();
        } catch (SQLException e) {
            System.err.println();
            System.err.println("Error!");
            System.err.println(e.getErrorCode());
            System.err.println("Please make sure the folder path is correct and all tables are created");
            System.err.println();
        } catch (NumberFormatException e) {
            System.err.println();
            System.err.println("Error!");
            System.err.println("Please make sure the folder path is correct and all tables are created");
            System.err.println();
        }
    }

    // Show content of tables
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
