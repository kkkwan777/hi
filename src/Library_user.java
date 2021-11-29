import java.sql.*;
import java.util.Scanner;

public class Library_user {
    public static void main(String args[]) {
        try {
            // connection to the database //
            String db_url = "jdbc:mysql://projgw.cse.cuhk.edu.hk:2633/db5";
            String db_username = "Group5";
            String db_password = "password";

            Connection connection = DriverManager.getConnection(db_url, db_username, db_password);

            // query statement //
            String query_operation = "select DISTINCT B.returndate, C.callnumber, B.copynumber, C.title, C.author, " +
                    "DATE_FORMAT(checkoutdate, '%%d/%%m/%%Y') AS changedDate " +
                    "from Borrow B, Book C " +
                    "where B.userid = '%s' AND B.callnumber = C.callnumber " +
                    "order by changedDate DESC";

            // prompt the input for the user id //
            Scanner id_in = new Scanner(System.in);
            System.out.println("Enter The User ID: ");
            String input_in = id_in.nextLine();

            // add back the input %s back to the query //
            query_operation = String.format(query_operation, input_in);
            System.out.println(query_operation);


            // used to query //
            Statement stmt = connection.createStatement();
            // the result of the query is stored at the resultSet //
            ResultSet resultSet = stmt.executeQuery(query_operation);

            // some informatin statements//
            System.out.println("Loan Record: ");
            System.out.println("|CallNum|CopyNum|Title|Author|Check-out|Returned?|");

            // print out the result through cursor //
            while (resultSet.next()) {
                System.out.print("|");
                System.out.print(resultSet.getString("callnumber"));
                System.out.print("|");

                System.out.print(resultSet.getInt("copynumber"));
                System.out.print("|");

                System.out.print(resultSet.getString("title"));
                System.out.print("|");

                System.out.print(resultSet.getString("author"));
                System.out.print("|");

                System.out.print(resultSet.getString("changedDate"));
                System.out.print("|");

                if (resultSet.getString("returndate") != null)
                    System.out.print("Yes");
                else
                    System.out.print("No");

                System.out.println("|");
            }
        } catch (Exception e) {
            e.printStackTrace();
            }
        System.out.println("End Of Query");
    }
}