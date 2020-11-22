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

    public UserDao() throws SQLException {

        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/Creativity", "root", "root");
    }

    public void shutdown() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    public List<User> getUserList()  {
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
                User user = new User(id,login, password,type,userId);
               userList.add(user);
            }
            return userList;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    return null;
    }
    // other methods, eg. addPerson(...) etc
}

