import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


// display result set
public class Operation {
    public static void display(Statement stmt, String query) {
        try (ResultSet rs = stmt.executeQuery(query)) {
            displayResultSet(rs);
        } catch (SQLException e) {
            System.err.println("Error: " + e.getMessage());
            System.err.println("Please contact Administrator");
        }
    }

    public static ResultSet executeQuery(Statement stmt, String query) throws SQLException {
        return stmt.executeQuery(query);
    }

    public static void displayResultSet(ResultSet rs) throws SQLException {
        ResultSetMetaData rsmd = rs.getMetaData();
        int colNumber = rsmd.getColumnCount();

        // Print column headers
        for (int i = 1; i <= colNumber; i++) {
            System.out.print("| " + rsmd.getColumnName(i) + " ");
        }
        System.out.println("|");

        // Print rows
        while (rs.next()) {
            for (int i = 1; i <= colNumber; i++) {
                String colVal = rs.getString(i);
                System.out.print("| " + colVal + " ");
            }
            System.out.println("|");
        }
    }
}

