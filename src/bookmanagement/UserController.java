package bookmanagement;

import model.UserModel;
import java.sql.PreparedStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserController {

    private static final String TABLE_USER = "users";
   
    private static final String COLUMN_USER_NAME = "Username";
    private static final String COLUMN_USER_PASSWORD = "Passwords";

    private Connection connection = null;

    public UserController() {
        if (connection == null) {
            try {
                connection = (Connection) ConnectUtils.getConnection();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(UserController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public boolean getAccount(String username, String password) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_USER + " WHERE "
                + COLUMN_USER_NAME + " = ? AND " + COLUMN_USER_PASSWORD + " = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, username);
        ps.setString(2, password);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            return true;
        }
        return false;

    }

    public boolean getUser(String username) throws SQLException {
        String sql = "SELECT * FROM " + TABLE_USER + " WHERE "
                + COLUMN_USER_NAME + " = ?";
        PreparedStatement ps = connection.prepareStatement(sql);
        ps.setString(1, username);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            return true;
        }
        return false;
    }

    public void insertUser(UserModel user) throws SQLException {
        String sql = "INSERT INTO " + TABLE_USER + " values(?,?)";
        PreparedStatement pst = connection.prepareStatement(sql);
        pst.setString(1, user.getUserName());
        pst.setString(2, user.getPassWord());
        pst.executeUpdate();
        pst.close();

    }
}
