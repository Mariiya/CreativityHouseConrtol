package sample.dao;


import sample.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;

public class UserDao {


    private Connection connection;
    private Statement stmnt;

    public UserDao() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Creativity", "root", "root");
        stmnt = connection.createStatement();
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }


    public List<User> getUserList() {
        try (
                Statement stmnt = connection.createStatement();
                ResultSet rs = stmnt.executeQuery("select * from users");
        ) {
            List<User> userList = new ArrayList<>();
            while (rs.next()) {
                int id = rs.getInt("user_id");
                String login = rs.getString("login");
                String password = rs.getString("password");
                String type = rs.getString("user_type");
                int userId = rs.getInt("user_num");
                User user = new User(id, login, password, type, userId);
                userList.add(user);
            }
            return userList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return null;
    }

    public void updateType(int id, String newType) throws SQLException {
        stmnt.executeUpdate("UPDATE users set user_type='" + newType + "' WHERE user_id=" + id + ";");
    }

    public void updateLogin(int id, String newLogin) throws SQLException {
        stmnt.executeUpdate("UPDATE users set user_type='" + newLogin + "' WHERE user_id=" + id + ";");
    }

    public void updatePassword(int id, String newPass) throws SQLException {
        stmnt.executeUpdate("UPDATE users set password='" + newPass + "' WHERE user_id=" + id + ";");
    }

    public boolean create(String login, String passw, String userType, int user_num) throws SQLException {
        return stmnt.execute("INSERT INTO Users VALUES (NULL,'" + login + "','" + passw + "','" + userType + "'," + user_num + ");");
    }

    public void delete(Integer id) throws SQLException {
        stmnt.execute("DELETE FROM users WHERE user_id=" + id + ";");
    }
}

