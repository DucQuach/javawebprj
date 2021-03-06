package DataModel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JavaConnectDB {

    public static Connection ConnectDB() throws ClassNotFoundException, SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://172.30.194.36:3306/javaweb?zeroDateTimeBehavior=convertToNull", "root", "1");
            return con;
        } catch (Exception ex) {
            System.out.println("Database.getConnection() Error -->"
                    + ex.getMessage());
            return null;
        }
    }

    public static void close(Connection con) {
        try {
            con.close();
        } catch (Exception ex) {
        }
    }
}
