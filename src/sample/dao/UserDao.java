package sample.dao;


import sample.model.User;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;

public class UserDao extends BaseDaoUtils {

    public List<User> getUserList() {
        try (
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

    public boolean isEmail(String email) {
        try (
                ResultSet rs = stmnt.executeQuery("select * from users WHERE login='"+addSlashes(email)+"';");
        ) {
            int count=0;
            while (rs.next()) {
                count++;
            }
            if(count==0) {
                return false;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return true;
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

    public int create(String login, String passw, String userType, int user_num) throws SQLException {
        return stmnt.executeUpdate("INSERT INTO Users VALUES (NULL,'" + addSlashes(login) + "','" + passw + "','" + addSlashes(userType) + "'," + user_num + ");");
    }

    public void delete(Integer id) throws SQLException {
        stmnt.execute("DELETE FROM users WHERE user_id=" + id + ";");
    }
}

