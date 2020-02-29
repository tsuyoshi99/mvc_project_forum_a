package Model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DB
 */
public class DB {
    private static Connection conn=null;
    private static String url = "jdbc:mariadb://10.10.11.209:5674/test_db";
    private static String user = "tsuyoshi";
    private static String password = "0809";
    private DB() {}

    public static Connection getConnection() throws SQLException{
        if(conn != null) {
            return conn;
        }else {
            conn = DriverManager.getConnection(url,user,password);
            return conn;
        }
    }
}