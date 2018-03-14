
package bookmanagement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectUtils {
    private static final String HOST_NAME = "localhost";

    private static final String DB_NAME = "bookdb";

    private static final String USERNAME = "root";

    private static final String PASSWORD = "12345";

    public static Connection getConnection() throws SQLException,
            ClassNotFoundException {
        return getConnection(HOST_NAME, DB_NAME, USERNAME, PASSWORD);
    }

    private static Connection getConnection(String hostName, String dbName,
            String userName, String password) throws SQLException,
            ClassNotFoundException {
        Class.forName("com.mysql.jdbc.Driver");
        String connectionURL = "jdbc:mysql://" + hostName +"/" + dbName +"?useSSL=false";
        Connection conn = (Connection) DriverManager.getConnection(connectionURL, userName,
                password);
        return conn;
    }
    
   
}
