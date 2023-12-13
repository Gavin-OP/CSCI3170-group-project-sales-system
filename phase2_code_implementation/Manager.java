//
// Last modification: 2023-12-13
//
// The code is used to implement the Manager page.
// The Manager page is used to list all salespersons, count the no. of sales record of each salesperson under a specific range on years of experience, show the total sales value of each manufacturer and show the N most popular part.
//

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Scanner;

public class Manager {
    public static Scanner input = new Scanner(System.in);
        public static void Manager_operation(){
        do{
            System.out.println("\n-----Operations for manager menu-----");
            System.out.println("What kinds of operation would like to perform?");
            System.out.println("1. List all salespersons");
            System.out.println("2. Count the no. of sales record of each salesperson under a specific range on years of experience");
            System.out.println("3. Show the total sales value of each manufacturer");
            System.out.println("4. Show the N most popular part");
            System.out.println("5. Return to the main menu");
            System.out.print("Enter your choice: ");

            int choice = input.nextInt();
            switch (choice){
                case 1:
                    listAllSalesperson();
                    break;
                case 2:
                    countRecordswithRangeofExperience();
                    System.out.println("End of Query");
                    break;
                case 3:
                    listManufacturersByTotalSales();
                    System.out.println("End of Query");
                    break;
                case 4:
                    System.out.print("Type in the number of parts: ");
                    int N = input.nextInt();
                    listTopNPopularParts(N);
                    System.out.println("End of Query");
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid Input");
            }
        } while(true);
    }
    private static void listAllSalesperson(){
        String orderbyAscending = "SELECT sid, sName, sPhoneNumber, sExperience FROM salesperson ORDER BY sExperience ASC;";
        String orderbyDescending = "SELECT sid, sName, sPhoneNumber, sExperience FROM salesperson ORDER BY sExperience DESC;";
        System.out.println("Choose ordering:");
        System.out.println("1. By ascending order");
        System.out.println("2. By descending order");
        System.out.print("Choose the list ordering: ");
        int order = input.nextInt();
        try{
            Connection mysql = Main.connectToMySQL();
            Statement sql = mysql.createStatement();
            if(order == 1){
                ResultSet ascendingRS = sql.executeQuery(orderbyAscending);
                ResultSetMetaData matadata = ascendingRS.getMetaData();
                int columnsNumber = matadata.getColumnCount();
                System.out.println("| ID | Name | Mobile Phone | Years of Experience |");
                while (ascendingRS.next()) {
                    System.out.print("| ");
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(" | ");
                        String columnValue = ascendingRS.getString(i);
                        System.out.print(columnValue);
                    }
                    System.out.println(" |");
                }
            }
            else if(order == 2){
                ResultSet result = sql.executeQuery(orderbyDescending);
                ResultSetMetaData matadata = result.getMetaData();
                int columnsNumber = matadata.getColumnCount();
                System.out.println("| ID | Name | Mobile Phone | Years of Experience |");
                while (result.next()) {
                    System.out.print("| ");
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(" | ");
                        String columnValue = result.getString(i);
                        System.out.print(columnValue);
                    }
                    System.out.println(" |");
                }
            }
            else {
                System.out.println("Invalid Input");
            }
        } catch(Exception e){
            System.out.println(e);
        }
    }
    private static void countRecordswithRangeofExperience(){
        String countwithRange = "SELECT s.sid, s.sName, s.sExperience, COUNT(t.sid) as transaction_count FROM salesperson s LEFT JOIN transaction t ON s.sid = t.sid WHERE s.sExperience BETWEEN ? AND ? GROUP BY s.sid ORDER BY s.sid DESC;";
        System.out.print("Type in the lower bound for years of experience: ");
        int lower = input.nextInt();
        System.out.print("Type in the uppper bound for years of experience: ");
        int uppper = input.nextInt();
        try{
            Connection mysql = Main.connectToMySQL();
            Statement sql = mysql.createStatement();
            PreparedStatement countPS = mysql.prepareStatement(countwithRange);
            countPS.setInt(1,lower);
            countPS.setInt(2,uppper);
            if(lower <= uppper){
                countPS.setInt(1, lower);
                countPS.setInt(2, uppper);
                ResultSet result = countPS.executeQuery();
                ResultSetMetaData matadata = countPS.getMetaData();
                int columnsNumber = matadata.getColumnCount();
                System.out.println("| ID | Name | Years of Experience | Number of Transaction");
                while (result.next()) {
                    System.out.print("| ");
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(" | ");
                        String columnValue = result.getString(i);
                        System.out.print(columnValue);
                    }
                    System.out.println(" |");
                }
            }
            else {
                System.out.println("Invalid Input");
            }
        } catch(Exception e){
            System.out.println(e);
        }
    }
    private static void listManufacturersByTotalSales() { 

        String sql = "SELECT m.mid, m.mname, SUM(p.pPrice) as totalSalesValue " + 
                     "FROM manufacturer m " + 
                     "JOIN part p ON m.mid = p.mid " + 
                     "JOIN transaction t ON p.pid = t.pid " + 
                     "GROUP BY m.mid, m.mname " + 
                     "ORDER BY totalSalesValue DESC;";

        try {

            Connection mysql = Main.connectToMySQL();
            Statement state = mysql.createStatement();
            ResultSet result = state.executeQuery(sql);

            System.out.println("| Manufacturer ID | Manufacturer Name | Total Sales Value |");

            while (result.next()) {

                System.out.println("| " + result.getString("mid") + " | " + result.getString("mname") + " | " + result.getString("totalSalesValue") + " |");

            }

        } catch (Exception e) {

            System.out.println(e);

        }

    }

    private static void listTopNPopularParts(int N) {
        String sql = "SELECT p.pid, p.pname, COUNT(t.tid) as totalTransaction " + 
                     "FROM part p " + 
                     "JOIN transaction t ON p.pid = t.pid " +
                     "GROUP BY p.pid, p.pname " + 
                     "ORDER BY totalTransaction DESC " + 
                     "LIMIT " + N + ";"; 

        try {

            Connection mysql = Main.connectToMySQL();

            Statement state = mysql.createStatement();

            ResultSet result = state.executeQuery(sql);

            System.out.println("| Part ID | Part Name | No. of Transaction |");

            while (result.next()) {

                System.out.println("| " + result.getString("pid") + " | " + result.getString("pname") + " | " + result.getString("totalTransaction") + " |");

            }

        } catch (Exception e) { 

            System.out.println(e);

        }

    }

}