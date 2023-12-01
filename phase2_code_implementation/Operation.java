import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

public class Operation {
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
            System.err.println("Please contact Administrator");
            System.err.println();
        }
    }
}
